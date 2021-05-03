package com.kim.common.util;


import com.kim.common.entity.DTO.ParagraphDTO;
import com.kim.common.entity.DTO.TableDTO;
import com.kim.common.entity.DTO.WordDTO;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.*;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

/**
 * @author huangjie
 * @description  poi处理word文档工具类
 * @date 2020/7/17
 */
public class PoiUtil {

    /**
     * 将word文档输入流解析成word文档对象
     * word文档要求2007+版本、docx后缀
     * 暂不考虑图片层、批注层、页头、页脚
     * 忽略空字符串内容
     * 约定只有一层表格
     * @param wordInput word文档输入流
     * @return Word word文档对象
     * */
    public static WordDTO resolveWord(InputStream wordInput ) throws Exception{
        if(wordInput==null){
            return null;
        }
        WordDTO word=new WordDTO();

        XWPFDocument document=new XWPFDocument(wordInput);
        //提取段落
        List<ParagraphDTO> paragraphs=reseloveParagraphs(document.getParagraphs());
        word.setParagraphs(paragraphs);
        List<TableDTO> tables=new LinkedList<>();

        //提取表格
        List<XWPFTable> xwpfTables = document.getTables();
        if(xwpfTables!=null&&xwpfTables.size()>0){
            for(int i=0;i<xwpfTables.size();i++){
                TableDTO table=new TableDTO();
                List<TableDTO.RowDTO> rows=new LinkedList<>();
                XWPFTable xwpfTable = xwpfTables.get(i);
                List<XWPFTableRow> xwpfRows = xwpfTable.getRows();
                //提取表格内的行
                if(xwpfRows!=null&&xwpfRows.size()>0){
                    for(int j=0;j<xwpfRows.size();j++){
                        TableDTO.RowDTO row=new TableDTO.RowDTO();
                        XWPFTableRow xwpfTableRow = xwpfRows.get(j);
                        List<XWPFTableCell> tableCells = xwpfTableRow.getTableCells();
                        List<TableDTO.CellDTO> cells=new LinkedList<>();

                        //提取行内的单元格
                        if(tableCells!=null&&tableCells.size()>0){
                            for(int k=0;k<tableCells.size();k++){
                                XWPFTableCell tableCell = tableCells.get(k);
                                if(StringUtils.isNotEmpty(tableCell.getText())){
                                    TableDTO.CellDTO cell=new TableDTO.CellDTO();
                                    cell.setIndex(k);
                                    List<XWPFParagraph> paragraphsCell = tableCell.getParagraphs();
                                    cell.setParagraphs(reseloveParagraphs(paragraphsCell));
                                    cells.add(cell);
                                }
                            }
                            row.setCells(cells);
                            row.setIndex(j);
                            rows.add(row);
                        }
                    }
                    table.setIndex(i);
                    table.setRows(rows);
                    tables.add(table);
                }
            }
        }
        word.setTables(tables);
        return word;
    }

    private static List<ParagraphDTO> reseloveParagraphs(List<XWPFParagraph> xwpfParagraphs){

        if(xwpfParagraphs!=null&&xwpfParagraphs.size()>0){
            List<ParagraphDTO> paragraphs=new LinkedList<>();
            for(int i=0;i<xwpfParagraphs.size();i++){
                XWPFParagraph xwpfParagraph = xwpfParagraphs.get(i);
                if(StringUtils.isNotEmpty(xwpfParagraph.getText())){
                    ParagraphDTO paragraph=new ParagraphDTO();
                    paragraph.setIndex(i);
                    List<XWPFRun> xwpfRuns = xwpfParagraph.getRuns();
                    List<ParagraphDTO.RunDTO> runs=new LinkedList<>();
                    for (int j=0;j<xwpfRuns.size();j++) {
                        XWPFRun xwpfRun = xwpfRuns.get(j);
                        if(StringUtils.isNotEmpty(xwpfRun.getText(0))){
                            ParagraphDTO.RunDTO run=new ParagraphDTO.RunDTO();
                            run.setIndex(j);
                            run.setText(xwpfRun.getText(0));
                            runs.add(run);
                        }

                    }
                    paragraph.setRuns(runs);
                    paragraphs.add(paragraph);
                }
            }
            return paragraphs;
        }
        return null;

    }

    /**
     * 将word文档对象的对应段落或者表格进行替换
     * 暂不考虑图片层、批注层、页头、页脚
     * 约定只有一层表格
     * 忽略空字符串内容
     * @param word 文档对象
     * @param wordInput word文档输入流对象
     * @param wordOut word文档输出流对象
     * */
    public static void replaceWord(WordDTO word, InputStream wordInput, OutputStream wordOut) throws Exception{

        if(word==null){
            return;
        }
        List<ParagraphDTO> paragraphs = word.getParagraphs();

        XWPFDocument document=new XWPFDocument(wordInput);
        //替换段落
        replaceParagraphs(document.getParagraphs(),paragraphs);

        //替换表格
        List<TableDTO> tables = word.getTables();
        List<XWPFTable> xwpfTables = document.getTables();
        if(tables!=null&&tables.size()>0){
            for(int i=0;i<tables.size();i++){
                TableDTO table = tables.get(i);
                XWPFTable xwpfTable = xwpfTables.get(table.getIndex());
                List<TableDTO.RowDTO> rows = table.getRows();
                if(rows!=null&&rows.size()>0){
                    for(int j=0;j<rows.size();j++){
                        TableDTO.RowDTO row = rows.get(j);
                        XWPFTableRow tableRow = xwpfTable.getRow(row.getIndex());
                        List<TableDTO.CellDTO> cells = row.getCells();
                        if(cells!=null&&cells.size()>0){

                            for(int k=0;k<cells.size();k++){
                                TableDTO.CellDTO cell = cells.get(k);
                                XWPFTableCell rowCell = tableRow.getCell(cell.getIndex());
                                List<XWPFParagraph> cellParagraphs = rowCell.getParagraphs();
                                if(cellParagraphs!=null&&cellParagraphs.size()>0){
                                    replaceParagraphs(cellParagraphs,cell.getParagraphs());
                                }

                            }
                        }

                    }
                }
            }
        }

        document.write(wordOut);
    }

    private static void replaceParagraphs(List<XWPFParagraph> xwpfParagraphs , List<ParagraphDTO> paragraphs){
        if(paragraphs!=null&&paragraphs.size()>0){
            for (ParagraphDTO paragraph:paragraphs
            ) {
                XWPFParagraph xwpfParagraph = xwpfParagraphs.get(paragraph.getIndex());
                List<XWPFRun> xwpfRuns = xwpfParagraph.getRuns();
                List<ParagraphDTO.RunDTO> runs = paragraph.getRuns();
                for (int i=0;i<runs.size();i++) {
                    xwpfRuns.get(runs.get(i).getIndex()).setText(runs.get(i).getText(),0);
                }

            }
        }


    }


}
