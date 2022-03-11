package com.kim.spring.mvc.controller;

import com.kim.spring.mvc.pojo.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Arrays;
import java.util.Date;

/**
 * @author huangjie
 * @description  前后的分离，后端接口控制器,crud
 * @date 2022/3/10
 */
//@RestController注解相当于在每个controller方法上加上了@ResponseBody注解
@RestController
@RequestMapping("/order")
public class OrderController {


    @PostMapping("/add")
    public Order add(@RequestBody Order order){
        return order;
    }

    //添加了@RequestParam注解的参数则该参数为必传
    @GetMapping("/get/id")
    public Order get(@RequestParam Integer id){
        Order order=new Order();
        order.setId(id);
        order.setName("订单1");
        order.setCreateTime(new Date());
        return order;
    }

    //Restfull风格get请求
    @GetMapping("/get/{name}")
    public Order getByName(@PathVariable(value="name") String name){
        Order order=new Order();
        order.setId(2);
        order.setName(name);
        order.setCreateTime(new Date());
        return order;
    }

    //对servlet原生api的支持
    @GetMapping("/servlet")
    public String servlet(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        System.out.println(request);
        System.out.println(response);
        System.out.println(session);
        return "success";
    }

    //全局异常捕捉
    @GetMapping("/exception")
    public String globalException(){
        int i=1/0;
        return "success";
    }

    //文件上传
    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file){
        System.out.println(file.getOriginalFilename());
        return "success";
    }

    //批量文件上传
    @PostMapping("/uploads")
    public String uploads(@RequestParam MultipartFile[] files){
        for (MultipartFile file:files
             ) {
            System.out.println(file.getOriginalFilename());
        }
        return "success";
    }

    //文件下载：方式一
    @GetMapping("/download1")
    public void download1(HttpServletResponse response) throws Exception {

        response.setCharacterEncoding("UTF-8");
        String fileName=new String("example.xlsx".getBytes("UTF-8"),"iso-8859-1");
        response.setContentType("application/octet-stream; charset=UTF-8");
        //设置Content-Disposition
        response.setHeader("Content-Disposition", "attachment;filename="+fileName);
        try (InputStream in=new FileInputStream("c:/example.xlsx");
             OutputStream out=response.getOutputStream();
        ){
            byte[] b=new byte[1024];
            int len=0;
            while ((len=in.read(b))>0){
                out.write(b,0,len);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //文件下载，方式二
    @GetMapping("/download2")
    public ResponseEntity<byte[]> download2() throws Exception{
        String fileName=new String("example.xlsx".getBytes("UTF-8"),"iso-8859-1");
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment",fileName);
        try (InputStream in=new FileInputStream("c:/example.xlsx");){
            return new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(in),headers, HttpStatus.OK);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<byte[]>(new byte[0],headers, HttpStatus.INTERNAL_SERVER_ERROR);

    }



}
