package org.example;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    static JDBCIntegration jdbc=new JDBCIntegration();
    static void addData(Scanner in) {
        Employee ob=new Employee();
        System.out.print("Enter name: ");
        String name=in.nextLine();
        ob.setName(name);
        System.out.print("Enter Salary: ");
        double salary=in.nextFloat();
        ob.setSalary(salary);
        System.out.print("Enter Start Date: ");
        String date=in.nextLine();
        ob.setStart_date(date);
        System.out.print("Enter Gender: ");
        char gender=in.nextLine().charAt(0);
        ob.setGender(gender);
        System.out.print("Enter Phone Number: ");
        String phone=in.nextLine();
        ob.setPhone(phone);
        System.out.print("Enter Address: ");
        String address=in.nextLine();
        ob.setAddress(address);
        System.out.print("Enter Basic Pay: ");
        int basic=in.nextInt();
        ob.setBasic_pay(basic);
        System.out.print("Enter Deductions: ");
        int deductions=in.nextInt();
        ob.setDeductions(deductions);
        System.out.print("Enter Taxable Pay: ");
        int taxable_pay=in.nextInt();
        ob.setTaxable_pay(taxable_pay);
        System.out.print("Enter Income Tax: ");
        int income_tax=in.nextInt();
        ob.setIncome_tax(income_tax);
        System.out.print("Enter Net Pay: ");
        int net_pay=in.nextInt();
        ob.setNet_pay(net_pay);
        System.out.print("Enter Department Id: ");
        int dept_id=in.nextInt();
        ob.setDept_id(dept_id);
        try {
            jdbc.insertData(ob);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }
    static void allEmployees() {
        try {
            List<Employee> allData=jdbc.readAllData();
            for(Employee employee: allData) {
                System.out.println(employee);
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
    static void updateSalary(Scanner in) {
        System.out.print("Enter name of the employee: ");
        String name=in.nextLine();
        System.out.print("Enter new salary: ");
        double salary=in.nextDouble();
        try {
            jdbc.updateSalary(salary,name);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }
    static void employeesByDate(Scanner in) {
        System.out.print("Enter date: ");
        String date=in.nextLine();
        try {
            List<Employee> allData=jdbc.readSalaryByDate(date);
            for(Employee employee: allData) {
                System.out.println(employee);
            }
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }
    static void salarySum() {
        try {
            int sumMale=jdbc.getSum('M');
            int sumFemale=jdbc.getSum('F');
            System.out.println("Sum of salary of all the Male employees: "+sumMale);
            System.out.println("Sum of salary of all the Female employees: "+sumFemale);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }
    static void salaryAvg() {
        try {
            int avgMale=jdbc.getAverage('M');
            int avgFemale=jdbc.getAverage('F');
            System.out.println("Average of salary of all the Male employees: "+avgMale);
            System.out.println("Average of salary of all the Female employees: "+avgFemale);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }
    public static void main(String[] args) {
        JDBCIntegration ob=new JDBCIntegration();
        Scanner in=new Scanner(System.in);
        try (Connection connection=ob.connectDatabase()) {
            System.out.println("Connection Established Successfully!!");
            int choice;
            do {
                System.out.println("Enter the choice you want to perform: ");
                System.out.println("1. Add Employees.");
                System.out.println("2. See all Employees");
                System.out.println("3. Update Salary");
                System.out.println("4. See employees on the basis of Date");
                System.out.println("5. Sum of Salary of all the employees.");
                System.out.println("6. Average of Salary of all the employees.");
                choice=in.nextInt();
                switch (choice) {
                    case 1:
                        addData(in);
                        break;
                    case 2:
                        allEmployees();
                        break;
                    case 3:
                        updateSalary(in);
                        break;
                    case 4:
                        employeesByDate(in);
                        break;
                    case 5:
                        salarySum();
                        break;
                    case 6:
                        salaryAvg();
                        break;
                    case 7:
                        break;
                    default:
                        System.out.println("Wrong Choice!!");
                        break;
                }
            }while (choice!=7);
        } catch(SQLException exception) {
            System.out.println("Exception: "+exception.getMessage());
        }
    }
}