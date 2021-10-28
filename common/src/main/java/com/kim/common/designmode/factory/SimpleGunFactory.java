package com.kim.common.designmode.factory;

/**
 * @author huangjie
 * @description   简单工厂模式
 * @date 2021/10/28
 */
public class SimpleGunFactory {


    public Gun createGun(String gunType){
        Gun gun;
        switch (gunType){
            case "ak47":
                gun=new Ak47();
                break;
            case "m16":
                gun=new M16();
                break;
            case "g36":
                gun=new G36();
                break;
            default:
                gun = null;
        }
        return gun;
    }
}
