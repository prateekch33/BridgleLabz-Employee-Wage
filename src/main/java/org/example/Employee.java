package org.example;

public class Employee {
    private String name,start_date,phone,address;
    private double salary;
    private int dept_id,basic_pay,deductions,taxable_pay,income_tax,net_pay;
    private char gender;

    public Employee() {};
    public Employee(String name,String start_date,String phone,String address,double salary,int dept_id,int basic_pay, int deductions, int taxable_pay, int income_tax, int net_pay, char gender) {
        this.name=name;
        this.start_date=start_date;
        this.phone=phone;
        this.address=address;
        this.salary=salary;
        this.dept_id=dept_id;
        this.basic_pay=basic_pay;
        this.deductions=deductions;
        this.taxable_pay=taxable_pay;
        this.income_tax=income_tax;
        this.net_pay=net_pay;
        this.gender=gender;
    }

    public char getGender() {
        return gender;
    }

    public int getBasic_pay() {
        return basic_pay;
    }

    public int getDeductions() {
        return deductions;
    }

    public int getDept_id() {
        return dept_id;
    }

    public int getIncome_tax() {
        return income_tax;
    }

    public int getNet_pay() {
        return net_pay;
    }

    public double getSalary() {
        return salary;
    }

    public int getTaxable_pay() {
        return taxable_pay;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBasic_pay(int basic_pay) {
        this.basic_pay = basic_pay;
    }

    public void setDeductions(int deductions) {
        this.deductions = deductions;
    }

    public void setDept_id(int dept_id) {
        this.dept_id = dept_id;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public void setIncome_tax(int income_tax) {
        this.income_tax = income_tax;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNet_pay(int net_pay) {
        this.net_pay = net_pay;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public void setTaxable_pay(int taxable_pay) {
        this.taxable_pay = taxable_pay;
    }
}
