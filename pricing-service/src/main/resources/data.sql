-- src/main/resources/data.sql

-- 1) Limpio la tabla antes de reinsertar
TRUNCATE TABLE tariff_config;

-- 2) Inserto sólo los 3 registros base
INSERT INTO tariff_config (laps, minutes, base_price) VALUES
(10,10,15000),
(15,15,20000),
(20,20,25000);