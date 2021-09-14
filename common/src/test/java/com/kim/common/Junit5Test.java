package com.kim.common;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

/**
 * @author huangjie
 * @description
 * @date 2021-9-7
 */
public class Junit5Test {

    /**
     * 常用注解:
     *
     * @BeforeEach：在每个单元测试方法执行前都执行一遍
     * @BeforeAll：在每个单元测试方法执行前执行一遍（只执行一次）
     * @AfterAll:每个单元测试方法执行之后执行一遍(只执行一次)
     * @AfterEach:每个单元测试方法之后执行一遍
     * 使用以上注解方法的话要在测试类上加此注解:@TestInstance(TestInstance.Lifecycle.PER_CLASS)
     * @DisplayName("商品入库测试")：用于指定单元测试的名称
     * @Disabled：当前单元测试置为无效，即单元测试时跳过该测试
     * @RepeatedTest(n)：重复性测试，即执行n次
     * @ParameterizedTest：参数化测试，
     * @ValueSource(ints = {1, 2, 3})：参数化测试提供数据
     */

    @Test
    @DisplayName("测试判断true和false")
    public void testTrueOrFalse() {
        //判断表达式是否为true，如果是false则抛出异常
        Assertions.assertTrue(1 == 1);
        //判断表达式是否为false，如果是true则抛出异常
        Assertions.assertFalse(1 == 1);
    }

    @Test
    @DisplayName("判断两个对象是否相等")
    public void testEquals() {
        Assertions.assertEquals("expected", "actual");
    }

    @Test
    @DisplayName("判断是否为null")
    public void testIsNull() {
        Assertions.assertNotNull(new Junit5Test());
        Assertions.assertNull(null);
    }

    @Test
    @DisplayName("异常类型约束")
    public void throwsException() {
        //若代码抛出异常类型不符合预期，则抛出异常
        RuntimeException occurErr = Assertions.assertThrows(RuntimeException.class, () -> {
            throw new RuntimeException("occur err");
        });
        //occurErr.printStackTrace();

        IllegalArgumentException myException = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            throw new RuntimeException("throw a err exception");
        });
        myException.printStackTrace();

    }

    @Test
    @DisplayName("判断代码是否超时")
    public void timeout() {
        Assertions.assertTimeout(Duration.ofSeconds(3), () -> {
            Thread.sleep(2 * 1000);
        });
    }

    @Test
    @DisplayName("组合断言，内部所有断言通过才算通过")
    public void assertAll() {

        Assertions.assertAll(() -> {
            Assertions.assertTrue(1 == 1);
        }, () -> {
            Assertions.assertEquals(1L, 1L);
        }, () -> {
            Assertions.assertTimeout(Duration.ofSeconds(1), () -> {
                Thread.sleep(1 * 1000);
            });
        });

    }

    private static int count;

    @RepeatedTest(5)
    @DisplayName("重复性测试，重复5次")
    public void repeatedFiveTest() {
        count++;
        System.out.println(count);
    }

    @DisplayName("参数化测试")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    public void parametersTest(int param) {
        Assertions.assertEquals(param % 2, 0);
        System.out.println(param + "能被2整除");
    }

    @DisplayName("嵌套测试")
    @SpringBootTest
    public static class InnerTest {

        @Nested
        @DisplayName("内嵌测试类")
        @TestInstance(TestInstance.Lifecycle.PER_CLASS)         //@BeforeEach,@BeforeAll等注解生效的配套注解
        class InnerInnerTest {

            @Test
            @DisplayName("内嵌测试")
            public void innerTest() {
                Assertions.assertEquals(1.0, 2);
            }


            @BeforeAll
            public void beforeAll(){
                System.out.println("before test");
            }
        }
    }

}
