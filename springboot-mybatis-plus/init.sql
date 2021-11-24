CREATE TABLE `it-test`.`t_user` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` VARCHAR(45) NULL COMMENT '用户名',
  `password` VARCHAR(45) NULL COMMENT '密码',
  `created_at` DATETIME NULL COMMENT '创建时间',
  `deleted_at` DATETIME NULL COMMENT '删除时间',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
COMMENT = '用户表';

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

ALTER TABLE `it-test`.`t_user`
ADD COLUMN `gids` JSON NULL COMMENT 'json字段' AFTER `deleted_at`;

ALTER TABLE `it-test`.`t_user`
ADD COLUMN `status` INT NULL COMMENT '状态' AFTER `gids`;

ALTER TABLE `it-test`.`t_user`
ADD COLUMN `tenant_id` BIGINT(255) NULL COMMENT '租户唯一标识字段' AFTER `status`;


