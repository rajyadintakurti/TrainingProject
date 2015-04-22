package com.training.myshop;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.FormParam;
import javax.ws.rs.PathParam;
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

@Path("/model")
public class ModelOperations {

    Connection conn = null;
    Statement stmt = null;
    String output="";
    ResultSet rs = null;

    /************************************************
      Getting Connection
     ************************************************/

    public Connection getConnected(){
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

    /************************************************
      This method displays all the Models detailsi
     ************************************************/

    @GET
        @Path("/list/{brand}")
        @Produces("application/json")
        public List<Model> getModelDetails(@PathParam("brand") int brandId) throws SQLException {
            List<Model> modelList = new ArrayList<Model>();
            try{
                conn = getConnected();
                stmt = conn.createStatement();
                rs = stmt.executeQuery("select * from model where brandId =" + brandId);
                while(rs.next()){
                    int mid = rs.getInt("modelId");
                    String mname = rs.getString("modelName");
                    float price = rs.getFloat("price");
                    int qty = rs.getInt("quantity");
                    String des = rs.getString("modelDescription");
                    int bid = rs.getInt("brandId");
                    Model model = new Model();
                    model.setModelId(mid);
                    model.setModelName(mname);
                    model.setPrice(price);
                    model.setQuantity(qty);
                    model.setModelDescription(des);
                    model.setBrandId(bid);
                    modelList.add(model);
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
            return modelList;
        }//getModelDetails()


    /***********************************************
      Inserts a Model new record
     ************************************************/

    @POST
        @Path("/insert")
        @Produces(MediaType.TEXT_PLAIN)
        //@Consumes("application/json")
        public String insertModel(@FormParam("modelName") String modelName,
                @FormParam("price") float price,
                @FormParam("quantity") int quantity,
                @FormParam("modelDescription") String modelDescription,
                @FormParam("brandId") int brandId) throws SQLException {
            try{
                conn = getConnected();
                String sql = "insert into model(modelName, price, quantity, modelDescription, brandId) values(?,?,?,?,?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, modelName);
                ps.setFloat(2, price);
                ps.setInt(3, quantity);
                ps.setString(4, modelDescription);
                ps.setInt(5, brandId);
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
            return output;
        }//insertModel()

    /**********************************************************
      Deletes a Model record
     **********************************************************/

    @GET
        @Path("/delete")
        @Produces("application/json")
        public Response deleteModel() throws SQLException {
            Model model = new Model();
            model.setModelId(3005);

            try{
                conn = getConnected();
                PreparedStatement ps = conn.prepareStatement("delete from model where modelId = ?");
                ps.setInt(1, model.getModelId());
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
        }//deleteModel()

    /*************************************************
      Updates a Model record
     *************************************************/

    @GET
        @Path("/update")
        @Produces("application/json")
        public Model updateModel() throws SQLException {
            Model model = new Model();

            try{
                conn = getConnected();
                PreparedStatement ps = conn.prepareStatement("update model set modelName = ?, price = ?, quantity = ?, modelDescription = ?, brandId = ? where modelId = ?");
                model.setModelId(1003);
                model.setModelName("moto e");
                model.setPrice(9999);
                model.setQuantity(8);
                model.setModelDescription("smart moto e");
                model.setBrandId(1003);

                ps.setString(1, model.getModelName());
                ps.setFloat(2, model.getPrice());
                ps.setInt(3, model.getQuantity());
                ps.setString(4, model.getModelDescription());
                ps.setInt(5, model.getBrandId());
                ps.setInt(6, model.getModelId());
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
            return model;
        }//updateModel()

}//ModelOperations


