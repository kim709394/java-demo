package com.kim.common;

import com.kim.common.entity.Computer;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.validator.Var;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * @Author kim
 * @Since 2021/4/21
 * 集合框架测试
 */
public class CollectionTest {


    @Test
    @DisplayName("集合常用处理")
    public void collectionAccess(){

        List<Integer> list= new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        //反转
        System.out.println("正常："+list);
        Collections.reverse(list);
        System.out.println("反转后："+list);
        //排序,从小到大
        //如果返回值是正数则会调换位置，如果返回值是负数或0则位置不变
        //即o1-o2：从小到大，o2-o1：从大到小
        //Collections.sort(list, (o1, o2) -> o1.compareTo(o2));简写Integer::compareTo
        Collections.sort(list, Integer::compareTo);
        System.out.println("从小到大排序："+list);
        //排序，从大到小
        //如果返回值是正数则会调换位置，如果返回值是负数或0则位置不变
        //即o1-o2：从小到大，o2-o1：从大到小
        //Collections.sort(list, (o1, o2) -> o2.compareTo(o1));简写 Comparator.reverseOrder()
        Collections.sort(list, Comparator.reverseOrder());
        System.out.println("从大到小排序："+list);

        //定制化对象大小比较
        List<Computer> computers=new ArrayList<>();
        computers.add(new Computer(1));
        computers.add(new Computer(2));
        computers.add(new Computer(3));
        computers.add(new Computer(4));
        computers.add(new Computer(5));
        computers.add(new Computer(6));
        //反转
        System.out.println("正常："+computers);
        Collections.reverse(computers);
        System.out.println("反转后："+computers);
        //从小到大排序
        Collections.sort(computers, Computer::compareTo);
        System.out.println("从小到大排序："+computers);
        //从大到小排序
        Collections.sort(computers, Comparator.reverseOrder());
        System.out.println("从大到小排序："+computers);
        //随机排序
        Collections.shuffle(list);
        System.out.println("随机排序："+list);
        //交换两个位置的元素
        Collections.swap(list,0,1);
        System.out.println("交换0和1的元素后："+list);
        //旋转,distance为正数时将list最后的元素移到前面，为负数则将头几个元素整体移到后面
        Collections.rotate(list,2);
        System.out.println("将后面2位元素移到前面："+list);
        //二分法查找元素，返回元素所在的索引,此时要求list是有序的
        int i = Collections.binarySearch(list, 4);
        System.out.println("元素4的索引是："+i);
        //获取最大值
        Integer max = Collections.max(list);
        System.out.println("list的最大值是："+max);
        //根据定制规则获取最大值
        Computer maxComputer = Collections.max(computers, Computer::compareTo);
        System.out.println("尺寸最大电脑："+maxComputer);
        //用指定的元素替换list的所有元素
        Collections.fill(list,10);
        System.out.println("用10替换list后："+list);
        //统计元素出现次数
        int frequency = Collections.frequency(list, 10);
        System.out.println("10在list中出现的次数："+frequency);
        //统计元素10在list中第一次出现的索引
        int index = Collections.indexOfSubList(list, Arrays.asList(10));
        System.out.println("10在list第一次出现的索引："+index);
        //用新元素替换旧元素
        Collections.replaceAll(list,10,8);
        System.out.println("被替换后的list:"+list);

        /**
         * 最好不要用下面这些方法，效率非常低，需要线程安全的集合类型时请考虑使用 JUC 包下的并发集合。
         * 同步控制方法：
         * synchronizedCollection(Collection<T>  c) //返回指定 collection 支持的同步（线程安全的）collection。
         * synchronizedList(List<T> list)//返回指定列表支持的同步（线程安全的）List。
         * synchronizedMap(Map<K,V> m) //返回由指定映射支持的同步（线程安全的）Map。
         * synchronizedSet(Set<T> s) //返回指定 set 支持的同步（线程安全的）set。
         *
         * */



    }

    /**
     * 测试LinkedHashMap有序
     * */
    @Test
    public void testLinkedHashMap(){

        Map<String,Object> linkedHashmap=new LinkedHashMap<>();
        for(int i=0;i<100;i++){
            linkedHashmap.put(i+"",i);
        }

        for (Map.Entry<String,Object> entry:linkedHashmap.entrySet()
        ) {
            System.out.println(entry.getKey());
        }
        System.out.println("hashMap");
        /**********************hashMap***************************/
        Map<String,Object> hashMap=new HashMap<>();
        for(int i=0;i<100;i++){
            hashMap.put(i+"",i);
        }
        for (Map.Entry<String,Object> entry:hashMap.entrySet()
        ) {
            System.out.println(entry.getKey().hashCode());
            System.out.println(entry.getKey());
        }

    }

    /**
     * 合并数组
     * */
    @Test
    public void mergeArr(){

        String[] arr1=new String[]{"1","2"};
        String[] arr2=new String[]{"3"};
        String[] arr3 = ArrayUtils.addAll(arr1, arr2);
        Arrays.stream(arr3).forEach(System.out::println);
    }


}
