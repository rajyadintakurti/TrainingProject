package com.training.myshop;

import java.sql.*;

import java.util.List;
import java.util.ArrayList;

import java.util.Set;
import java.util.HashSet;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Path("/signup")
public class SignupOperations {

    /*******************************************************
      Common Connector snippet
     ******************************************************/

    public Connection getConnected(){
        Connection conn = null;
        try{
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/MyShopDB");
            conn = ds.getConnection();
        } catch(Exception e){
            e.printStackTrace();
        }
        return conn;
    }


    /**************************************************************
      To list the Users
     *************************************************************/

    @POST
        @Produces(MediaType.APPLICATION_JSON)
        @Path("/list")
        public Authentication getUsers(@FormParam("email") String email,@FormParam("password") String password) {
            Connection conn = null;
            Authentication user = null;
            Statement stmt = null;
            String shaPassword = null;
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-1");
                md.update(password.getBytes());
                BigInteger hash = new BigInteger(1, md.digest());
                shaPassword = hash.toString(16);

            } catch (NoSuchAlgorithmException e) { 
                    e.printStackTrace();
            }

            try {
                conn = getConnected();
		
                stmt = conn.createStatement();
                String sql = "select userName, email, password from users where email = \"" + email + "\" and password = \"" + shaPassword +"\"";
                ResultSet rs = stmt.executeQuery(sql);
                if(rs != null) {
                    rs.next();
                        user = new Authentication();
                        user.setUserName(rs.getString("userName"));
                        user.setEmail(rs.getString("email"));
                        user.setPassword(rs.getString("password"));
                        //UsersList.add(user);
                 }
                
                rs.close();
            } catch(Exception e) {
                e.printStackTrace();
            } finally {
                //finally block used to close resources
                try{
                    if(stmt!=null)
                        stmt.close();
                }catch(SQLException se2){
                }// nothing we can do
                try{if(conn!=null)
                    conn.close();
                }catch(SQLException se){
                    se.printStackTrace();
                }//end finally try
            }

            //return Response.status(200).entity(output).build();
            return user;
        }


    /****************************************************************
      To Insert a new user record
     ***************************************************************/

        @POST
        @Path("/createUser")
        @Produces(MediaType.TEXT_PLAIN)
        
        public String insertUser(@FormParam("userName") String userName,@FormParam("email") String email,@FormParam("password") String password,@FormParam("mobileNo") long mobileNo) {

            Connection conn = null;
            PreparedStatement pstmt = null;
            String output = "Updated ";
            String hashStr = null;
            //String password = "password";
            
          try {
                MessageDigest md = MessageDigest.getInstance("SHA-1");
                md.update(password.getBytes());
                BigInteger hash = new BigInteger(1, md.digest());
                hashStr = hash.toString(16);

            } catch (NoSuchAlgorithmException e) { 
                    e.printStackTrace();
            }

            try{
                conn = getConnected();
                output =userName;
                String sql = "insert into users(userName, email, password, mobileNo) values(?, ?, ?, ?)";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, userName);
                pstmt.setString(2, email);
                pstmt.setString(3, hashStr);
                pstmt.setLong(4, mobileNo);
                int updated = pstmt.executeUpdate();
                output = output + updated + "records successfully";
            } catch(Exception e) {
                e.printStackTrace();
            } finally {
                //finally block used to close resources
                try {
                    if(pstmt != null)
                        pstmt.close();
                } catch(SQLException se2) {
                }// nothing we can do
                try {
                    if(conn != null)
                        conn.close();
                } catch(SQLException se) {
                    se.printStackTrace();
                } //end finally try
            }
            return output;
        }
}	
