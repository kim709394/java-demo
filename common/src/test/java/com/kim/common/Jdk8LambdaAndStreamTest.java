package com.kim.common;

import com.kim.common.service.LamService;
import lombok.Data;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @Author kim
 * @Since 2021/4/21
 * lambda表达式和流编程测试
 */
public class Jdk8LambdaAndStreamTest {

    /**
     * lambda表达式，
     * 当接口只有一个实现方法的时候，可以用一行代码声明他的实现类：lam -> Todo 方法实现代码
     */
    @Test
    public void lamTest(){
        LamService lamService= lam->System.out.println(lam);
        lamService.lamdaExecute("myLam");
    }

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

    @Before
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
     * 流编程的使用
     * */
    @Test
    public void stream(){



    }


}
