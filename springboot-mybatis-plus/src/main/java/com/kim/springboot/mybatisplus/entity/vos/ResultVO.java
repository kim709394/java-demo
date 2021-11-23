package com.kim.springboot.mybatisplus.entity.vos;


import com.kim.springboot.mybatisplus.consts.ErrorEnum;

/**
 * kim
 * 2021/4/7
 */
public class ResultVO<T> {

    private Integer code;
    private String msg;
    private T data;


    public ResultVO(){

    }

    public ResultVO(Integer code, String msg, T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    //成功
    public static ResultVO success(Object data){
        return new ResultVO(ErrorEnum.CODE_2000.code,ErrorEnum.CODE_2000.msg, data);
    }

    public static ResultVO success(){
        return new ResultVO(ErrorEnum.CODE_2000.code,ErrorEnum.CODE_2000.msg, null);
    }



    //服务端错误
    public static ResultVO serverErr(){
        return result(ErrorEnum.CODE_5001.code, ErrorEnum.CODE_5001.msg,null);
    }

    public static ResultVO result(Integer code,String msg,Object data){
        return new ResultVO(code,msg,data);
    }

    public static ResultVO result(ErrorEnum errorEnum,Object data){
        return result(errorEnum.code,errorEnum.msg,data);
    }

    public static ResultVO result(Integer code,String msg){
        return result(code,msg,null);
    }

    public static ResultVO result(ErrorEnum errorEnum){
        return result(errorEnum.code,errorEnum.msg,null);
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}