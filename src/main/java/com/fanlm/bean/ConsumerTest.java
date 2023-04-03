package com.fanlm.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.swing.text.StyleConstants;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * @ClassName: ConsumerTest
 * @Description:
 * @Author: fanLeiMin
 * @Date: 2021/8/17 17:14
 */
public class ConsumerTest {
    public static void main(String[] args) {
        Consumer c = a -> {a = (int)a * 2;
            System.out.println(a);
        };
        c.accept(123);

        Consumer f = System.out::println;
        Consumer f2 = n -> System.out.println(n + "-F2");

        //执行完F后再执行F2的Accept方法
        f.andThen(f2).accept("test");

        //连续执行F的Accept方法
        f.andThen(f).andThen(f).andThen(f).accept("test1");
//        -----------------------------------------------
        ArrayList<Employee> employees = new ArrayList<>();
        String[] prefix = {"A", "B"};
        for (int i = 1; i <= 10; i++)
            employees.add(new Employee(prefix[i % 2] + i, i * 1000));
        employees.forEach(new SalaryConsumer());
        employees.forEach(new NameConsumer());

//        ---------------------------
        List<Apple> apples = Arrays.asList(new Apple("color1","name1"), new Apple("color2","name2"));
        consumerApple(apples,ap -> {ap.setColor(ap.getColor() + "123");
            System.out.println(ap.getColor());});

    }

    static class Employee {
        private String name;
        private int salary;

        public Employee() {
            this.salary = 4000;
        }

        public Employee(String name, int salary) {
            this.name = name;
            this.salary = salary;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSalary() {
            return salary;
        }

        public void setSalary(int salary) {
            this.salary = salary;
        }

        @Override
        public String toString() {
            return new StringBuilder()
                    .append("name:").append(name)
                    .append(",salary:").append(salary)
                    .toString();
        }
    }

    // 输出需要交税的员工
    static class SalaryConsumer implements Consumer<Employee> {

        @Override
        public void accept(Employee employee) {
            if (employee.getSalary() > 2000) {
                System.out.println(employee.getName() + "要交税了.");
            }
        }

    }

    // 输出需要名字前缀是‘A’的员工信息
    static class NameConsumer implements Consumer<Employee> {

        @Override
        public void accept(Employee employee) {
            if (employee.getName().startsWith("A")) {
                System.out.println(employee.getName() + " salary is " + employee.getSalary());
            }
        }
    }

    public static void  consumerApple(List<Apple> apples, Consumer<Apple> consumer) {
        for (Apple apple : apples) {
            consumer.accept(apple);
        }
    }
    @Data
    @AllArgsConstructor
    static class Apple {
        private String color;
        private String name;
    }
}
