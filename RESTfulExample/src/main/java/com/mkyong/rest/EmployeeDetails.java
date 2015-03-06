package com.mkyong.rest;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

@Path("/emp")
public class EmployeeDetails {

    Connection conn = null;
    Statement stmt = null;
    String output="";
    ResultSet rs = null;

//This method displays all employees details
    @GET
    @Path("empdetails")
    @Produces("application/json")
    public List<Employee> getEmp() throws SQLException {
        List<Employee> employeeList = new ArrayList<Employee>();
        try{
            //Register JDBC driver
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/UsersDB");
            conn = ds.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from employee");
            while(rs.next()){
                int empid  = rs.getInt("empid");
                String name = rs.getString("empname");
                String desg = rs.getString("designation");
                int deptid = rs.getInt("deptid");
                int salary = rs.getInt("salary");
                Employee emp = new Employee();
                emp.setEmployeeId(empid);
                emp.setEmployeeName(name);
                emp.setEmployeeDesig(desg);
                emp.setDeptId(deptid);
                emp.setSalary(salary);
                employeeList.add(emp);
            }
        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        } finally {
            rs.close();
            stmt.close();
            conn.close();
        }
        return employeeList;
    }//getEmp()

//This method displays one employee record
    @GET
    @Path("/emprecord")
    @Produces("application/json")
    public Employee empRecord() throws SQLException {
        Employee emp = new Employee();
        emp.setEmployeeId(115);

        try{
            //Register JDBC driver
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/UsersDB");
            conn = ds.getConnection();
            PreparedStatement ps = conn.prepareStatement("select * from employee where empid = ?");
            ps.setInt(1, emp.getEmployeeId());
            rs = ps.executeQuery();
            while(rs.next()){
                //emp.setEmployeeId(rs.getInt(1));
                emp.setEmployeeName(rs.getString(2));
                emp.setEmployeeDesig(rs.getString(3));
                emp.setDeptId(rs.getInt(4));
                emp.setSalary(rs.getInt(5));
            }
            ps.close();
        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        } finally {
            rs.close();
            conn.close();
        }
        return emp;
    }//empRecord()

//Inserts a new record
    @POST
    @Path("/insertemp")
    @Consumes("application/json")
    public Response insertEmp(Employee emp) throws SQLException {
        try{
            //Register JDBC driver
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/UsersDB");
            conn = ds.getConnection();
            String sql = "insert into employee values(?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, emp.getEmployeeId());
            ps.setString(2, emp.getEmployeeName());
            ps.setString(3, emp.getEmployeeDesig());
            ps.setInt(4, emp.getDeptId());
            ps.setInt(5, emp.getSalary());
            int i = ps.executeUpdate();
            output = i + " record inserted";
            ps.close();
        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        } finally {
            conn.close();
        }
        return Response.status(200).entity(output).build();
    }//insertEmp()

//Updates a record
    @GET
    @Path("/updateemp")
    @Produces("application/json")
    public Employee updateEmp() throws SQLException {
        Employee emp = new Employee();
        emp.setEmployeeName("kranti");
        emp.setSalary(12000);
        emp.setEmployeeId(119);

        try{
            //Register JDBC driver
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/UsersDB");
            conn = ds.getConnection();
            PreparedStatement ps = conn.prepareStatement("update employee set empname = ?, salary = ? where empid = ?");
            ps.setString(1, emp.getEmployeeName());
            ps.setInt(2, emp.getSalary());
            ps.setInt(3, emp.getEmployeeId());
            int i = ps.executeUpdate();
            output = i + " record updated";
            ps.close();
        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        } finally {
            conn.close();
        }
        return emp;
    }//updateEmp()

//Deletes a record
    @GET
    @Path("/deleteemp")
    @Produces("application/json")
    public Response deleteEmp() throws SQLException {
        Employee emp = new Employee();
        emp.setEmployeeId(121);

	 try{
             //Register JDBC driver
             Context initContext = new InitialContext();
             Context envContext = (Context) initContext.lookup("java:comp/env");
             DataSource ds = (DataSource) envContext.lookup("jdbc/UsersDB");
             conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement("delete from employee where empid = ?");
             ps.setInt(1, emp.getEmployeeId());
             int i = ps.executeUpdate();
             output = i + " record deleted";
             ps.close();
	 }catch(SQLException se) {
             se.printStackTrace();
         }catch(Exception e) {
             e.printStackTrace();
         } finally {
             conn.close();
         }
         return Response.status(200).entity(output).build();
    }//deleteEmp()

}//EmployeeDetails

