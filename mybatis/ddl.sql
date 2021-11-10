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
  `uid` INT NOT NULL '用户id',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin
COMMENT = '订单表';

CREATE TABLE `it-test`.`t_user_role` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '角色',
  `user_id` INT NULL COMMENT '用户id',
  `role_id` INT NULL COMMENT '角色id',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin
COMMENT = '用户角色表';

CREATE TABLE `it-test`.`t_role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `code` VARCHAR(45) NULL,
  `created_at` DATETIME NULL,
  `deleted_at` DATETIME NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin
COMMENT = '角色表';

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

