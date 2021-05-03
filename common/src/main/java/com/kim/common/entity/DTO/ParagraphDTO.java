package com.kim.common.entity.DTO;

import lombok.Data;

import java.util.List;

/**
 * @author huangjie
 * @description  word文档段落对象
 * @date 2020/7/21
 */
@Data
public class ParagraphDTO {

    private Integer index;

    private List<RunDTO> runs;

    /**
     * 段落里面的run对象
     * */
    @Data
    public static class RunDTO{
        private Integer index;
        private String text;
    }
}
