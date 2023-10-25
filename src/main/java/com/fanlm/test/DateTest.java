package com.fanlm.test;

import java.time.LocalDate;

/**
 * @ClassName: DateTest
 * @Description:
 * @Author: fanLeiMin
 * @Date: 2022/1/6 15:54
 */
public class DateTest {
    public static void main(String[] args) {
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate.getDayOfMonth());
        System.out.println(localDate.getMonthValue());
        System.out.println(localDate.getMonth());
        System.out.println(localDate.getDayOfWeek());
        System.out.println(localDate.getDayOfYear());
        System.out.println(localDate.getYear());
    }
}
