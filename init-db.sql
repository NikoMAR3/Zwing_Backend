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

-- Índices para optimización
CREATE INDEX IF NOT EXISTS idx_events_aggregate_id ON events(aggregate_id);
CREATE INDEX IF NOT EXISTS idx_events_aggregate_version ON events(aggregate_id, version);
CREATE INDEX IF NOT EXISTS idx_events_occurred_on ON events(occurred_on);
CREATE INDEX IF NOT EXISTS idx_outbox_published ON outbox_events(published, occurred_on);
CREATE INDEX IF NOT EXISTS idx_snapshots_channel_rack_id ON snapshots(channel_rack_id);