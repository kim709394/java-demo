package com.kim.common;

import com.kim.common.entity.StringBufferFactory;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author kim
 * @Since 2021/4/21
 * 对象池测试
 */
public class ObjectPoolTest {

    @Test
    public void ObjectPool() throws Exception{
        //初始化对象池
        GenericObjectPool genericObjectPool=new GenericObjectPool<StringBuffer>(new StringBufferFactory());
        //设置池中最大对象数量
        genericObjectPool.setMaxTotal(4);
        //设置最小空闲等待时间(当对象池中空闲对象持续时间达到这个时间时可能会被移除)为无穷大
        genericObjectPool.setMinEvictableIdleTimeMillis(2);
        //设置最大空闲数量
        genericObjectPool.setMaxIdle(4);
        //连接空闲的最小时间，达到此值后空闲链接将会被移除，且保留minIdle个空闲连接数；
        genericObjectPool.setSoftMinEvictableIdleTimeMillis(2);
        //空闲连接检测的周期（单位毫秒）；如果为负值，表示不运行检测线程；
        genericObjectPool.setTimeBetweenEvictionRunsMillis(2);
        //genericObjectPool.setMinIdle(4);
        ObjectPool<StringBuffer> stringBufferObjectPool=(ObjectPool<StringBuffer>)genericObjectPool;

        System.out.println("*************-----------******************---------------***************");
        List<StringBuffer> stringBuffers=new ArrayList<>();
        //从对象池里借对象出来
        for(int i=0;i<4;i++){
            StringBuffer sb=stringBufferObjectPool.borrowObject();
            stringBuffers.add(sb);
        }
        System.out.println("对象池中活跃对象数量:"+stringBufferObjectPool.getNumActive());
        System.out.println("对象池中空闲对象数量:"+stringBufferObjectPool.getNumIdle());
        //将对象还会对象池
        for (StringBuffer sb: stringBuffers
        ) {
            stringBufferObjectPool.returnObject(sb);
        }
        System.out.println("对象池中活跃对象数量:"+stringBufferObjectPool.getNumActive());
        System.out.println("对象池中空闲对象数量:"+stringBufferObjectPool.getNumIdle());
        Thread.sleep(15*1000);
        //清除并关闭对象池
        stringBufferObjectPool.clear();
        stringBufferObjectPool.close();
        System.out.println(stringBufferObjectPool);
        System.out.println("对象池中活跃对象数量:"+stringBufferObjectPool.getNumActive());
        System.out.println("对象池中空闲对象数量:"+stringBufferObjectPool.getNumIdle());
        System.out.println("########################-------------------##########################");
        //StringBuffer stringBuffer2 = stringBufferObjectPool.borrowObject();




    }

}
