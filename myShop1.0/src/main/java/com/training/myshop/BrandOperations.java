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

@Path("/brand")
public class BrandOperations {

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
      To list the Brands
     **************************************************************/

    @GET
        @Produces(MediaType.APPLICATION_JSON)
        @Path("/list/{category}")
        public List<Brand> brandList(@PathParam("category") int categoryId) {
            Connection conn = null;
            List<Brand> brandList = new ArrayList<Brand>();
            Statement stmt = null;
            try {
                conn = getConnected();
                stmt = conn.createStatement();
                String sql = "select * from brand where categoryId = " + categoryId;
                ResultSet rs = stmt.executeQuery(sql);
                while(rs.next()) {
                    Brand brand = new Brand();
                    brand.setBrandId(rs.getInt("brandId"));
                    brand.setCategoryId(rs.getInt("categoryId"));
                    brand.setBrandName(rs.getString("brandName"));
                    //add this brand object to the brandList object
                    brandList.add(brand);
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
            return brandList;
        }


    /****************************************************************
      To update an existing Brand
     ***************************************************************

     @GET
     @Produces(MediaType.TEXT_PLAIN)
     @Path("/update")
     public String updateBrand(@QueryParam("id") int id) {

     Connection conn = null;
     Statement stmt = null;
     Employee emp = null;
     String output = "Updated ";

     try{
     conn = getConnected();
     stmt = conn.createStatement();
     String sql = "update employee set address = 'address' where id = " + id;
     int updated = stmt.executeUpdate(sql);
     output = output + updated + "records successfully";
     } catch(Exception e) {
     e.printStackTrace();
     } finally {
    //finally block used to close resources
    try {
    if(stmt != null)
    stmt.close();
    } catch(SQLException se2) {
    }// nothing we can do
    try {
    if(conn != null)
    conn.close();
    } catch(SQLException se) {
    se.printStackTrace();
    }//end finally try
    }
    return output;
    }

    /****************************************************************
    To Delete an existing brand
     ***************************************************************/

    @GET
        @Produces(MediaType.TEXT_PLAIN)
        @Path("/delete")
        public String deleteBrand(@QueryParam("name") String brandName) {

            Connection conn = null;
            PreparedStatement stmt = null;
            String output = "Deleted ";

            try{
                conn = getConnected();
                String sql = "delete from brand where brandName = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, brandName);
                stmt.executeUpdate();
                output = output + " record with brandName :" + brandName  + " successfully";
            } catch(Exception e) {
                e.printStackTrace();
            } finally {
                //finally block used to close resources
                try {
                    if(stmt != null)
                        stmt.close();
                } catch(SQLException se2) {
                }// nothing we can do
                try {
                    if(conn != null)
                        conn.close();
                } catch(SQLException se) {
                    se.printStackTrace();
                }//end finally try
            }
            return output;
        }


    /****************************************************************
      To Insert a new Brand record
     ***************************************************************/

    @POST
        @Produces(MediaType.TEXT_PLAIN)
        //@Consumes("application/json")
        @Path("/insert")
        public String insertBrand(@FormParam("brandName") String brandName,
                @FormParam("categoryId") int categoryId) {

            Connection conn = null;
            PreparedStatement pstmt = null;
            //Employee emp = null;
            String output = "Updated ";

            try{
                conn = getConnected();
                //stmt = conn.createStatement();
                output =brandName;
                String sql = "insert into brand(brandName, categoryId) values(?, ?)";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, brandName);
                pstmt.setInt(2, categoryId);
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
                }//end finally try
            }
            return output;
        }
}
