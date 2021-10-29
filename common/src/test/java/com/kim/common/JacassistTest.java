package com.kim.common;

import javassist.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.swing.border.EmptyBorder;
import java.io.EOFException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author huangjie
 * @description   javassist 运行中生成和改变类
 * @date 2021/10/29
 */
public class JacassistTest {

    private CtClass ctClass;
    private ClassPool pool;

    @BeforeEach
    public void before() throws Exception{
        //初始化类池
        ClassPool pool = ClassPool.getDefault();
        this.pool = pool;
        //创建一个类
        CtClass ctClass = pool.makeClass("com.kim.common.javassist.Example");
        this.ctClass=ctClass;
        //创建一个成员变量,变量名为myMember
        CtField myMember = new CtField(pool.get("java.lang.String"), "myMember", ctClass);
        //设置访问修饰符为private
        myMember.setModifiers(Modifier.PRIVATE);
        //将成员变量设置进类里面并且设置初始值为myMember
        ctClass.addField(myMember,CtField.Initializer.constant("myMember"));
        //生成set、get方法
        ctClass.addMethod(CtNewMethod.setter("setMyMember",myMember));
        ctClass.addMethod(CtNewMethod.getter("getMyMember",myMember));

        //生成无参构造方法
        CtConstructor constrNoParam = new CtConstructor(new CtClass[]{}, ctClass);
        constrNoParam.setBody("{}");
        ctClass.addConstructor(constrNoParam);

        //生成有参构造方法,只有一个String类型的参数
        CtConstructor constrHasParam = new CtConstructor(new CtClass[]{pool.get("java.lang.String")}, ctClass);
        //添加方法体,$0表示this , $1,$2,$3表示第一第二第三个参数
        constrHasParam.setBody("{$0.myMember = $1;}");
        ctClass.addConstructor(constrHasParam);

        //添加一个自定义方法,无返回值，方法名:myDefMethod,参数只有一个Integer
        CtMethod ctMethod=new CtMethod(CtClass.voidType,"myDefMethod",new CtClass[]{CtClass.intType},ctClass);
        //访问修饰符是public
        ctMethod.setModifiers(Modifier.PUBLIC);
        //添加方法体
        ctMethod.setBody("{System.out.println(\"传入的参数是:\"+$1);System.out.println(\"哈哈\");}");
        ctClass.addMethod(ctMethod);
    }

    @Test
    @DisplayName("利用代码生成一个类")
    public void createClass() throws NotFoundException, CannotCompileException, IOException {

        //编译到类路径下，将会生成.class文件
        ctClass.writeFile(this.getClass().getClassLoader().getResource("").getPath());
        System.out.println("代码生成完成");

    }

    @Test
    @DisplayName("实例化自定义类")
    public void instanceDefClass() throws CannotCompileException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

        Object o = ctClass.toClass().newInstance();
        //利用反射调用方法
        Method setMyMember = o.getClass().getMethod("setMyMember", String.class);
        setMyMember.invoke(o,"newMember");
        Method getMyMember = o.getClass().getMethod("getMyMember");
        Object result = getMyMember.invoke(o);
        System.out.println(result);

    }

    @Test
    @DisplayName("读取生成.class文件实例化自定义类")
    public void instanceDefClassByReadClassFile() throws NotFoundException, CannotCompileException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

        //添加类加载路径,执行这一步可以不用生成.class文件
        pool.appendClassPath(this.getClass().getClassLoader().getResource("").getPath());
        CtClass ctClass = pool.get("com.kim.common.javassist.Example");
        Object o = ctClass.toClass().newInstance();
        Method myDefMethod = o.getClass().getMethod("myDefMethod", int.class);
        myDefMethod.invoke(o,123);
    }

    public interface ExampleInterface{
        void myDefMethod(int arg);
    }

    @Test
    @DisplayName("通过实现之前定义好的接口来生成类")
    public void defClassImplInterface() throws Exception{
        //加载接口
        CtClass exampleI = pool.get("com.kim.common.JacassistTest$ExampleInterface");
        //让生成的类实现接口
        ctClass.setInterfaces(new CtClass[]{exampleI});
        //实例化生成的类，用接口接收，这样以后可以用接口的引用来调用方法，减少反射的开销
        ExampleInterface exampleInterface = (ExampleInterface) ctClass.toClass().newInstance();
        exampleInterface.myDefMethod(12);

    }

    public static class UserService{

        public void show(){
            System.out.println("my name is mike");

        }
    }

    @Test
    @DisplayName("修改某个已定义好的类")
    public void modifyClass() throws Exception {
        //加载目标类
        CtClass targetClass = pool.get("com.kim.common.JacassistTest$UserService");
        //获取目标类的方法
        CtMethod show = targetClass.getDeclaredMethod("show");
        //在方法执行前插入执行代码
        show.insertBefore("{System.out.println(\"insert before\");}");
        //在方法执行后插入执行代码
        show.insertAfter("{System.out.println(\"insert after\");}");
        //新增一个方法
        CtMethod newMethod = new CtMethod(CtClass.voidType, "newMethod", new CtClass[]{}, targetClass);
        newMethod.setModifiers(Modifier.PUBLIC);
        newMethod.setBody("{System.out.println(\"新增加的方法\");}");
        targetClass.addMethod(newMethod);

        //实例化类并调用目标方法和新增的方法
        Object o = targetClass.toClass().newInstance();
        Method show1 = o.getClass().getMethod("show");
        show1.invoke(o);
        Method newMethod1 = o.getClass().getMethod("newMethod");
        newMethod1.invoke(o);

    }



}
