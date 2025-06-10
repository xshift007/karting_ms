CREATE TABLE sessions (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  session_date DATE NOT NULL,
  start_time TIME NOT NULL,
  end_time TIME NOT NULL,
  capacity INT,
  version BIGINT,
  participants_count INT DEFAULT 0,
  UNIQUE KEY uq_session_unique(session_date,start_time,end_time)
) ENGINE=InnoDB;
