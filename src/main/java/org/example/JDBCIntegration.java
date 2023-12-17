package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCIntegration {
    public Connection connectDatabase() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/payroll_service";
        String username = "root";
        String password = "PrateekC@20035";
        return DriverManager.getConnection(url, username, password);
    }

    public void createTable() throws SQLException {
        String query = "create table employee_payroll(id int auto_increment primary key,name varchar(20),salary float,start_date date, gender char, phone varchar(10), address varchar(200) default 'NA',basic_pay int, deductions int, taxable_pay int, income_tax int, net_pay int, dept_id int after address);";
        try (Connection connection = connectDatabase();
                Statement statement = connection.createStatement();) {
            statement.executeUpdate(query);
            System.out.println("Table created successfully!!");
        } catch (SQLException exception) {
            throw new SQLException(exception.getMessage());
        }
    }

    public void insertData(Employee ob) throws SQLException {
        String query = "insert into employee_payroll(name,salary,start_date,gender,phone,address,basic_pay,dept_id,payroll_id) values (?,?,?,?,?,?,?,?,?);";
        try (Connection connection = connectDatabase();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, ob.getName());
            statement.setDouble(2, ob.getSalary());
            statement.setString(3, ob.getStart_date());
            statement.setString(4, String.valueOf(ob.getGender()));
            statement.setString(5, ob.getPhone());
            statement.setString(6, ob.getAddress());
            statement.setInt(7, ob.getBasic_pay());
            statement.executeUpdate();
            System.out.println("Data added successfully!!");
        } catch (SQLException exception) {
            throw new SQLException(exception.getMessage());
        }
    }

    public List<Employee> readAllData() throws Exception {
        List<Employee> employees = new ArrayList<>();
        String query = "select * from employee_payroll";
        try (Connection connection = connectDatabase();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Employee ob = new Employee(resultSet.getString("name"), resultSet.getString("start_date"),
                        resultSet.getString("phone"), resultSet.getString("address"), resultSet.getInt("salary"),
                        resultSet.getInt("dept_id"), resultSet.getInt("basic_pay"), resultSet.getInt("deductions"),
                        resultSet.getInt("taxable_pay"), resultSet.getInt("income_tax"), resultSet.getInt("net_pay"),
                        resultSet.getString("gender").charAt(0));
                employees.add(ob);
            }
            return employees;
        } catch (SQLException exception) {
            throw new Exception(exception.getMessage());
        }
    }

    public void updateSalary(double salary, String name) throws SQLException {
        String query = "update employee_payroll set salary=? where name=?";
        try (Connection connection = connectDatabase();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, salary);
            preparedStatement.setString(2, name);
            preparedStatement.executeUpdate();
            System.out.println("Salary Updated Successfully!!");
        } catch (SQLException exception) {
            throw new SQLException(exception.getMessage());
        }
    }

    public List<Employee> readSalaryByDate(String date) throws SQLException {
        List<Employee> employees = new ArrayList<>();
        String query = "select * from employee_payroll where start_date between cast(? as date) and date(now());";
        try (Connection connection = connectDatabase();
                PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            preparedStatement.setString(1, date);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Employee ob = new Employee(resultSet.getString("name"), resultSet.getString("start_date"),
                        resultSet.getString("phone"), resultSet.getString("address"), resultSet.getInt("salary"),
                        resultSet.getInt("dept_id"), resultSet.getInt("basic_pay"), resultSet.getInt("deductions"),
                        resultSet.getInt("taxable_pay"), resultSet.getInt("income_tax"), resultSet.getInt("net_pay"),
                        resultSet.getString("gender").charAt(0));
                employees.add(ob);
            }
            return employees;
        } catch (SQLException exception) {
            throw new SQLException(exception.getMessage());
        }
    }

    public int getSum(char gender) throws SQLException {
        String query = "select count(salary) as Sum from employee_payroll where gender=?;";
        try (Connection connection = connectDatabase();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, String.valueOf(gender));
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.getInt("Sum");
        } catch (SQLException exception) {
            throw new SQLException(exception.getMessage());
        }
    }

    public int getAverage(char gender) throws SQLException {
        String query = "select avg(salary) as Average from employee_payroll where gender=?;";
        try (Connection connection = connectDatabase();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, String.valueOf(gender));
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.getInt("Average");
        } catch (SQLException exception) {
            throw new SQLException(exception.getMessage());
        }
    }

    public void createPayrollDetailsTable() throws SQLException {
        String query = "CREATE TABLE payroll_details (id INT AUTO_INCREMENT PRIMARY KEY, employee_id INT, deductions FLOAT, taxable_pay FLOAT, tax FLOAT, net_pay FLOAT, FOREIGN KEY (employee_id) REFERENCES employee_payroll(id) ON DELETE CASCADE);";
        try (Connection connection = connectDatabase();
                Statement statement = connection.createStatement();) {
            statement.executeUpdate(query);
            System.out.println("Payroll details table created successfully with cascading delete!!");
        } catch (SQLException exception) {
            throw new SQLException(exception.getMessage());
        }
    }

    public void insertDataWithPayrollDetails(Employee ob) throws SQLException {
        String employeeQuery = "INSERT INTO employee_payroll(name,salary,start_date,gender,phone,address,basic_pay,dept_id,payroll_id) VALUES (?,?,?,?,?,?,?,?,?);";
        String payrollDetailsQuery = "INSERT INTO payroll_details(employee_id, deductions, taxable_pay, tax, net_pay) VALUES (?,?,?,?,?);";
        Connection connection = null;
        PreparedStatement employeeStatement = null;
        PreparedStatement payrollDetailsStatement = null;
        try {
            connection = connectDatabase();
            connection.setAutoCommit(false);
            employeeStatement = connection.prepareStatement(employeeQuery, Statement.RETURN_GENERATED_KEYS);
            employeeStatement.setString(1, ob.getName());
            employeeStatement.setDouble(2, ob.getSalary());
            employeeStatement.setString(3, ob.getStart_date());
            employeeStatement.setString(4, String.valueOf(ob.getGender()));
            employeeStatement.setString(5, ob.getPhone());
            employeeStatement.setString(6, ob.getAddress());
            employeeStatement.setDouble(7, ob.getBasic_pay());
            employeeStatement.setInt(8, ob.getDept_id());
            employeeStatement.executeUpdate();
            ResultSet rs = employeeStatement.getGeneratedKeys();
            int employeeId = 0;
            if (rs.next()) {
                employeeId = rs.getInt(1);
            }
            double deductions = ob.getSalary() * 0.2;
            double taxablePay = ob.getSalary() - deductions;
            double tax = taxablePay * 0.1;
            double netPay = ob.getSalary() - tax;
            payrollDetailsStatement = connection.prepareStatement(payrollDetailsQuery);
            payrollDetailsStatement.setInt(1, employeeId);
            payrollDetailsStatement.setDouble(2, deductions);
            payrollDetailsStatement.setDouble(3, taxablePay);
            payrollDetailsStatement.setDouble(4, tax);
            payrollDetailsStatement.setDouble(5, netPay);
            payrollDetailsStatement.executeUpdate();
            connection.commit();
            System.out.println("Data and payroll details added successfully!!");
        } catch (SQLException exception) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            throw new SQLException(exception.getMessage());
        } finally {
            if (employeeStatement != null) {
                employeeStatement.close();
            }
            if (payrollDetailsStatement != null) {
                payrollDetailsStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    public void removeEmployeeFromPayroll(int employeeId) throws SQLException {
        String query = "UPDATE employee_payroll SET is_active = false WHERE id = ?;";
        try (Connection connection = connectDatabase();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, employeeId);
            statement.executeUpdate();
            System.out.println("Employee removed from payroll successfully!!");
        } catch (SQLException exception) {
            throw new SQLException(exception.getMessage());
        }
    }

    public List<Employee> getActiveEmployees() throws SQLException {
        List<Employee> employeeList = new ArrayList<>();
        String query = "SELECT * FROM employee_payroll WHERE is_active = true;";
        try (Connection connection = connectDatabase();
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setName(resultSet.getString("name"));
                employee.setSalary(resultSet.getDouble("salary"));
                employee.setGender(resultSet.getString("gender").charAt(0));
                employeeList.add(employee);
            }
        } catch (SQLException exception) {
            throw new SQLException(exception.getMessage());
        }
        return employeeList;
    }
}
