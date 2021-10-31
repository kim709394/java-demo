CREATE TABLE `it-test`.`t_user` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` VARCHAR(45) NULL COMMENT '用户名',
  `password` VARCHAR(45) NULL COMMENT '密码',
  `createdAt` DATETIME NULL COMMENT '创建时间',
  `deletedAt` DATETIME NULL COMMENT '删除时间',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
COMMENT = '用户表';


