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

@Path("/product")
public class ProductOperations {

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
      To list the Products
     **************************************************************/

        @GET
        @Produces(MediaType.APPLICATION_JSON)
        @Path("/list")
        public List<Product> productList() {
            Connection conn = null;
            List<Product> productList = new ArrayList<Product>();
            Statement stmt = null;
            String output;
            try {
                conn = getConnected();
                if( conn != null )
                    output = "connection is successful";
                stmt = conn.createStatement();
                String sql = "select * from product";
                ResultSet rs = stmt.executeQuery(sql);
                while(rs.next()) {
                    Product product = new Product();
                    product.setProductId(rs.getInt("productId"));
                    product.setModelId(rs.getInt("modelId"));
                    product.setCategoryId(rs.getInt("categoryId"));
                    product.setBrandId(rs.getInt("brandId"));
                    //add this product object to the productList object
                    productList.add(product);
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
            return productList;
        }


        /****************************************************************
          To update an existing Product
          ***************************************************************

        @GET
        @Produces(MediaType.TEXT_PLAIN)
        @Path("/update")
        public String updateEmployee(@QueryParam("id") int id) {

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
          To Delete an existing product
          ***************************************************************/

        @GET
        @Produces(MediaType.TEXT_PLAIN)
        @Path("/delete")
        public String deleteProduct(@QueryParam("product") int productId) {

            Connection conn = null;
            PreparedStatement stmt = null;
            String output = "Deleted ";

            try{
                conn = getConnected();
                String sql = "delete from product where productId = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, productId);
                stmt.executeUpdate();
                output = output + " record with name" + productId + " successfully";
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
          To Insert a new Product record
          ***************************************************************/

        @POST
        @Produces(MediaType.TEXT_PLAIN)
        @Path("/insert")
        public String insertProduct(Product product) {

            Connection conn = null;
            PreparedStatement pstmt = null;
            //Employee emp = null;
            String output = "Updated ";

            try{
                conn = getConnected();
                String sql = "insert into product(modelId, categoryId, brandId) values(?, ?, ?)";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, product.getModelId());
                pstmt.setInt(2, product.getCategoryId());
                pstmt.setInt(3, product.getBrandId());
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
