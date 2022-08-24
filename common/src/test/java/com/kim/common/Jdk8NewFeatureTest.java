package com.kim.common;

import com.kim.common.service.LamService;
import lombok.Data;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @Author kim
 * @Since 2021/4/21
 * jdk8新特性
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Jdk8NewFeatureTest {

    /**
     * lambda表达式，函数式编程
     * 当接口只有一个实现方法的时候，可以用一行代码声明他的实现类：lam -> Todo 方法实现代码
     */
    @Test
    public void lamTest(){
        LamService lamService= lam->System.out.println(lam);
        lamService.lamdaExecute("myLam");
    }

    /**
     * 时间类*/
    @Test
    public void timeTest(){
        //只有日期对象，默认pattern为yyyy-MM-dd
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate); //yyyy-MM-dd
        //只有时间对象，默认pattern为HH:mm:ss
        LocalTime localTime = LocalTime.now().withNano(0);
        System.out.println(localTime);
        //时间日期对象
        LocalDateTime localDateTime = LocalDateTime.now();
        //将时间日期对象转化为时间戳
        long milli = localDateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        System.out.printf("当前时间转化为毫秒：%s",milli);
        System.out.println();
        //时间转换器对象
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String nowDateTime = localDateTime.format(formatter);
        System.out.println(nowDateTime);
        //获取指定的日期或时间
        LocalDate targetlocalDate = LocalDate.of(2022, 8, 24);
        LocalTime targetLocalTime = LocalTime.of(12, 56, 33);
        LocalDateTime targetLocalDateTime = LocalDateTime.of(2022, 8, 24, 12, 56, 33);
        System.out.println(targetlocalDate);
        System.out.println(targetLocalTime);
        System.out.println(targetLocalDateTime);
        //加减日期
        //一周前
        LocalDateTime minus = localDateTime.minus(1, ChronoUnit.WEEKS);
        System.out.println(minus);
        //一周后
        LocalDateTime plus = localDateTime.plus(1, ChronoUnit.WEEKS);
        System.out.println(plus);
        //等价
        LocalDateTime plusWeeks = localDateTime.plusWeeks(1);
        System.out.println(plusWeeks);
        //计算两个日期之间的差值
        Period between = Period.between(LocalDate.parse("2022-08-25"), LocalDate.parse("2022-09-05"));
        System.out.printf("相差的年月日：%s年,%s月,%s天",between.getYears(),between.getMonths(),between.getDays());
        System.out.println();
        //计算两个日期相差的总天数
        long totalDays = LocalDate.parse("2022-08-25").toEpochDay()-LocalDate.parse("2022-09-05").toEpochDay();
        System.out.println(totalDays);
        //获取指定日期
        //获取当月第一天
        LocalDateTime firstDayOfMonth = localDateTime.with(TemporalAdjusters.firstDayOfMonth());
        System.out.println(firstDayOfMonth);
        //最后一天
        LocalDateTime lastDayOfMonth = localDateTime.with(TemporalAdjusters.lastDayOfMonth());
        System.out.println(lastDayOfMonth);
        //当年最后一天
        LocalDateTime lastDayOfYear = localDateTime.with(TemporalAdjusters.lastDayOfYear());
        System.out.println(lastDayOfYear);
        //当年最后一个周六
        LocalDateTime lastStaurdayOfYear = lastDayOfYear.with(TemporalAdjusters.lastInMonth(DayOfWeek.SATURDAY));
        System.out.println(lastStaurdayOfYear);



    }



    @DisplayName("流式编程")
    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    public static class StreamCode{
        @Data
        public static class Cat{
            private String name;
            private Integer age;

            public Cat(String name,Integer age){
                this.name=name;
                this.age=age;
            }


            @Override
            public boolean equals(Object obj) {
                if(obj instanceof Cat){
                    Cat cat=(Cat)obj;
                    return this.age == cat.age;
                }
                return super.equals(obj);
            }

        }

        private List<Cat> cats;

        @BeforeAll
        public void initCats(){
            if(cats == null || cats.size() == 0){
                cats = new ArrayList();
            }
            cats.add(new Cat("kitty",8));
            cats.add(new Cat("helen",9));
            cats.add(new Cat("alice",5));
            cats.add(new Cat("amy",5));

        }

        /**
         * stream流编程
         * */
        @Test
        public void example(){
            List<Integer> ages = cats.stream()
                    .filter(cat -> cat.getAge() > 6)                //筛选出年龄大于6岁的猫
                    .sorted(Comparator.comparing(Cat::getAge))      //按年龄从小到大进行排序,从大到小排序:Comparator.comparing(Cat::getAge).reversed()
                    .map(Cat::getAge)                               //只收集年龄字段
                    .collect(Collectors.toList());                  //转化成list集合

            ages.stream().forEach(age -> System.out.println(age));  //遍历集合

            cats.stream().distinct().forEach(cat -> System.out.println(cat.getAge()));
        }

        /**
         *groupby分组
         */
        @Test
        public void groupBy(){

            Map<Integer, List<Cat>> catGroups= cats.stream()
                    .collect(Collectors.groupingBy(Cat::getAge));   //根据年龄进行分组
            //遍历
            catGroups.forEach((integer, cats1) -> System.out.println(integer+": "+cats1));

        }

        /**
         * 生成流
         * */
        @Test
        public void generate() throws IOException {

            //通过集合生成流
            Stream<String> stream = Arrays.asList("a", "b", "c", "d").stream();
            //通过数组生成流,数值流
            IntStream intStream = Arrays.stream(new int[]{1, 2, 3, 4, 5});
            //数值流通过boxed方法生成对象流
            Stream<Integer> boxedStream = intStream.boxed();
            //通过of方法生成
            Stream<Integer> integerStream = Stream.of(6, 7, 8, 9);
            //通过文件生成,生成的流集合每个元素都是文件的其中一行字符，默认编码格式是utf-8
            Stream<String> lines = Files.lines(Paths.get("example.md"));
            //通过iterator生成,生成一个初始值为0，以1递增的整型数字流,它是一个无限流,通过limit截断无限流的一部分,取前10个数
            Stream<Integer> iterate = Stream.iterate(0, i -> i + 1).limit(10);
            //generate方法生成，该方法没有初始值参数，随机生成的元素组成一个流，同时也是无限流
            //本例生成一组
            final int[] index = {0};
            Stream<Integer> generate = Stream.generate(() -> {
                index[0]++;
                return index[0] / 2 == 0 ? 0 : 1;
            }).limit(10);
        }

        /**
         * 过滤出符合条件的对象
         * */
        @Test
        public void stream(){

            List<Integer> list=new ArrayList<>();
            for(int i=0;i<10;i++){
                list.add(i);
            }
            Optional<Integer> first = list.stream().filter(integer -> integer == -1).findFirst();

            System.out.println(first.isPresent());


        }
    }





}
