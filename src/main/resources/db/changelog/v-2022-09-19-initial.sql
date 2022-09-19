CREATE TABLE service_history(
    id SERIAL,
    service_name VARCHAR(255),
    updated_at TIMESTAMP,
    version INT
);

CREATE TABLE system_history(
    id SERIAL,
    system_name VARCHAR(255),
    updated_at TIMESTAMP,
    version INT
);