package com.myshop.www;

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
import java.util.Set;
import java.util.HashSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

@Path("/myshop")
public class ShoppingCart {

    Connection conn = null;
    Statement stmt = null;
    String output="";
    ResultSet rs = null;

    @GET
    @Path("/display")
    @Produces("application/json")
    public Set<String> getDetails() throws SQLException {
        Set<String> pduSet = new HashSet<String>();
        try{
            //Register JDBC driver
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/UsersDB");
            conn = ds.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select productCode from products");
            Product pdu = new Product();
            while(rs.next()){
                //int pid  = rs.getInt("productid");
                //String pname = rs.getString("name");
                String pcode = rs.getString("productCode");
                //int qty = rs.getInt("quantity");
                //double price = rs.getDouble("price");
                //Product pdu = new Product();
                //pdu.setPid(pid);
                //pdu.setPname(pname);
                pdu.setPcode(pcode);
                //pdu.setQty(qty);
                //pdu.setPrice(price);
                pduSet.add(pcode);
            }
        }catch(SQLException se){
            se.toString();
        }catch(Exception e){
            e.printStackTrace();
        } finally {
            rs.close();
            stmt.close();
            conn.close();
        }
        return pduSet;
    }
    /*public String getInfo() {
      String output = "<html> "
      + "<title>" + "Shopping Cart" + "</title>"
      + "<body><center><br><br><br>" + "Shopping Cart" + "<br>"
      + "--------------------" + "<br>" + "Products" + "<br>" + "Search" + "<br>" + "Cart" + "</center></body>" +
      "</html> ";
      return output;
    //return Response.status(200).entity(output).build();
    }*/
}

