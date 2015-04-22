package com.training.myshop;

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

@Path("/image")
public class ImageOperations {

    Connection conn = null;
    Statement stmt = null;
    String output="";
    ResultSet rs = null;

    /******************************************************
      Getting Connection
     ******************************************************/

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

    /*****************************************************
      This method displays all the image details
     *****************************************************/

    @GET
        @Path("/list")
        @Produces("application/json")
        public List<Image> getImageDetails() throws SQLException {
            List<Image> imageList = new ArrayList<Image>();
            try{
                conn = getConnected();
                stmt = conn.createStatement();
                rs = stmt.executeQuery("select * from image");
                while(rs.next()){
                    String imageId = rs.getString("imageId");
                    int modelId = rs.getInt("modelId");
                    String des = rs.getString("imageDescription");
                    Image img = new Image();
                    img.setImageId(imageId);
                    img.setModelId(modelId);
                    img.setImageDescription(des);
                    imageList.add(img);
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
            return imageList;
        }//getImageDetails()

    /*************************************************
      Inserts a Image new record
     **************************************************/

    @POST
        @Path("/insert")
        @Consumes("application/json")
        public Response insertImage(Image img) throws SQLException {
            try{
                conn = getConnected();
                String sql = "insert into image(imageId, modelId,imageDescription) values(?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, img.getImageId());
                ps.setInt(2, img.getModelId());
                ps.setString(3, img.getImageDescription());
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
        }//insertImage()

    /*******************************************************
      Updates a Image record
     ******************************************************/

    @GET
        @Path("/update")
        @Produces("application/json")
        public Image updateImage() throws SQLException {
            Image img = new Image();
            img.setImageId("2003");
            img.setModelId(1003);
            img.setImageDescription("Smart phone");

            try{
                conn = getConnected();
                PreparedStatement ps = conn.prepareStatement("update image set modelId = ?, imageDescription = ? where imageId = ?");
                ps.setInt(1, img.getModelId());
                ps.setString(2, img.getImageDescription());
                ps.setString(3, img.getImageId());
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
            return img;
        }//updateImage()

    /***********************************************************
      Deletes a Image record
     **********************************************************/

    @GET
        @Path("/delete")
        @Produces("application/json")
        public Response deleteImage() throws SQLException {
            Image img = new Image();
            img.setImageId("1002");

            try{
                conn = getConnected();
                PreparedStatement ps = conn.prepareStatement("delete from image where imageId = ?");
                ps.setString(1, img.getImageId());
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
        }//deleteImage()

}//ImageOperations
