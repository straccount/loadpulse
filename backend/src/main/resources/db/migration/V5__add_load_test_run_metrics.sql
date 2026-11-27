ALTER TABLE load_test_runs
ADD COLUMN total_requests BIGINT NOT NULL DEFAULT 0,
ADD COLUMN successful_requests BIGINT NOT NULL DEFAULT 0,
ADD COLUMN failed_requests BIGINT NOT NULL DEFAULT 0,
ADD COLUMN version BIGINT NOT NULL DEFAULT 0;

ALTER TABLE load_test_runs
ADD CONSTRAINT chk_run_metrics
CHECK (
    total_requests >= 0
    AND successful_requests >= 0
    AND failed_requests >= 0
    AND total_requests = successful_requests + failed_requests
);