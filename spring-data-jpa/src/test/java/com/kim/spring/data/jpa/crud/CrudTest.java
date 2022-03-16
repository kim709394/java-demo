package com.kim.spring.data.jpa.crud;

import com.kim.spring.data.jpa.dao.UserDao;
import com.kim.spring.data.jpa.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.criteria.*;
import javax.swing.text.html.HTMLDocument;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author huangjie
 * @description
 * @date 2022-03-16
 */
@ContextConfiguration(locations = {"classpath:spring-application.xml"})
@RunWith(SpringJUnit4ClassRunner.class)

public class CrudTest {


    @Autowired
    private UserDao userDao;

    //新增
    @Test
    public void testAdd(){
        User user1=new User();
        user1.setName("mike");
        user1.setCreatedAt(new Date());
        user1.setDeletedAt(new Date());
        User user2=new User();
        user2.setName("lucy");
        user2.setCreatedAt(new Date());
        user2.setDeletedAt(new Date());
        userDao.save(user1);
        userDao.save(user2);
    }

    //修改
    @Test
    public void testUpdate(){

        Optional<User> userWrapper = userDao.findById(1);
        User user = userWrapper.get();
        user.setName("john");
        userDao.save(user);
    }

    //查询单个
    @Test
    public void testFindOne(){
        Optional<User> userOptional = userDao.findById(1);
        System.out.println(userOptional.get());
        User user=new User();
        user.setName("john");
        Example<User> example=Example.of(user);
        Optional<User> one = userDao.findOne(example);
        System.out.println(one.get());

    }

    //查询多个
    @Test
    public void testFindAll(){

        List<User> all = userDao.findAll();
        System.out.println(all);

    }


    //hql查询
    @Test
    public void testHqlQuery(){
        List<User> john = userDao.selectByIdAndName(1, "john");
        john.stream().forEach(user -> System.out.println(user));
    }

    //原生sql查询
    @Test
    public void testNativeQuery(){
        List<User> john = userDao.selectByNative(1, "john");
        System.out.println(john);
    }


    //约定命名规则查询
    @Test
    public void testFindBy(){
        List<User> john = userDao.findByIdAndNameLike(1, "john");
        System.out.println(john);
    }

    //分页查询
    @Test
    public void testPage(){
        //从第0条开始，读取2条，以id字段进行排序
        Pageable pageable = PageRequest.of(0, 2, Sort.by(Sort.Direction.DESC, "id"));
        User user=new User();
        user.setName("john");
        //example api构建查询条件
        Example<User> example=Example.of(user);
        //Specification对象构建查询条件
        /*Specification<User> specification=new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //获取查询条件属性
                Path<Object> id = root.get("id");
                Path<Object> name = root.get("name");
                //条件一：等值查询
                Predicate predicate1 = criteriaBuilder.equal(id, 1);
                //条件二：模糊查询
                Predicate predicate2 = criteriaBuilder.like(name.as(String.class), "mike");
                //合并两个条件
                return criteriaBuilder.and(predicate1,predicate2);
            }
        };
        Page<User> page = userDao.findAll(specification, pageable);*/
        Page<User> page = userDao.findAll(example, pageable);
        System.out.println("总页数:"+        page.getTotalPages());
        System.out.println("总记录数："+page.getTotalElements());
        System.out.println("数据集合：");
        page.get().forEach(user1 -> {
            System.out.println(user1);
        });



    }


    //删除
    @Test
    public void testDelete(){
        userDao.deleteById(1);
    }





}
