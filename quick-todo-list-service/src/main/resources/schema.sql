CREATE TABLE IF NOT EXISTS `todos` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `todo_item` TEXT NOT NULL,
  `user_id_creator` INT UNSIGNED NOT NULL,
  `checked` tinyint NOT NULL DEFAULT '0',
  `color` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT 'success',
  `environment` VARCHAR(255) NULL DEFAULT NULL,
  `created_at` TIMESTAMP NULL DEFAULT NULL,
  `updated_at` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;