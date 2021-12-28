package com.kim.common;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.read.listener.ReadListener;
import com.kim.common.entity.ExcelData;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author huangjie
 * @description
 * @date 2021/12/27
 */
public class ExcelTest {

    /**
     * poi实现简单excel导出
     *
     */
    @Test
    public void testPoiExcelExtract() throws Exception {
        poiExcelExtract("xls");
        poiExcelExtract("xlsx");
    }

    /**
     * excel解析
     * */
    @Test
    public void excelRead(){
        excelAnalysis("C:\\Users\\WT-0906\\Desktop\\example.xls");
        System.out.println("-------------------------------------------------------------");
        excelAnalysis("C:\\Users\\WT-0906\\Desktop\\example.xlsx");

    }

    private void excelAnalysis(String filePath){

        InputStream in=null;
        Workbook excel=null;
        try{
            in=new FileInputStream(filePath);
            excel=WorkbookFactory.create(in);
            Sheet sheet = excel.getSheetAt(0);
            int firstRowNum = sheet.getFirstRowNum();
            int lastRowNum = sheet.getLastRowNum();
            for(int i=firstRowNum;i<=lastRowNum;i++){
                Row row = sheet.getRow(i);
                short firstCellNum = row.getFirstCellNum();
                short lastCellNum = row.getLastCellNum();
                for(int j=firstCellNum;j<lastCellNum;j++){
                    System.out.println(row.getCell(j));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(in!= null){
                    in.close();
                }
                if(excel != null){
                    excel.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void poiExcelExtract(String suffix){
        Workbook excel=null;
        OutputStream out=null;
        try {
            if("xls".equals(suffix.toLowerCase())){
                excel=new HSSFWorkbook();
                out=new FileOutputStream("C:\\Users\\WT-0906\\Desktop\\example.xls");
            }else if("xlsx".equals(suffix.toLowerCase())){
                excel=new XSSFWorkbook();
                out=new FileOutputStream("C:\\Users\\WT-0906\\Desktop\\example.xlsx");
            }
            //创建单元格样式
            CellStyle cellStyle = excel.createCellStyle();
            //设置单元格样式为居中
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            //创建一个sheel，传入sheel名称
            Sheet sheel = excel.createSheet("sheel");
            //创建头一行
            Row headerRow = sheel.createRow(0);
            //在一行中创建一个单元格
            Cell cell0 = headerRow.createCell(0);
            cell0.setCellStyle(cellStyle);
            cell0.setCellValue("第一格");
            Cell cell1 = headerRow.createCell(1);
            cell1.setCellValue("第二格");
            cell1.setCellStyle(cellStyle);
            Cell cell2 = headerRow.createCell(2);
            cell2.setCellStyle(cellStyle);
            cell2.setCellValue("第三格");

            for(int i=0;i<3;i++){
                //依次创建多行
                Row row = sheel.createRow(i+1);
                for(int j=0;j<3;j++){
                    Cell cell = row.createCell(j);
                    cell.setCellStyle(cellStyle);
                    cell.setCellValue("第"+(i+1)+"行的第"+j+"格");
                }
            }
            excel.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(out!= null){
                    out.close();
                }
                if(excel != null){
                    excel.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String fileName="C:\\Users\\WT-0906\\Desktop\\easyexcel.xlsx";

    @Test
    public void testPageRead(){
        pageRead();
    }

    //分页读取
    private void pageRead(){

        EasyExcel.read(fileName, ExcelData.class, new ReadListener<ExcelData>() {

            private final Integer BATCH_COUNT=10;   //设置一页10条
            private List<ExcelData> excelDatas=new ArrayList<>();


            @Override
            public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {
                //读取第一行表头
                System.out.println("读取表头");
                headMap.entrySet().forEach(entry->{
                    System.out.println("key:"+entry.getKey());
                    System.out.println("value:"+entry.getValue().getStringValue());
                });
            }

            @Override
            public void invoke(ExcelData data, AnalysisContext analysisContext) {
                //读取内容,一行一行的读取
                System.out.println("读取分页数据");
                excelDatas.add(data);
                if (excelDatas.size()>=BATCH_COUNT){
                    //读完第一页后执行操作
                    dataHandle();
                    //清除list缓存
                    excelDatas.clear();
                }

            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                //读取完后进行的操作,分页中读取最后一页不足一页的数据
                System.out.println("读取完后的操作");
                dataHandle();
                excelDatas.clear();
            }
            //执行读取后的操作
            private void dataHandle(){
                excelDatas.stream().forEach(excelData -> System.out.println(excelData));
            }
        }).sheet().doRead();    //读取第一个sheet的数据然后自动关闭


    }


}
