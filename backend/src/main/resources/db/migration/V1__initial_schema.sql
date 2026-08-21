CREATE TABLE users (
  id BIGSERIAL PRIMARY KEY, username VARCHAR(32) NOT NULL UNIQUE, email VARCHAR(255) NOT NULL UNIQUE,
  password_hash VARCHAR(255) NOT NULL, avatar_url VARCHAR(500), bio VARCHAR(280), registered_at TIMESTAMPTZ NOT NULL,
  activity_minutes BIGINT NOT NULL DEFAULT 0, experience BIGINT NOT NULL DEFAULT 0, level INTEGER NOT NULL DEFAULT 1
);
CREATE TABLE rooms (
  id BIGSERIAL PRIMARY KEY, name VARCHAR(100) NOT NULL, description VARCHAR(2000) NOT NULL, game_system VARCHAR(80) NOT NULL,
  campaign_type VARCHAR(30) NOT NULL, max_participants INTEGER NOT NULL, tags VARCHAR(500) NOT NULL,
  join_mode VARCHAR(30) NOT NULL, created_at TIMESTAMPTZ NOT NULL, completed BOOLEAN NOT NULL DEFAULT FALSE, version BIGINT NOT NULL DEFAULT 0
);
CREATE TABLE room_members (
  id BIGSERIAL PRIMARY KEY, room_id BIGINT NOT NULL REFERENCES rooms(id) ON DELETE CASCADE,
  user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE, role VARCHAR(20) NOT NULL, joined_at TIMESTAMPTZ NOT NULL,
  CONSTRAINT uk_room_member UNIQUE(room_id, user_id)
);
CREATE TABLE join_requests (
  id BIGSERIAL PRIMARY KEY, room_id BIGINT NOT NULL REFERENCES rooms(id) ON DELETE CASCADE,
  user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE, status VARCHAR(20) NOT NULL, created_at TIMESTAMPTZ NOT NULL,
  resolved_at TIMESTAMPTZ, CONSTRAINT uk_join_request UNIQUE(room_id, user_id)
);
CREATE TABLE messages (
  id BIGSERIAL PRIMARY KEY, room_id BIGINT NOT NULL REFERENCES rooms(id) ON DELETE CASCADE,
  user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE, content VARCHAR(2000) NOT NULL, sent_at TIMESTAMPTZ NOT NULL
);
CREATE INDEX idx_messages_room_sent ON messages(room_id, sent_at DESC);
CREATE TABLE availabilities (
  id BIGSERIAL PRIMARY KEY, room_id BIGINT NOT NULL REFERENCES rooms(id) ON DELETE CASCADE,
  user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE, available_date DATE NOT NULL,
  start_time TIME, end_time TIME, available BOOLEAN NOT NULL, CONSTRAINT uk_availability UNIQUE(room_id, user_id, available_date)
);
CREATE TABLE game_sessions (
  id BIGSERIAL PRIMARY KEY, room_id BIGINT NOT NULL REFERENCES rooms(id) ON DELETE CASCADE,
  scheduled_at TIMESTAMPTZ NOT NULL, title VARCHAR(120) NOT NULL, status VARCHAR(20) NOT NULL, created_at TIMESTAMPTZ NOT NULL
);
CREATE TABLE session_responses (
  id BIGSERIAL PRIMARY KEY, session_id BIGINT NOT NULL REFERENCES game_sessions(id) ON DELETE CASCADE,
  user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE, available BOOLEAN NOT NULL,
  CONSTRAINT uk_session_response UNIQUE(session_id, user_id)
);
CREATE TABLE friendships (
  id BIGSERIAL PRIMARY KEY, requester_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
  addressee_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE, status VARCHAR(20) NOT NULL, created_at TIMESTAMPTZ NOT NULL,
  CONSTRAINT uk_friendship UNIQUE(requester_id, addressee_id), CONSTRAINT chk_not_self CHECK(requester_id <> addressee_id)
);

