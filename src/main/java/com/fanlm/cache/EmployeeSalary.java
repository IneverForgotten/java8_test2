package com.fanlm.cache;

import io.netty.util.internal.ObjectUtil;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class EmployeeSalary {
    Long id;
    String employeeName;
    String salary;
    String salaryAfterTax;

    public String getSalary() {
        return salary;
    }

    public void setSalaryAfterTax(String salaryAfterTax) {
        this.salaryAfterTax = salaryAfterTax;
    }

    public EmployeeSalary(Long id,  String employeeName, String salary){
        this.id = id;
        this.employeeName = employeeName;
        this.salary = employeeName;
    }
}
class TaxTable {
    BigDecimal frontAmount;
    BigDecimal afterAmount;
    BigDecimal taxRate;
    public TaxTable(BigDecimal frontAmount, BigDecimal afterAmount, BigDecimal taxRate){
        this.afterAmount = afterAmount;
        this.frontAmount = frontAmount;
        this.taxRate = taxRate;
    }
}
class SalaryCalculator {
    public void calSalaryAfterTax (List<EmployeeSalary> salaryList,
                                   List<TaxTable > taxTable ) {
        for (EmployeeSalary employeeSalary : salaryList) {
            String salary = employeeSalary.getSalary();
            if (ObjectUtils.isEmpty(salary)){
                continue;
            }
            //税收计算
            employeeSalary.setSalaryAfterTax(getTax(new BigDecimal(employeeSalary.getSalary()), taxTable).toString());
        }
    }
    /**
     * 计算税收
     * @param salary 工资
     * @return 纳税金额
     */
    public BigDecimal getTax(BigDecimal salary, List<TaxTable > taxTable){
        BigDecimal tax = BigDecimal.ZERO;
        for (TaxTable table : taxTable) {
            if (salary.compareTo(table.frontAmount) == 1){
                if ((salary.compareTo(table.afterAmount) < 1)){
                    tax.add(salary.subtract(table.frontAmount).multiply(table.taxRate));
                }else {
                    tax.add(table.afterAmount.subtract(table.frontAmount).multiply(table.taxRate));
                }
            }
        }
        return tax;
    }


    public void testCalSalarAfterTax(){
        calSalaryAfterTax(
                Arrays.asList(new EmployeeSalary(1L, "a", "1000")),
                Arrays.asList(new TaxTable(new BigDecimal("0"), new BigDecimal("1000"), new BigDecimal("0"))));
    }

}

