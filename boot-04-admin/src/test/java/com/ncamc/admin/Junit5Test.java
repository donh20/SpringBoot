package com.ncamc.admin;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
@DisplayName("测试Junit5功能测试类")
public class Junit5Test {

    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * 测试前置条件
     */
    @DisplayName("测试前置条件")
    @Test
    void testassumptions(){
        Assumptions.assumeTrue(false,"结果不是true");
        System.out.println("111111");

    }

    @DisplayName("测试简单断言")
    @Test
    void testSimpleAssertions(){
        int cal = cal(2,3);
        assertEquals(5,cal,"业务逻辑计算失败");

        Object obj1 = new Object();
        Object obj2 = new Object();
        assertSame(obj1,obj2,"对象不一样");

    }

    @Test
    @DisplayName("array")
    void testArray(){
        assertArrayEquals(new int[]{1,2},new int[]{1,2});
    }

    @Test
    @DisplayName("组合断言")
    void all(){
        /**
         * 声明的所有断言都成功才会成功
         */
        assertAll("test",
                ()-> assertTrue(true && true,"结果不为true"),
                ()-> assertEquals(1,2,"结果不是1")
                );
        System.out.println("=========");
    }

    int cal(int i, int j){
        return i+j;
    }

    @DisplayName("异常断言")
    @Test
    void testException(){
        assertThrows(ArithmeticException.class,
                ()-> {int i = 10/2;},
                "业务逻辑居然没有出现异常");
    }

    @Test
    @DisplayName("测试displayname注解")
    void testDisplayName(){
        System.out.println("1");
        System.out.println(jdbcTemplate);
    }


    @DisplayName("快速失败")
    @Test
    void testFail(){
        //xxxxx
        if(1 == 2){
            fail("测试失败");
        }
    }

    @Disabled
    @Test
    @DisplayName("测试方法2")
    void test2(){
        System.out.println("2");
    }

    @BeforeEach
    void testBeforeEach(){
        System.out.println("测试开始.");
    }

    @AfterEach
    void testAfterEach(){
        System.out.println("测试结束..");
    }

    @BeforeAll
    static void testBeforeAll(){
        System.out.println("所有测试就要开始了...");
    }

    @AfterAll
    static void testAfterAll(){
        System.out.println("所有测试就要结束了...");
    }

    /**
     * 规定方法超时时间
     * @throws InterruptedException
     */
    @Timeout(value = 600,unit = TimeUnit.MILLISECONDS)
    @Test
    void testTimeout() throws InterruptedException {
        Thread.sleep(500);
    }

    @RepeatedTest(5)
    @Test
    void test3(){
        System.out.println(3);
    }
}
