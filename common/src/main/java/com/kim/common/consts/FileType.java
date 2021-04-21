package com.kim.common.consts;

/**
 * @Author kim
 * @Since 2021/4/21
 * 文件类型枚举
 */
public enum FileType {

    JPG("FFD8FF","jpg"),
    PNG("89504E47","png"),
    GIF("47494638","gif"),
    TIF("49492A00","tif"),
    BMP("424D","bmp"),
    DWG("41433130","dwg"),
    PSD("38425053","psd"),
    RTF("7B5C727466","rtf"),
    XML("3C3F786D6C","xml"),
    HTML("68746D6C3E","html"),
    EML("44656C69766572792D646174653A","eml"),
    DOC("D0CF11E0","doc"),
    MDB("5374616E64617264204A","mdb"),
    PS("252150532D41646F6265","ps"),
    PDF("255044462D312E","pdf"),
    DOCX("504B0304","docx"),
    RAR("52617221","rar"),
    WAV("57415645","wav"),
    AVI("41564920","avi"),
    RM("2E524D46","rm"),
    MPG("000001BA","mpg"),
    _MPG("000001B3","mpg"),
    MOV("6D6F6F76","mov"),
    ASF("3026B2758E66CF11","asf"),
    MID("4D546864","mid"),
    GZ("1F8B08","gz"),
    EXE_ALL("4D5A9000","exe/all"),
    TXT("75736167","txt");
    public String fileHead;
    public String fileType;

    FileType(String fileHead,String fileType){
        this.fileHead=fileHead;
        this.fileType=fileType;
    }


}
