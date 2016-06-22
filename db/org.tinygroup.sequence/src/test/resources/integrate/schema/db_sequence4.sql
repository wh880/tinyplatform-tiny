CREATE SCHEMA IF NOT EXISTS `db_sequence4`;

CREATE TABLE IF NOT EXISTS `sequence` (`name` VARCHAR(50) NOT NULL, `value` BIGINT NOT NULL, `min_value` BIGINT NOT NULL,`max_value` BIGINT NOT NULL,`step` INT NOT NULL, `gmt_create` DATE NULL,`gmt_modified` DATE NULL);
