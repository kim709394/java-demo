package com.kim.common;

import com.kim.common.entity.A;
import com.kim.common.entity.B;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Author kim
 * 属性拷贝工具
 * @Since 2021/5/19
 */
public class PropertiesCopyTest {

    /**
     * idea generateO2O插件例子
     */
    @Nested
    @DisplayName("idea generateO2O插件例子")
    public static class GenerateO2O {
        //利用generateO2O插件生成属性拷贝方法
        public static B a2b(A a) {
            if (a == null) {
                return null;
            }
            B b = new B();
            b.setName(a.getName());
            b.setId(a.getId());
            b.setColors(a.getColors());
            return b;
        }

        @Test
        public void copyTest() {
            A a = new A();
            a.setId(1);
            a.setName("mike");
            a.setColors(Arrays.asList("blue", "yellow", "red"));
            B b = a2b(a);
            System.out.println(b.getId());
            System.out.println(b.getName());
            b.getColors().stream().forEach(color -> System.out.println(color));
        }

        /**
         * 对于有父类的实体属性拷贝方法生成，无法将父类的属性拷贝方法生成出来，解决方法:
         * 1. 手动编写父类的属性set/get拷贝方法，这种方法适用于父类属性比较少的情况。
         * 2. 在父类生成一个copy方法，然后把生成的set/get拷贝方法代码赋值到实体属性拷贝方法里面再进行修改，这种方法适用于父类属性比较多的情况。
         */
        public static B a2bWithC(A a) {
            if (a == null) {
                return null;
            }
            B b = new B();
            b.setName(a.getName());
            b.setId(a.getId());
            b.setColors(a.getColors());
            b.setAge(a.getAge());
            return b;
        }

        @Test
        public void a2bWithCTest() {
            A a = new A();
            a.setId(1);
            a.setName("mike");
            a.setColors(Arrays.asList("blue", "yellow", "red"));
            a.setAge(18);
            B b = a2bWithC(a);
            System.out.println(b.getId());
            System.out.println(b.getName());
            System.out.println(b.getAge());
            b.getColors().stream().forEach(color -> System.out.println(color));
        }
    }

    @Nested
    @DisplayName("mapstruct工具进行属性拷贝接口生成")
    public static class MapStruct {

        //DO类
        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class UserDO {
            private Integer id;
            private String name;
            private Gender gender;
            private Date createTime;
            private Date deleteTime;

            public enum Gender {
                Male(1, "男"), Female(0, "女");

                public Integer code;
                public String name;

                Gender(Integer code, String name) {
                    this.code = code;
                    this.name = name;

                }
            }
        }

        //VO类
        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class UserVO {
            private Integer id;
            private String name;
            private String gender;
            private String createTime;
        }

        //定义转换mapper接口
        @Mapper
        public interface UserMapper {

            UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

            /**
             * 针对有特殊格式转换或者属性名称不对应的，可以手动指定转换格式和名称对应
             * 默认是根据同名称同类型的属性自动映射进行转换
             */
            @Mapping(source = "gender.name", target = "gender")
            @Mapping(source = "createTime", target = "createTime", dateFormat = "yyyy-MM-dd hh:mm:ss")
            UserVO userDO2UserVO(UserDO userDO);

            /**
             * list集合转换，转换规则和单对象的规则关联
             * */
            List<UserVO> userDOs2UserVOs(List<UserDO> userDOs);
        }

        @Test
        @DisplayName("DO转换为VO")
        public void userDO2VO() {
            UserDO userDO = UserDO.builder().id(1).name("mike").gender(UserDO.Gender.Male).createTime(new Date()).build();
            UserVO userVO = UserMapper.INSTANCE.userDO2UserVO(userDO);
            System.out.printf("%s%n",userVO);
        }

        @Test
        @DisplayName("集合转换")
        public void userDOs2VOs(){
            List<UserDO> userDOs=new ArrayList<>();
            UserDO userDO=UserDO.builder().id(2).name("helen").createTime(new Date()).gender(UserDO.Gender.Female).deleteTime(new Date()).build();
            userDOs.add(userDO);
            List<UserVO> userVOs=UserMapper.INSTANCE.userDOs2UserVOs(userDOs);
            System.out.printf("%s%n",userVOs);
        }


        /**
         * 多个对象融合转换为一个对象
         * */
        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Engine{

            private Integer power;

        }

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Wheel{
            private Integer size;
        }

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Car{
            private Integer enginePower;
            private Integer wheelSize;
        }

        @Mapper
        public interface CarMapper{

            CarMapper INSTANCE=Mappers.getMapper(CarMapper.class);

            @Mapping(source = "engine.power",target = "enginePower")
            @Mapping(source = "wheel.size",target="wheelSize")
            Car engineFuseWheel2Car(Engine engine,Wheel wheel);

        }

        @Test
        @DisplayName("两个不同对象融合成另一个对象")
        public void fuseTwoObject(){

            Engine engine=Engine.builder().power(300).build();
            Wheel wheel=Wheel.builder().size(24).build();
            Car car=CarMapper.INSTANCE.engineFuseWheel2Car(engine,wheel);
            System.out.printf("%s%n",car);

        }

    }


}
