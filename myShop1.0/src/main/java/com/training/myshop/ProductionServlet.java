package com.training.myshop;

import java.io.*;
import java.util.*;
import java.lang.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.output.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import org.imgscalr.Scalr.*;
import org.imgscalr.*;
import org.imgscalr.Scalr.Method;
import org.json.JSONObject;
import java.net.URL;
import java.net.URLDecoder;
import java.net.HttpURLConnection;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import javax.ws.rs.core.MultivaluedMap;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import java.util.ArrayList;
import java.util.List;
import com.sun.jersey.api.client.ClientResponse;



public class ProductionServlet extends HttpServlet {
    private boolean isMultipart;
    private String tempPath;
    private String tempPath1;
    private String thumbPath;
    private String mediumPath;
    private String largePath;
    String fileName="";
    private int maxFileSize = 50 * 1024;
    private int maxMemSize = 4 * 1024;
    private File file ;
    URL url = null;
    int modelId = 0;

    public void init( ) {
        // Get the file location where it would be stored.
        tempPath = getServletContext().getInitParameter("file-upload-temp");
        tempPath1 = getServletContext().getInitParameter("file-upload-temp1");
        thumbPath = getServletContext().getInitParameter("file-upload-thumb");
        mediumPath = getServletContext().getInitParameter("file-upload-medium");
        largePath = getServletContext().getInitParameter("file-upload-large");
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException {

        int categoryId=0;
        int brandId=0;
        // Check that we have a file upload request
        isMultipart = ServletFileUpload.isMultipartContent(request);
        java.io.PrintWriter out = response.getWriter( );
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // maximum size that will be stored in memory
        factory.setSizeThreshold(maxMemSize);
        // Location to save data that is larger than maxMemSize.
        factory.setRepository(new File(tempPath));
        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);
        // maximum file size to be uploaded.
        upload.setSizeMax( maxFileSize );
        try {
            // Parse the request to get file items.
            List fileItems = upload.parseRequest(request);
            // Process the uploaded file items
            Iterator i = fileItems.iterator();
            while ( i.hasNext () ) {
                FileItem fi = (FileItem)i.next();
                if( fi.isFormField() ) {
                    if( fi.getFieldName().equals("model") ) {
                        modelId = Integer.parseInt(fi.getString());
                    }else if( fi.getFieldName().equals("brand") ) {
                        brandId = Integer.parseInt(fi.getString());
                    }else if( fi.getFieldName().equals("category") ) {
                        categoryId = Integer.parseInt(fi.getString());
                    }
                }
                else {
                    // Get the uploaded file parameters
                    String fieldName = fi.getFieldName();
                    //JSONObject obj = new JSONObject();
                    fileName = fi.getName();
                    if ( !(fileName.isEmpty())) {
                        String contentType = fi.getContentType();
                        boolean isInMemory = fi.isInMemory();
                        long sizeInBytes = fi.getSize();
                        /* **************** Write the file ****************** */
                        if( fileName.lastIndexOf("\\") >= 0 ) {
                            file = new File( tempPath + fileName.substring( fileName.lastIndexOf("\\"))) ;
                        } else {
                            file = new File( tempPath + fileName.substring(fileName.lastIndexOf("\\")+1)) ;
                        }
                        fi.write( file ) ;
                        transform(response,request);
                    } else {
                        String defaultFileName= "";
                        File defaultFile = new File(tempPath1);
                        File[] listOfFiles = defaultFile.listFiles();
                        for (int k = 0; k < listOfFiles.length; k++) {
                            if (listOfFiles[k].isFile()) {
                               defaultFileName = listOfFiles[k].getName();
                            }
                        }
                    }
                }
            }
            //common task
            ClientConfig config = new DefaultClientConfig();
            Client client = Client.create(config);
            WebResource service = client.resource("http://172.20.105.121:8080/myShop1.0/rest/product/insert");
            JSONObject obj = new JSONObject();
            obj.put("modelId",modelId);
            obj.put("categoryId",categoryId);
            obj.put("brandId",brandId);
            ClientResponse cresponse = service.type("application/json").post(ClientResponse.class, obj.toString());
            String output = cresponse.getEntity(String.class);
            out.print("Successful " + output);

        }catch(Exception ex) {
            System.out.println(ex);
        }
    } 
    
    public void transform(HttpServletResponse response,HttpServletRequest request)throws ServletException, IOException {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(tempPath+fileName));
            convertThumbnails(image,response,request);
            convertMedium(image);
            convertLarge(image);
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
        
    public void convertThumbnails(BufferedImage image,HttpServletResponse response,HttpServletRequest request) throws ServletException, IOException {
        try {
            BufferedImage thumbImg = Scalr.resize(image, 150);
            java.io.PrintWriter out = response.getWriter( );
            //convert bufferedImage to outpurstream
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(thumbImg,"gif",os);
            int hcode = thumbImg.hashCode();
            File f2 = new File(thumbPath+hcode);
            ImageIO.write(thumbImg, "gif", f2);
            String hcode1 = Integer.toString(hcode);
            ClientConfig config = new DefaultClientConfig();
            Client client = Client.create(config);
            WebResource service = client.resource("http://172.20.105.121:8080/myShop1.0/rest/image/insert");
            JSONObject obj = new JSONObject();
            obj.put("imageId",hcode1);
            obj.put("imageDescription","thumb");
            obj.put("modelId", modelId);
            ClientResponse cresponse = service.type("application/json").post(ClientResponse.class, obj.toString());
            String output = cresponse.getEntity(String.class);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void convertMedium(BufferedImage image){
        try {
            BufferedImage mediumImg = Scalr.resize(image, 450);
            //convert bufferedImage to outpurstream
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(mediumImg,"png",os);
            //or wrtite to a file
            int hcode = mediumImg.hashCode();
            File f2 = new File(mediumPath+hcode);
            ImageIO.write(mediumImg, "png", f2);
            String hcode2 = Integer.toString(hcode);
            ClientConfig config = new DefaultClientConfig();
            Client client = Client.create(config);
            WebResource service = client.resource("http://172.20.105.121:8080/myShop1.0/rest/image/insert");
            JSONObject obj = new JSONObject();
            obj.put("imageId",hcode2);
            obj.put("imageDescription","medium");
            obj.put("modelId", modelId);
            ClientResponse cresponse = service.type("application/json").post(ClientResponse.class, obj.toString());
            String output = cresponse.getEntity(String.class);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void convertLarge(BufferedImage image){
        try {
            BufferedImage largeImg = Scalr.resize(image, 850);
            //convert bufferedImage to outpurstream
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(largeImg,"jpg",os);
            //or wrtite to a file
            int hcode = largeImg.hashCode();
            File f2 = new File(largePath+hcode);
            ImageIO.write(largeImg, "jpg", f2);
            String hcode3 = Integer.toString(hcode);
            ClientConfig config = new DefaultClientConfig();
            Client client = Client.create(config);
            WebResource service = client.resource("http://172.20.105.121:8080/myShop1.0/rest/image/insert");
            JSONObject obj = new JSONObject();
            obj.put("imageId",hcode3);
            obj.put("imageDescription","large");
            obj.put("modelId", modelId);
            ClientResponse cresponse = service.type("application/json").post(ClientResponse.class, obj.toString());
            String output = cresponse.getEntity(String.class);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
        throws ServletException, java.io.IOException {
            doPost(request,response);
            throw new ServletException("GET method used with " +
                    getClass( ).getName( )+": POST method required.");
        }
}
