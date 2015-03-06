package com.mkyong.rest;

public class Employee {
    private int employeeId;
    private String employeeName;
    private int salary;
    private String employeeDesig;
    private int deptId;

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeName() {
        return this.employeeName;
    }

    public void setEmployeeDesig(String employeeDesig) {
        this.employeeDesig = employeeDesig;
    }

    public String getEmployeeDesig() {
        return this.employeeDesig;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public int getDeptId() {
        return this.deptId;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getSalary() {
        return salary;
    }
}
