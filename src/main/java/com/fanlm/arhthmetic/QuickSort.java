package com.fanlm.arhthmetic;

public class QuickSort {
    public static void main(String[] args) {
        int[] arr = {10,7,2,4,7,62,3,4,2,1,8,9,19};
        mergerSort(arr);
//        selectSort(arr);
//        quickSort(arr, 0, arr.length-1);
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }


    /**
     * 快速排序
     * @param arr
     * @param low
     * @param high
     */
    public static void quickSort(int[] arr,int low,int high){
        int i,j,temp;
        if(low>high){
            return;
        }
        i=low;
        j=high;
        //temp就是基准位
        temp = arr[low];

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
//                arr[i]^=arr[j]^=arr[i]^=arr[j];
                arr[i] = arr[i] ^ arr[j];
                arr[j] = arr[i] ^ arr[j];
                arr[i] = arr[i] ^ arr[j];
//                t = arr[j];
//                arr[j] = arr[i];
//                arr[i] = t;
            }
        }
        //最后将基准为与i和j相等位置的数字交换
        arr[low] = arr[i];
        arr[i] = temp;
        //递归调用左半数组
        quickSort(arr, low, j-1);
        //递归调用右半数组
        quickSort(arr, j+1, high);
    }

    /**
     * 选择排序
     * @param arr
     */
    public static void selectSort(int[] arr){
        for (int i = 0; i < arr.length-1; i++) {
            int tempMaxIndex = 0;
            for (int j = 0; j < arr.length-i; j++) {
                if (arr[j] > arr[tempMaxIndex]) {
                    tempMaxIndex = j;
                }
            }
            int temp = arr[tempMaxIndex];
            arr[tempMaxIndex] = arr[arr.length-1-i];
            arr[arr.length-1-i] = temp;
        }
    }

    /**
     * 归并排序
     * @param arr
     */
    public static void mergerSort(int[] arr){
        if (arr == null || arr.length == 0){
            return;
        }
        int[] temp = new int[arr.length];
        mergerSort(arr, 0, arr.length-1, temp);
    }

    private static void mergerSort(int[] arr, int left, int right, int[] temp){
        if (left < right){
            // 找到中间点
            int mid = left + (right - left) / 2;
            // 对左半部分排序
            mergerSort(arr, left, mid, temp);
            // 对右半部分排序
            mergerSort(arr, mid + 1, right, temp);
            // 合并两个有序数组
            merge(arr, left, mid, right, temp);

        }
    }

    public static void merge(int[] arr, int left, int mid, int right, int[] temp){
        int i = left; // 左半部分的起始索引
        int j = mid + 1; // 右半部分的起始索引
        int k = 0; // 临时数组的起始索引

        // 将两个有序数组合并到临时数组中
        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }

        // 将左半部分剩余的元素复制到临时数组中
        while (i <= mid) {
            temp[k++] = arr[i++];
        }

        // 将右半部分剩余的元素复制到临时数组中
        while (j <= right) {
            temp[k++] = arr[j++];
        }

        // 将临时数组中的元素复制回原数组
        k = 0;
        while (left <= right) {
            arr[left++] = temp[k++];
        }
    }
}
