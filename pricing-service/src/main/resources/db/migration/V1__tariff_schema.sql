CREATE TABLE tariff_config(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    rate_type VARCHAR(10) NOT NULL,
    laps INT NOT NULL,
    minutes INT NOT NULL,
    price INT NOT NULL,
    UNIQUE KEY uq_rate_minutes(rate_type,minutes)
) ENGINE=InnoDB;
