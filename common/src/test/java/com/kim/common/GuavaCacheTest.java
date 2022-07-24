package com.kim.common;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @Author kim
 * @Since 2021/7/15
 */
public class GuavaCacheTest {


    @Test
    public void newCache(){
        /**
         * 缓存参数设置
         * */
        Cache<Object, Object> cache = CacheBuilder.newBuilder()
                .concurrencyLevel(Runtime.getRuntime().availableProcessors())  //设置并发级别cpu核心数，默认是4
                .initialCapacity(100)   //设置初始容量
                .maximumSize(500)       //设置最大存储容量,若缓存到达初始容量时将会扩容，到达最大容量时将会清除掉最近最少使用的缓存腾出空间后再新增缓存
                //设置权重函数，作为缓存清除的一种策略,如每一项缓存所占据的内存空间大小都不一样，可以看作它们有不同的“权重”（weights）,作为执行清除策略时优化回收的对象
                //设置权重之后会导致缓存无故被清除和设置缓存不成功
                .weigher((key, value) -> key.toString().length())
                .maximumWeight(150)     //设置最大权重
                .weakKeys()             //使用弱引用存储键。当键没有其它（强或软）引用时，缓存项可以被垃圾回收
                .weakValues()           //使用弱引用存储值。当值没有其它（强或软）引用时，缓存项可以被垃圾回收
                .softValues()           //使用软引用存储值。软引用只有在响应内存需要时，才按照全局最近最少使用的顺序回收。考虑到使用软引用的性能影响，我们通常建议使用更有性能预测性的缓存大小限定
                .build();

    }


    @Test
    public void cacheExample(){

        Cache<String,Object> cache=CacheBuilder.newBuilder()
                .concurrencyLevel(Runtime.getRuntime().availableProcessors())
                .initialCapacity(100)
                .maximumSize(200)
                .expireAfterWrite(10, TimeUnit.MINUTES)     //设置写入10分钟后过期
                .expireAfterAccess(5,TimeUnit.MINUTES)      //设置读写缓存后多久过期
                .build();

        //设置缓存
        cache.put("k1","v1");
        //获取缓存
        Object v1 = cache.getIfPresent("k1");
        System.out.println("k1 的缓存值是:"+v1);

        //清除缓存
        cache.invalidate("k1");
        //清除所有缓存
        cache.invalidateAll();
        //清除所有keys集合的缓存
        cache.invalidateAll(Arrays.asList("k1"));
        System.out.println(cache.getIfPresent("k1"));


    }


    @Test
    public void logingCache() throws ExecutionException {

        /**
         * 使用自定义ClassLoader加载数据，置入内存中。
         * 从LoadingCache中获取数据时，若数据存在则直接返回；
         * 若数据不存在，则根据ClassLoader的load方法加载数据至内存，然后返回该数据
         * */
        LoadingCache<String, Object> loadCache = CacheBuilder.newBuilder()
                .build(new CacheLoader<String, Object>() {
                    @Override
                    public Object load(String key) throws Exception {
                        return key.length() * 2;
                    }
                });

        System.out.println("value=:"+loadCache.get("k1"));


    }

}
