package com.kim.common;

import com.kim.common.util.UnicodeUtil;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;

/**
 * @Author kim
 * @Since 2021/4/21
 * 编解码测试
 */
public class EncodeTest {

    /**
     * unicode编解码测试
     * */
    @Test
    public void unicodeToCn() throws UnsupportedEncodingException {

        String str="\\u670d\\u52a1\\u542f\\u52a8\\u5b8c\\u6bd5";
        String res= UnicodeUtil.unicodeToCn(str);
        System.out.println(res);
    }

    @Test
    public void cnToUnicode(){
        String str="服务启动完毕";
        System.out.println(UnicodeUtil.cnToUnicode(str));
    }


}
