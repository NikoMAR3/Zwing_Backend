-- Tabla para eventos
CREATE TABLE IF NOT EXISTS events (
    id UUID PRIMARY KEY,
    aggregate_id VARCHAR(255) NOT NULL,
    version BIGINT NOT NULL,
    event_type VARCHAR(255) NOT NULL,
    payload JSONB NOT NULL,
    user_id VARCHAR(255),
    session_id VARCHAR(255),
    occurred_on TIMESTAMP NOT NULL,
    metadata JSONB,
    created_at TIMESTAMP DEFAULT NOW(),
    CONSTRAINT unique_aggregate_version UNIQUE(aggregate_id, version)
);

-- Tabla para outbox (publicación de eventos)
CREATE TABLE IF NOT EXISTS outbox_events (
    id UUID PRIMARY KEY,
    aggregate_id VARCHAR(255) NOT NULL,
    event_type VARCHAR(255) NOT NULL,
    payload JSONB NOT NULL,
    published BOOLEAN DEFAULT false,
    occurred_on TIMESTAMP NOT NULL,
    published_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT NOW()
);

-- Tabla para snapshots
CREATE TABLE IF NOT EXISTS snapshots (
    channel_rack_id VARCHAR(255) PRIMARY KEY,
    version BIGINT NOT NULL,
    snapshot_data JSONB NOT NULL,
    created_at TIMESTAMP DEFAULT NOW()
);


-- Migration para crear la tabla de sesiones en PostgreSQL
CREATE TABLE sessions (
    id VARCHAR(36) PRIMARY KEY,
    tool_id VARCHAR(255) NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    last_activity_at TIMESTAMP NOT NULL,
    status VARCHAR(20) NOT NULL CHECK (status IN ('ACTIVE', 'PAUSED', 'CLOSED', 'EXPIRED')),
    metadata JSONB DEFAULT '{}',
    created_at_db TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Índices para optimizar consultas
CREATE INDEX idx_sessions_tool_id ON sessions(tool_id);
CREATE INDEX idx_sessions_user_id ON sessions(user_id);
CREATE INDEX idx_sessions_status ON sessions(status);
CREATE INDEX idx_sessions_tool_status ON sessions(tool_id, status);
CREATE INDEX idx_sessions_created_at ON sessions(created_at DESC);

-- Trigger para actualizar updated_at
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER update_sessions_updated_at BEFORE UPDATE ON sessions
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

-- Índices para optimización
CREATE INDEX IF NOT EXISTS idx_events_aggregate_id ON events(aggregate_id);
CREATE INDEX IF NOT EXISTS idx_events_aggregate_version ON events(aggregate_id, version);
CREATE INDEX IF NOT EXISTS idx_events_occurred_on ON events(occurred_on);
CREATE INDEX IF NOT EXISTS idx_outbox_published ON outbox_events(published, occurred_on);
CREATE INDEX IF NOT EXISTS idx_snapshots_channel_rack_id ON snapshots(channel_rack_id);