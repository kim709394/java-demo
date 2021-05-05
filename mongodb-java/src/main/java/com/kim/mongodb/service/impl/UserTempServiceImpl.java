package com.kim.mongodb.service.impl;

import com.kim.mongodb.entity.UserDO;
import com.kim.mongodb.entity.UserPageIn;
import com.kim.mongodb.entity.UserPageOut;
import com.kim.mongodb.service.UserTempService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author huangjie
 * @description
 * @date 2021-5-5
 */
@Service
public class UserTempServiceImpl implements UserTempService {

    @Autowired
    private MongoTemplate template;   //mongodb操作模板


    @Override
    public String add(UserDO userDO) {
        userDO=template.insert(userDO);
        return userDO.getId();
    }

    @Override
    public void update(UserDO userDO) {
        Query query=new Query(Criteria.where("id").is(userDO.getId()));
        Update update=new Update();

        if(StringUtils.isNotEmpty(userDO.getName())){
            update.set("name",userDO.getName());
        }
        if(StringUtils.isNotEmpty(userDO.getTelephone())){
            update.set("telephone",userDO.getTelephone());
        }
        if(null!=userDO.getAge()){
            update.set("age",userDO.getAge());
        }
        template.updateFirst(query,update, UserDO.class);
    }

    @Override
    public void delete(String id) {
        template.remove(new Query(Criteria.where("id").is(id)),UserDO.class);
    }

    @Override
    public void deleteByLogic(String id) {
        Update update = new Update();
        update.set("deleted",true);
        template.updateFirst(new Query(Criteria.where("id").is(id)),update,UserDO.class);
    }

    @Override
    public UserDO get(String id) {
        return template.findById(id,UserDO.class);
    }

    @Override
    public List<UserDO> list() {
        return template.findAll(UserDO.class);
    }

    @Override
    public UserPageOut doPage(UserPageIn userPageIn) {
        //获取查询条件
        Query query = getQuery(userPageIn);
        //查询总记录数
        long count = template.count(query, UserDO.class);
        query.with(Sort.by(Sort.Direction.DESC,"createdTime"))   //倒序排序
                .skip((userPageIn.getPageNo()-1)*userPageIn.getPageSize())       //偏移量
                .limit(userPageIn.getPageSize());    //扫面量
        List<UserDO> userDOs = template.find(query, UserDO.class);
        UserPageOut userPageOut=new UserPageOut();
        userPageOut.setCount(Integer.valueOf(count+""));
        userPageOut.setUserDOs(userDOs);
        return userPageOut;
    }

    //获取查询条件
    private Query getQuery(UserPageIn userPageIn){
        Query query=new Query();
        Criteria startTimeCriteria=null;
        Criteria endTimeCriteria=null;
        if(null!=userPageIn.getCreatedTimeStart()){
            startTimeCriteria=Criteria.where("createdTime").gte(userPageIn.getCreatedTimeStart());  //大于等于
        }
        if(null!=userPageIn.getCreatedTimeEnd()){
            endTimeCriteria=Criteria.where("createdTime").lte(userPageIn.getCreatedTimeEnd());    //小于等于
        }
        if(null!=startTimeCriteria&&null!=endTimeCriteria){
            query.addCriteria(new Criteria().andOperator(startTimeCriteria,endTimeCriteria));      //将两个条件以and连接
        }else if(null!=startTimeCriteria){
            query.addCriteria(startTimeCriteria);
        }else if(null!=endTimeCriteria){
            query.addCriteria(endTimeCriteria);
        }
        if(StringUtils.isNotEmpty(userPageIn.getName())){
            query.addCriteria(Criteria.where("name").regex(userPageIn.getName()));   //字符串模糊查询，类似mysql的'%ss%'
        }
        return query;
    }

    @Override
    public void groupBy() {

        List<AggregationOperation> aggregationOperations=new ArrayList<>();
        //添加过滤查询条件
        aggregationOperations.add(Aggregation.match(Criteria.where("deleted").is(false)));
        //添加group by语句,以name字段进行group by，并以nameCount作为数量的字段名
        aggregationOperations.add(Aggregation.group("name").count().as("nameCount"));
        Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
        //分组查询，指定Map为返回值类型
        AggregationResults<Map> aggregate = template.aggregate(aggregation, UserDO.class, Map.class);
        //遍历结果集
        aggregate.getMappedResults().stream().forEach(map -> {
            //打印groupby字段的名称
            System.out.println(map.get("_id"));
            //打印名称对应的数量
            System.out.println(map.get("nameCount"));
        });

    }
}
