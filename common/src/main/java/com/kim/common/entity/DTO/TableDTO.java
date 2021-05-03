package com.kim.common.entity.DTO;

import lombok.Data;

import java.util.List;

/**
 * @author huangjie
 * @description   word文档的表格对象
 * @date 2020/7/21
 */
@Data
public class TableDTO {

    private Integer index;

    private List<RowDTO> rows;

    /**
     * 表格里面的行对象
     * */
    @Data
    public static class RowDTO{
        private Integer index;
        private List<CellDTO> cells;

    }

    /**
     * 行里面的单元格对象
     * */
    @Data
    public static class CellDTO{

        private Integer index;
        private List<ParagraphDTO> paragraphs;


    }

}
