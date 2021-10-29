package com.kim.common;

import javassist.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * @author huangjie
 * @description   javassist 运行中生成和改变类
 * @date 2021/10/29
 */
public class JacassistTest {

    private CtClass ctClass;

    @BeforeEach
    public void before() throws Exception{
        //初始化类池
        ClassPool pool = ClassPool.getDefault();
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




}
