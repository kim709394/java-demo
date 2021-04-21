package com.kim.common;

import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.MapContext;
import org.apache.commons.jexl3.internal.Engine;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author kim
 * @Since 2021/4/21
 * 字符串转化为java代码执行
 */
public class StrToCodeExeTest {


    @Test
    public void testProcessString() {
        String expression = "(a>1&&b<2)||(c>3&&d<4)";
        Map<String, Object> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        map.put("d",4);
        JexlEngine jexlEngine = new Engine();
        JexlExpression jexlExpression = jexlEngine.createExpression(expression);
        JexlContext jexlContext = new MapContext();
        map.forEach(jexlContext::set);
        System.out.println(jexlExpression.evaluate(jexlContext));
    }

}
