package com.fanlm.quit;

public class QuickSort {
    public static void quickSort(int[] arr,int start,int length){
        int i,j,temp,t;
        if(start>length){
            return;
        }
        i=start;
        j=length;
        //temp就是基准位
        temp = arr[start];

        while (i<j) {
            //先看右边，依次往左递减
            while (temp<=arr[j]&&i<j) {
                j--;
            }
            //再看左边，依次往右递增
            while (temp>=arr[i]&&i<j) {
                i++;
            }
            //如果满足条件则交换
            if (i<j) {
                t = arr[j];
                arr[j] = arr[i];
                arr[i] = t;
            }

        }
        //最后将基准为与i和j相等位置的数字交换
        arr[start] = arr[i];
        arr[i] = temp;
        //递归调用左半数组
        quickSort(arr, start, j-1);
        //递归调用右半数组
        quickSort(arr, j+1, length);
    }


    public static void main(String[] args){
        String a = "abc";
        String b = "abc";

        System.out.println(a ==b);

        int[] arr = {10,7,2,4,7,62,3,4,2,1,8,9,19};
        quickSort(arr, 0, arr.length-1);
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }
}