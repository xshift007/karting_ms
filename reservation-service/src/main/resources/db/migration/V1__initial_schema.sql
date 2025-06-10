CREATE TABLE reservation (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  laps INT NOT NULL,
  participants INT NOT NULL,
  session_id BIGINT,
  client_email VARCHAR(255),
  base_price INT NOT NULL,
  discount_percent INT NOT NULL,
  final_price INT NOT NULL,
  status VARCHAR(32)
) ENGINE=InnoDB;
