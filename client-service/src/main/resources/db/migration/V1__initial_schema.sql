CREATE TABLE clients (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(120) NOT NULL UNIQUE,
  name VARCHAR(120) NOT NULL,
  phone VARCHAR(32),
  registered_at DATETIME,
  INDEX idx_clients_email (email)
) ENGINE=InnoDB;

CREATE TABLE visits (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  client_id BIGINT NOT NULL,
  visit_date DATE NOT NULL,
  FOREIGN KEY (client_id) REFERENCES clients(id),
  INDEX idx_visit_date (visit_date)
) ENGINE=InnoDB;
