package com.kim.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author huangjie
 * @description 数字类型处理，包括整形、浮点型等
 * @date 2022/6/16
 */
public class NumberTest {

    /**
     * 对于基本数据类型包装类的比较，==和equals()都是比较的两个对象的地址是否相同，而不是
     * 数值，对于拆装类型的比较，==比较的是数值是否相等
     * 浮点型数据类型Double和Float没有缓存机制
     * 所有比较包装类的数值是否相等都用equal()方法，不要用==
     */

    @Test
    @DisplayName("整型常量值")
    public void IntegerFinal() {
        //Integer常量池：在-128~127之间的Integer实例将会缓存在常量池中
        //范围之外的整型数字不会被常量池缓存，只会每次新实例化一个对象
        Integer a = 127;    //实例化一个整型数据，并且缓存在常量池
        Integer b = 127;    //直接从常量池获取
        //==比较的是两个对象的地址，b从常量池获取，因此两者相等
        System.out.println("a==b:" + (a == b));

        //129超出常量池范围，因此每次都实例化不同的对象，所以两者的地址不相等
        Integer c = 129;
        Integer d = 129;
        System.out.println("c==d:" + (c == d));
        //Integer重写了Object的equals()方法，会把两者拆箱为基本类型进行比较，
        //所以比较的是值是否相等
        System.out.println("c equals d:" + c.equals(d));

    }

    @Test
    @DisplayName("浮点型精度丢失")
    public void floatFailed() {
        //由于计算机底层对浮点型数字转化为二进制的问题，导致浮点型数字精度丢失
        float a = 1.8F;
        float b = 1.7F;
        float c = 2.0F;
        float d = 1.9F;
        float a_b = a - b;
        float c_d = c - d;
        System.out.println("a-b:" + a_b);    //0.099999905
        System.out.println("c-d:" + c_d);    //0.100000024
        System.out.println(a_b == c_d);
    }

    @Test
    @DisplayName("BigDecimal的使用")
    public void bigDecimalUse() {

        BigDecimal a = new BigDecimal("7.0");
        BigDecimal b = new BigDecimal("3.0");
        BigDecimal c = new BigDecimal("4.4444");
        //加减乘除
        System.out.println("加："+a.add(b));
        System.out.println("减："+a.subtract(b));
        System.out.println("乘："+a.multiply(b));
        try {
            System.out.println("除不尽："+a.divide(b));
        } catch (Exception e) {
            System.out.println("除不尽抛出异常");
            e.printStackTrace();
        }
        //scale为保留2位小数，RoundingMode为保留规则，HALF_UP代表四舍五入
        System.out.println("除法保留两位小数："+a.divide(b,2, RoundingMode.HALF_UP));

        //比较大小
        System.out.println("a>b:"+a.compareTo(b));//大于则返回1
        System.out.println("b<c:"+b.compareTo(c));//小于则返回-1
        System.out.println("a=a:"+a.compareTo(a));//等于则返回0

        //保留几位小数，ROUND_UP：无条件进1，DOWN：无条件舍弃，HALF_UP：四舍五入
        BigDecimal d = c.setScale(3, RoundingMode.DOWN);//保留3位小数，无条件舍弃
        System.out.println("d="+d);

        /**使用注意事项
         * 严禁使用new BigDecimal(double val)的构造方法进行初始化浮点型数据，否则会产生丢失精度问题
         * new BigDecimal(0.1F),实际值为0.10000000149
         * 使用String的构造方法或者使用BigDecimal.valueOf(double val)方法
         * */

        System.out.println("丢失精度："+new BigDecimal(0.1F));
        System.out.println("BigDecimal.valueOf(double val)方法："+BigDecimal.valueOf(0.1));

    }

    @Test
    @DisplayName("位移")
    public void displacement(){
        //将5转化为二进制后整体向左移动两位，左边丢弃，右边补0，等值运算：5*2的移位位数次方：5*2的2次方
        int a = 5 << 2;
        //将11转化为二进制后整体向右移动2位，右边丢弃，左边补符号位，即1，等值运算：11/2的移位位数次方：11/2的2次方
        int b = 11 >> 2;
        System.out.println("带符号左移："+a);
        System.out.println("带符号右移："+b);
        //类似于 >>，区别是补0而不是补符号位
        int c = 10 >>> 2;
        System.out.println("无符号右移:"+c);

        //将10000*100用位运算来替代
        int d = (10000 << 6) + (10000 << 5) + (10000 << 2);
        System.out.println("10000*100="+d);
    }


}
