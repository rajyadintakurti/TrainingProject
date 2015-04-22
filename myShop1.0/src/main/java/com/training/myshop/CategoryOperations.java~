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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import java.net.*;


@Path("/category")
public class CategoryOperations {

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
      To list the Categories
     **************************************************************/

        @GET
        @Produces(MediaType.APPLICATION_JSON)
        @Path("/list")
        public List<Category> categoryList() {
            Connection conn = null;
            List<Category> categoryList = new ArrayList<Category>();
            Statement stmt = null;
            try {
                conn = getConnected();
                stmt = conn.createStatement();
                String sql = "select * from category";
                ResultSet rs = stmt.executeQuery(sql);
                while(rs.next()) {
                    Category category = new Category();
                    category.setCategoryId(rs.getInt("categoryId"));
                    category.setCategoryName(rs.getString("categoryName"));
                    category.setCategoryDescription(rs.getString("categoryDescription"));
                    //add this category object to the categoryList object
                    categoryList.add(category);
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
            return categoryList;
        }

    /**************************************************************
      To list the Categories by BrandId
     **************************************************************

        @GET
        @Produces(MediaType.APPLICATION_JSON)
        @Path("/list/{brand}")
        public List<Category> categoryListByBrand(@PathParam("brand") int brandId) {
            Connection conn = null;
            List<Category> categoryList = new ArrayList<Category>();
            Statement stmt = null;
            try {
                conn = getConnected();
                stmt = conn.createStatement();
                String sql = "select cat.categoryName,cat.categoryId,cat.categoryDescription from product prod, category cat where prod.categoryId = cat.categoryId AND prod.brandId = " + brandId;
                ResultSet rs = stmt.executeQuery(sql);
                while(rs.next()) {
                    Category category = new Category();
                    category.setCategoryId(rs.getInt("categoryId"));
                    category.setCategoryName(rs.getString("categoryName"));
                    category.setCategoryDescription(rs.getString("categoryDescription"));
                    //add this category object to the categoryList object
                    categoryList.add(category);
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
            return categoryList;
        }

        /****************************************************************
          To update an existing Category
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
          To Delete an existing category
          ***************************************************************/

        @GET
        @Produces(MediaType.TEXT_PLAIN)
        @Path("/delete")
        public String deleteCategory(@QueryParam("name") String categoryName) {

            Connection conn = null;
            PreparedStatement stmt = null;
            String output = "Deleted ";

            try{
                conn = getConnected();
                String sql = "delete from category where categoryName = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, categoryName);
                stmt.executeUpdate();
                output = output + " record with name" + categoryName + " successfully";
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
          To Insert a new Category record
          ***************************************************************/

        @POST
        @Produces(MediaType.TEXT_PLAIN)
        //@Consumes("application/json")
        @Path("/insert")
        public String insertCategory(
                @FormParam("categoryName") String categoryName,
                @FormParam("categoryDescription") String categoryDescription) {

            Connection conn = null;
            PreparedStatement pstmt = null;
            //Employee emp = null;
            String output = "Updated ";

            try{
                conn = getConnected();
                //stmt = conn.createStatement();
                output =categoryName;
                String sql = "insert into category(categoryName, categoryDescription) values(?, ?)";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, categoryName);
                pstmt.setString(2, categoryDescription);
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
        
        @POST
        @Consumes(MediaType.TEXT_PLAIN)
        @Produces("application/json")
        @Path("/search/{value}")
        public List<SearchCategory> search(@PathParam("value") String value) throws SQLException {
            //value = value+10;
            Connection conn = null;
            Statement stmt = null;
            String str = null;
            String categoryName = null;
            String brandName = null;
            String modelName = null;
            String modelDescription = null;
            float price = 0.0f;
            //int brandId=0;
            //String categoryName = null;
            List<SearchCategory> searchList = new ArrayList<SearchCategory>();
            try {
                conn = getConnected();
                stmt = conn.createStatement();
                if(isInteger(value)) {
                    int searchId = Integer.parseInt(value);
                    String brandIdsql = "select c.categoryName,b.brandName,m.modelName,m.price,m.modelDescription from category c,brand b,model m where c.categoryId="+searchId+" and c.categoryId=b.categoryId and b.brandId = m.brandId";
                    ResultSet rs = stmt.executeQuery(brandIdsql);
                    while(rs.next()) {
                        SearchCategory searchCategory = new SearchCategory();
searchCategory.setCategoryName(rs.getString("c.categoryName"));
searchCategory.setBrandName(rs.getString("b.brandName"));
searchCategory.setModelName(rs.getString("m.modelName"));
searchCategory.setPrice(rs.getFloat("m.price"));
searchCategory.setModelDescription(rs.getString("m.modelDescription"));
                        searchList.add(searchCategory);
                    }
                    rs.close();
                    //return searchList;
                  }
                else {
                    //return searchList;
                    String checkBrandName = null;
                    String searchItem = value;
                    String searchBrandName = "select brandName from brand where brandName LIKE '%"+value+"%'";
                    PreparedStatement ps1 = conn.prepareStatement(searchBrandName);
                    //ps1.setString(1,"%"+searchItem+"%");
                    ResultSet rs1 = ps1.executeQuery();
                   /* while(rs1.next()) {
                        checkBrandName = rs1.getString("brandName");
                    }*/
                    if (rs1.next()) {
                        String brandsql = "select c.categoryName,b.brandName,m.modelName,m.price,m.modelDescription from category c,brand b,model m where b.brandName LIKE '%"+searchItem+"%' and c.categoryId=b.categoryId and b.brandId = m.brandId";
                        ResultSet rs = stmt.executeQuery(brandsql);
                        while(rs.next()) {
                            SearchCategory searchCategory = new SearchCategory();
searchCategory.setCategoryName(rs.getString("c.categoryName"));
searchCategory.setBrandName(rs.getString("b.brandName"));
searchCategory.setModelName(rs.getString("m.modelName"));
searchCategory.setPrice(rs.getFloat("m.price"));
searchCategory.setModelDescription(rs.getString("m.modelDescription"));
                            searchList.add(searchCategory);
                        }
                         //rs.close();
                         //rs1.close();
                    }
                    else if(!rs1.next()) {
                        String checkModelName = null;
                        String searchModelName = " select modelName from model where modelName LIKE '%"+value+"%'";
                         ResultSet rs2 = stmt.executeQuery(searchModelName);
                         /*while(rs2.next()) {
                             checkModelName = rs2.getString("modelName");
                         }*/
                         if(rs2.next()) {
                             String modelsql = "select c.categoryName,b.brandName,m.modelName,m.price,m.modelDescription from category c,brand b,model m where m.modelName LIKE '%"+value+"%' and c.categoryId=b.categoryId and b.brandId = m.brandId";
                             ResultSet rs = stmt.executeQuery(modelsql);
                             while(rs.next()) {
                                SearchCategory searchCategory = new SearchCategory();
searchCategory.setCategoryName(rs.getString("c.categoryName"));
searchCategory.setBrandName(rs.getString("b.brandName"));
searchCategory.setModelName(rs.getString("m.modelName"));
searchCategory.setPrice(rs.getFloat("m.price"));
searchCategory.setModelDescription(rs.getString("m.modelDescription"));
                                searchList.add(searchCategory);
                            }
                            //rs2.close();
                            //rs.close();
                        }
                        else if(!rs2.next()) {
                            String searchCategoryName = "select categoryName from category where categoryName LIKE '%"+value+"%'";
                            ResultSet rs3 = stmt.executeQuery(searchCategoryName);
                            if(rs3.next()) {
                                 String categorysql = "select c.categoryName,b.brandName,m.modelName,m.price,m.modelDescription from category c,brand b,model m where c.categoryName LIKE '%"+value+"%' and c.categoryId=b.categoryId and b.brandId = m.brandId";
                                 ResultSet rs4 = stmt.executeQuery( categorysql);
                                 while(rs4.next()) {
                                    SearchCategory searchCategory = new SearchCategory();
searchCategory.setCategoryName(rs4.getString("c.categoryName"));
searchCategory.setBrandName(rs4.getString("b.brandName"));
searchCategory.setModelName(rs4.getString("m.modelName"));
searchCategory.setPrice(rs4.getFloat("m.price"));
searchCategory.setModelDescription(rs4.getString("m.modelDescription"));
                                    searchList.add(searchCategory);
                                }
                                rs1.close();
                                rs2.close();
                                rs3.close();
                                rs4.close();

                            }
                        }
                        //  rs.close();
                    }

                    else {
                         return searchList;
                    }
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }
             return searchList;
        }
        public static boolean isInteger(String str) {
    if (str == null) {
        return false;
    }
    int length = str.length();
    if (length == 0) {
        return false;
    }
    int i = 0;
    if (str.charAt(0) == '-') {
        if (length == 1) {
            return false;
        }
        i = 1;
    }
    for (; i < length; i++) {
        char c = str.charAt(i);
        if (c <= '/' || c >= ':') {
            return false;
        }
    }
    return true;
}

}
