CREATE TABLE IF NOT EXISTS `texts` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `text_before_cleanup` TEXT NULL DEFAULT NULL,
  `text_after_cleanup` TEXT NULL DEFAULT NULL,
  `environment` VARCHAR(255) NULL DEFAULT NULL,
  `created_at` TIMESTAMP NULL DEFAULT NULL,
  `updated_at` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;
