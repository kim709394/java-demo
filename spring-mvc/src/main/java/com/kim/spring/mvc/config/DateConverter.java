package com.kim.spring.mvc.config;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author huangjie
 * @description  配置时间类型转换器
 * @date 2022-03-12
 */
public class DateConverter implements Converter<String, Date> {



    @Override
    public Date convert(String s){


        try {
            Long dateLong=Long.valueOf(s);
            return new Date(dateLong);
        } catch (NumberFormatException e) {
            try {
                return new SimpleDateFormat("yyyy-MM-dd").parse(s);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }
}
