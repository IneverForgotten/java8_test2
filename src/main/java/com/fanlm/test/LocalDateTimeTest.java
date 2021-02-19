package com.fanlm.test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @program: java8_test
 * @description: java8 date
 * @author: flm
 * @create: 2021-02-07 14:28
 **/
public class LocalDateTimeTest {
    public static void main(String[] args) {
        newFormat();

        LocalDate date = LocalDate.of(2021, 1, 26);
        LocalDate parse = LocalDate.parse("2021-01-26");

        LocalDateTime dateTime = LocalDateTime.of(2021, 1, 26, 12, 12, 22);
        LocalDateTime parse1 = LocalDateTime.parse("2021-01-26 12:12:22");

        LocalTime time = LocalTime.of(12, 12, 22);
        LocalTime parse2 = LocalTime.parse("12:12:22");
    }


    public static void newFormat(){
        //format yyyy-MM-dd
        LocalDate date = LocalDate.now();
        System.out.println(String.format("date format : %s", date));

        //format HH:mm:ss
        LocalTime time = LocalTime.now().withNano(0);
        System.out.println(String.format("time format : %s", time));

        //format yyyy-MM-dd HH:mm:ss
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println(String.format("dateTime format : %s", dateTimeFormatter.format(dateTime)));
    }
}
