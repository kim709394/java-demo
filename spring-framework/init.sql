CREATE TABLE `it-test`.`t_goods` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL COMMENT '商品名称',
  `price` INT NULL,
  `created_at` DATETIME NULL,
  `deleted_at` DATETIME NULL,
  `oid` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin
COMMENT = '商品表';