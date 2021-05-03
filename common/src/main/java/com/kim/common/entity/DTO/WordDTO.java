package com.kim.common.entity.DTO;

import lombok.Data;

import java.util.List;

/**
 * @author huangjie
 * @description  word文档对象
 * @date 2020/7/21
 */
@Data
public class WordDTO {

    List<ParagraphDTO> paragraphs;

    List<TableDTO> tables;

}
