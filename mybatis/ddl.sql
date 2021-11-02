CREATE TABLE `it-test`.`t_user` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` VARCHAR(45) NULL COMMENT '用户名',
  `password` VARCHAR(45) NULL COMMENT '密码',
  `created_at` DATETIME NULL COMMENT '创建时间',
  `deleted_at` DATETIME NULL COMMENT '删除时间',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
COMMENT = '用户表';

CREATE TABLE `it-test`.`t_order` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` VARCHAR(45) NULL COMMENT '订单名称',
  `status` INT NULL COMMENT '订单状态',
  `created_at` DATETIME NULL,
  `deleted_at` DATETIME NULL COMMENT '删除时间',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin
COMMENT = '订单表';

