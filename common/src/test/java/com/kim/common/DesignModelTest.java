package com.kim.common;

import com.kim.common.designmode.factory.*;
import com.kim.common.designmode.group.clear.ClearBranch;
import com.kim.common.designmode.group.clear.ClearClearRoot;
import com.kim.common.designmode.group.clear.ClearComponent;
import com.kim.common.designmode.group.clear.ClearLeaf;
import com.kim.common.designmode.group.safe.SafeBranch;
import com.kim.common.designmode.group.safe.SafeLeaf;
import com.kim.common.designmode.group.safe.SafeRoot;
import com.kim.common.designmode.pojo.Car;
import com.kim.common.designmode.builder.CarBuilder;
import com.kim.common.designmode.pojo.Computer;
import com.kim.common.designmode.proxy.*;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author huangjie
 * @description
 * @date 2021/10/27
 */
@DisplayName("设计模式测试")
public class DesignModelTest {


    @Test
    @DisplayName("构建者模式")
    public void builder() {
        //构建汽车
        Car car = new CarBuilder().id(1).engine("发动机").body("车身").wheel("轮胎").electronic("电器设备").build();
        System.out.println(car);
        //构建电脑
        System.out.println("------利用lombok @Builder注解实现构建者生产对象");
        Computer computer = Computer.builder().cpu("英特尔").brand("lenovo").memory("16G").name("笔记本电脑").size("15寸").videoCard("独显").build();
        System.out.println(computer);
    }

    @Test
    @DisplayName("简单工厂模式")
    public void simpleFactory(){
        SimpleGunFactory simpleGunFactory=new SimpleGunFactory();
        Gun ak47 = simpleGunFactory.createGun("ak47");
        ak47.fire();
        Gun m16 = simpleGunFactory.createGun("m16");
        m16.fire();
        Gun g36 = simpleGunFactory.createGun("g36");
        g36.fire();
    }

    @Test
    @DisplayName("静态方法工厂模式")
    public void staticMethodFactory(){
        Gun ak47 = StaticMethodGunFactory.createAk47();
        ak47.fire();
        Gun m16 = StaticMethodGunFactory.createM16();
        m16.fire();
        Gun g36 = StaticMethodGunFactory.createG36();
        g36.fire();
    }

    @Test
    @DisplayName("多个工厂方法模式")
    public void multiMethodFactory(){

        MultiMethodGunFactory multiMethodGunFactory=new MultiMethodGunFactory();
        Gun ak47 = multiMethodGunFactory.createAk47();
        ak47.fire();
        Gun m16 = multiMethodGunFactory.createM16();
        m16.fire();
        Gun g36 = multiMethodGunFactory.createG36();
        g36.fire();
    }

    @Test
    @DisplayName("抽象方法工厂模式")
    public void abstractMethodFactory(){
        GunFactory gunFactory1=new Ak47Factory();
        Gun gun1 = gunFactory1.createGun();
        GunFactory gunFactory2=new M16Factory();
        Gun gun2=gunFactory2.createGun();
        GunFactory gunFactory3=new G36Factory();
        Gun gun3 = gunFactory3.createGun();
        gun1.fire();
        gun2.fire();
        gun3.fire();

    }


    @Test
    @DisplayName("静态代理模式")
    public void staticProxy(){

        CarService carService=new CarServiceImpl();
        CarService carServiceProxy = new CarServiceStaticProxyImpl(carService);
        System.out.println("--------------原生方法---------");
        Car car = carService.get(1);
        System.out.println("--------------代理方法---------");
        Car car2 = carServiceProxy.get(1);
    }

    @Test
    @DisplayName("jdk的动态代理,自定义代理对象泛型构建器实现")
    //jdk代理要求被代理对象必须实现一个接口
    public void jdkDynamicProxy(){
        //实例化被代理的对象
        CarService carService=new CarServiceImpl();
        //实例化jdk动态代理构建器，声明被代理的对象的实现接口泛型,注入被代理对象
        JdkDynamicProxyBuilder<CarService> builder=new JdkDynamicProxyBuilder<>(carService);
        //构建代理对象，传入代理方法实现
        CarService carServiceProxy = builder.build(new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("前置处理");
                Object result = null;
                try {
                    result = method.invoke(carService, args);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("异常处理");
                } finally {
                    System.out.println("后置处理");
                }

                return result;
            }
        });
        carServiceProxy.get(1);

    }

    @Test
    @DisplayName("jdk动态代理，原生实现")
    public void jdkDynamicOriginal(){
        //实例化被代理的对象
        CarService carService=new CarServiceImpl();
        CarService carServiceProxy = (CarService)Proxy.newProxyInstance(carService.getClass().getClassLoader(), carService.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("前置处理");
                Object result = null;
                try {
                    result = method.invoke(carService, args);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("异常处理");
                } finally {
                    System.out.println("后置处理");
                }

                return result;
            }
        });
        carServiceProxy.get(1);


    }

    @Test
    @DisplayName("cglib代理，利用自定义构建器生成代理类")
    public void cglibProxy(){
        ComputerService computerService=new ComputerService();
        CglibProxyBuilder<ComputerService> cglibProxyBuilder=new CglibProxyBuilder<>(computerService);
        ComputerService computerServiceProxy = cglibProxyBuilder.build(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println("前置处理");
                try {
                    Object result = method.invoke(computerService, objects);
                    return result;
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("异常处理");
                } finally {
                    System.out.println("后置处理");
                }
                return null;
            }
        });
        computerServiceProxy.get(1);


    }

    @Test
    @DisplayName("组合模式：安全形式")
    public void groupBySafe(){

        SafeRoot safeRoot =new SafeRoot("父节点");
        SafeBranch b1=new SafeBranch("分支1", safeRoot);
        SafeBranch b1_1=new SafeBranch("分支1-1",b1);
        SafeLeaf l1=new SafeLeaf("叶子1-1-1",b1_1);
        SafeLeaf l2=new SafeLeaf("叶子1-1-2",b1_1);
        SafeLeaf l3=new SafeLeaf("叶子1-1-3",b1_1);
        safeRoot.addChild(b1);
        b1.addChild(b1_1);
        b1_1.addChild(l1);
        b1_1.addChild(l2);
        b1_1.addChild(l3);
        safeRoot.operation("action:");
    }

    @Test
    @DisplayName("组合模式：透明形式")
    public void groupByClear(){

        ClearComponent clearRoot = new ClearClearRoot("父节点");
        ClearComponent b1=new ClearBranch("分支1", clearRoot);
        ClearComponent b1_1=new ClearBranch("分支1-1",b1);
        ClearComponent l1=new ClearLeaf("叶子1-1-1",b1_1);
        ClearComponent l2=new ClearLeaf("叶子1-1-2",b1_1);
        ClearComponent l3=new ClearLeaf("叶子1-1-3",b1_1);
        clearRoot.addChild(b1);
        b1.addChild(b1_1);
        b1_1.addChild(l1);
        b1_1.addChild(l2);
        b1_1.addChild(l3);
        clearRoot.operation("action:");
    }



}
