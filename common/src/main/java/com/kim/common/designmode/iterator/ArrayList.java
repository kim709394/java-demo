package com.kim.common.designmode.iterator;

/**
 * @author huangjie
 * @description     自定义数组集合
 * @date 2021-10-30
 */
public class ArrayList<E> implements List<E>{

    private Object[] arr;
    private int size;
    private static int cap=100;

    public ArrayList(){
        arr=new Object[cap];
    }

    @Override
    public void add(E t) {
        if(size==cap){
            increment();
        }
        arr[size] = t;
        size++;
    }

    private void increment(){
        cap+=100;
        Object[] tmp=new Object[cap];
        for(int i = 0;i<arr.length;i++){
            tmp[i] = arr[i];
        }
        arr = tmp;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public E get(int index) {
        return (E)arr[index];
    }


    @Override
    public Iterator<E> iterator() {


        return new Iterator<E>() {
            private int limit;
            @Override
            public boolean hasNext() {
                return limit < size;
            }

            @Override
            public E next() {
                E e = get(limit);
                limit++;
                return e;
            }
        };
    }
}
