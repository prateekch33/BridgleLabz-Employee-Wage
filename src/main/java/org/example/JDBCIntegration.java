package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCIntegration {
    public Connection connectDatabase() throws SQLException {
        String url="jdbc:mysql://localhost:3306/payroll_service";
        String username="root";
        String password="PrateekC@20035";
        return DriverManager.getConnection(url,username,password);
    }

    public void createTable() throws SQLException {
        String query="create table employee_payroll(id int auto_increment primary key,name varchar(20),salary float,start_date date, gender char, phone varchar(10), address varchar(200) default 'NA',basic_pay int, deductions int, taxable_pay int, income_tax int, net_pay int, dept_id int after address);";
        try (Connection connection=connectDatabase();
            Statement statement=connection.createStatement();) {
            statement.executeUpdate(query);
            System.out.println("Table created successfully!!");
        } catch(SQLException exception) {
            throw new SQLException(exception.getMessage());
        }
    }

    public void insertData(Employee ob) throws SQLException {
        String query="insert into employee_payroll(name,salary,start_date,gender,phone,address,basic_pay,deductions,taxable_pay,income_tax,net_pay,dept_id) values (?,?,?,?,?,?,?,?,?,?,?,?);";
        try (Connection connection=connectDatabase();
             PreparedStatement statement=connection.prepareStatement(query)) {
            statement.setString(1,ob.getName());
            statement.setDouble(2,ob.getSalary());
            statement.setString(3,ob.getStart_date());
            statement.setString(4, String.valueOf(ob.getGender()));
            statement.setString(5,ob.getPhone());
            statement.setString(6, ob.getAddress());
            statement.setInt(7,ob.getBasic_pay());
            statement.setInt(8,ob.getDeductions());
            statement.setInt(9,ob.getTaxable_pay());
            statement.setInt(10,ob.getIncome_tax());
            statement.setInt(11,ob.getNet_pay());
            statement.setInt(12,ob.getDept_id());
            statement.executeUpdate();
            System.out.println("Data added successfully!!");
        } catch(SQLException exception) {
            throw new SQLException(exception.getMessage());
        }
    }

    public List<Employee> readAllData() throws Exception {
        List<Employee> employees=new ArrayList<>();
        String query="select * from employee_payroll";
        try (Connection connection=connectDatabase();
            Statement statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery(query)) {
            while (resultSet.next()) {
                Employee ob=new Employee(resultSet.getString("name"),resultSet.getString("start_date"),resultSet.getString("phone"),resultSet.getString("address"),resultSet.getInt("salary"),resultSet.getInt("dept_id"),resultSet.getInt("basic_pay"),resultSet.getInt("deductions"),resultSet.getInt("taxable_pay"),resultSet.getInt("income_tax"),resultSet.getInt("net_pay"),resultSet.getString("gender").charAt(0));
                employees.add(ob);
            }
            return employees;
        } catch (SQLException exception) {
            throw new Exception(exception.getMessage());
        }
    }

    public void updateSalary(double salary,String name) throws SQLException {
        String query="update employee_payroll set salary=? where name=?";
        try (Connection connection=connectDatabase();
            PreparedStatement preparedStatement=connection.prepareStatement(query)) {
            preparedStatement.setDouble(1,salary);
            preparedStatement.setString(2,name);
            preparedStatement.executeUpdate();
            System.out.println("Salary Updated Successfully!!");
        } catch(SQLException exception) {
            throw new SQLException(exception.getMessage());
        }
    }

    public List<Employee> readSalaryByDate(String date) throws SQLException {
        List<Employee> employees=new ArrayList<>();
        String query="select * from employee_payroll where start_date between cast(? as date) and date(now());";
        try (Connection connection=connectDatabase();
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            ) {
            preparedStatement.setString(1,date);
            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next()) {
                Employee ob=new Employee(resultSet.getString("name"),resultSet.getString("start_date"),resultSet.getString("phone"),resultSet.getString("address"),resultSet.getInt("salary"),resultSet.getInt("dept_id"),resultSet.getInt("basic_pay"),resultSet.getInt("deductions"),resultSet.getInt("taxable_pay"),resultSet.getInt("income_tax"),resultSet.getInt("net_pay"),resultSet.getString("gender").charAt(0));
                employees.add(ob);
            }
            return employees;
        } catch (SQLException exception) {
            throw new SQLException(exception.getMessage());
        }
    }

    public int getSum(char gender) throws SQLException {
        String query="select count(salary) as Sum from employee_payroll where gender=?;";
        try (Connection connection=connectDatabase();
            PreparedStatement preparedStatement=connection.prepareStatement(query)) {
            preparedStatement.setString(1,String.valueOf(gender));
            ResultSet resultSet=preparedStatement.executeQuery();
            return resultSet.getInt("Sum");
        } catch(SQLException exception) {
            throw new SQLException(exception.getMessage());
        }
    }
    public int getAverage(char gender) throws SQLException {
        String query="select avg(salary) as Average from employee_payroll where gender=?;";
        try (Connection connection=connectDatabase();
             PreparedStatement preparedStatement=connection.prepareStatement(query)) {
            preparedStatement.setString(1,String.valueOf(gender));
            ResultSet resultSet=preparedStatement.executeQuery();
            return resultSet.getInt("Average");
        } catch(SQLException exception) {
            throw new SQLException(exception.getMessage());
        }
    }
}
