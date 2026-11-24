CREATE TABLE load_test_configs (
    id BIGSERIAL PRIMARY KEY,
    target_api_id BIGINT NOT NULL,
    name VARCHAR(150) NOT NULL,
    virtual_users INTEGER NOT NULL,
    duration_seconds INTEGER NOT NULL,
    requests_per_second INTEGER NOT NULL,
    timeout_ms INTEGER NOT NULL,

    created_at TIMESTAMP WITH TIME ZONE NOT NULL
        DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_load_test_target
        FOREIGN KEY (target_api_id)
        REFERENCES target_apis(id),

    CONSTRAINT chk_virtual_users
        CHECK (virtual_users BETWEEN 1 AND 100),

    CONSTRAINT chk_duration
        CHECK (duration_seconds BETWEEN 1 AND 3600),

    CONSTRAINT chk_rps
        CHECK (requests_per_second BETWEEN 1 AND 1000),

    CONSTRAINT chk_timeout
        CHECK (timeout_ms BETWEEN 100 AND 60000)
);

CREATE INDEX idx_load_test_configs_target
    ON load_test_configs(target_api_id);