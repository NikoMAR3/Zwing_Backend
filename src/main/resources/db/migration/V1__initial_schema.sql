-- Función para actualizar updated_at (debe ir PRIMERO)
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

-- Tabla de usuarios
CREATE TABLE users (
    user_id UUID PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255),
    picture VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Índice para email
CREATE INDEX idx_users_email ON users(email);

-- Trigger para actualizar updated_at en usuarios
CREATE TRIGGER update_users_updated_at BEFORE UPDATE ON users
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

-- Tabla para eventos
CREATE TABLE events (
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
CREATE TABLE outbox_events (
    id UUID PRIMARY KEY,
    aggregate_id VARCHAR(255) NOT NULL,
    event_type VARCHAR(255) NOT NULL,
    payload JSONB NOT NULL,
    correlation_id VARCHAR(255),
    published BOOLEAN DEFAULT false,
    occurred_on TIMESTAMP NOT NULL,
    published_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT NOW()
);

-- Tabla para snapshots
CREATE TABLE snapshots (
    channel_rack_id VARCHAR(255) PRIMARY KEY,
    version BIGINT NOT NULL,
    snapshot_data JSONB NOT NULL,
    created_at TIMESTAMP DEFAULT NOW()
);

-- Tabla de sesiones
CREATE TABLE sessions (
    id VARCHAR(36) PRIMARY KEY,
    tool_id VARCHAR(255) NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    last_activity_at TIMESTAMP NOT NULL,
    status VARCHAR(20) NOT NULL CHECK (status IN ('ACTIVE', 'PAUSED', 'CLOSED', 'EXPIRED')),
    metadata JSONB DEFAULT '{}',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla para Sound Presets
CREATE TABLE sound_presets (
    sound_id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(50) NOT NULL,
    blob_url VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tablitas para projects

CREATE TABLE projects (
    id        VARCHAR PRIMARY KEY,
    name      VARCHAR NOT NULL,
    owner_id  VARCHAR NOT NULL
);

CREATE TABLE project_members (
    id         SERIAL PRIMARY KEY,
    user_id    VARCHAR NOT NULL,
    role       VARCHAR NOT NULL,
    project_id VARCHAR NOT NULL REFERENCES projects(id) ON DELETE CASCADE
);

CREATE TABLE tool_refs (
    tool_id    VARCHAR NOT NULL,
    project_id VARCHAR NOT NULL REFERENCES projects(id) ON DELETE CASCADE,
    PRIMARY KEY (tool_id, project_id)
);

-- Índices para eventos
CREATE INDEX idx_events_aggregate_id ON events(aggregate_id);
CREATE INDEX idx_events_aggregate_version ON events(aggregate_id, version);
CREATE INDEX idx_events_occurred_on ON events(occurred_on);

-- Índices para outbox
CREATE INDEX idx_outbox_published ON outbox_events(published, occurred_on);

-- Índices para snapshots
CREATE INDEX idx_snapshots_channel_rack_id ON snapshots(channel_rack_id);

-- Índices para sesiones
CREATE INDEX idx_sessions_tool_id ON sessions(tool_id);
CREATE INDEX idx_sessions_user_id ON sessions(user_id);
CREATE INDEX idx_sessions_status ON sessions(status);
CREATE INDEX idx_sessions_tool_status ON sessions(tool_id, status);
CREATE INDEX idx_sessions_created_at ON sessions(created_at DESC);

-- Índices para sound presets
CREATE INDEX idx_sound_presets_category ON sound_presets(category);

-- Trigger para actualizar updated_at en sesiones
CREATE TRIGGER update_sessions_updated_at BEFORE UPDATE ON sessions
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();