CREATE TABLE load_test_runs (
    id BIGSERIAL PRIMARY KEY,
    load_test_config_id BIGINT NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    started_at TIMESTAMP WITH TIME ZONE,
    finished_at TIMESTAMP WITH TIME ZONE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    error_message VARCHAR(1000),

    CONSTRAINT fk_load_test_run_config FOREIGN KEY (load_test_config_id) REFERENCES load_test_configs(id),
    CONSTRAINT chk_load_test_run_status CHECK (status IN ('PENDING','RUNNING','COMPLETED','FAILED')));

CREATE INDEX idx_load_test_runs_config ON load_test_runs(load_test_config_id);
CREATE INDEX idx_load_test_runs_status ON load_test_runs(status);