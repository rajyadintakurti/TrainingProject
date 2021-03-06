package com.training.myshop;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.ClientResponse;
import java.io.IOException;
import javax.ws.rs.core.MediaType;
//import org.json.JSONObject;
import java.util.StringTokenizer;  
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JSONArray;

//import org.json.JSONObject;
public class SearchServlet extends HttpServlet {
    public void init() {
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType("text/html");
        try {
            java.io.PrintWriter pw = response.getWriter();
            String searchitem1 = request.getParameter("stname");
            int length = searchitem1.length();
/************check weather the user given the serching item or item*************/
/************if given**************/
            String searchitem = "";
            StringTokenizer st = new StringTokenizer(searchitem1," ");
            while(st.hasMoreTokens()) {
                searchitem = searchitem+st.nextToken();
            }
            if(length!=0) {
                ClientConfig config = new DefaultClientConfig();
                Client client = Client.create(config);
                WebResource service = client.resource("http://172.20.105.121:8080/myShop1.0/rest/category/search/"+searchitem);
                //JSONObject obj = new JSONObject();
                ClientResponse cresponse = service.type("application/json").post(ClientResponse.class, searchitem);
                String output = cresponse.getEntity(String.class);
                ClientResponse response2 = service.accept("application/json").post(ClientResponse.class);
                JSONArray outerArray = (JSONArray)JSONSerializer.toJSON( response2.getEntity(String.class) );
                if(outerArray.size()!=0) {
                JSONObject displayjson = (JSONObject) outerArray.get(0);
                JSONArray displaynameArray = displayjson.names();
                pw.println("<html><head><meta charset=\"utf-8\"><link rel=\"shortcut icon\" href=\"favicon.ico\"><title>myshop</title><script src=\"https://code.jquery.com/jquery-1.10.2.js\"></script><script src=\"https://code.jquery.com/ui/1.11.4/jquery-ui.js\"></script><title>Search Operation on categoryid</title><body bgcolor = \"#DDD9D9\" align=\"center\"><form action = \"http://172.20.105.121:8080/myShop1.0/index.jsp\" method = \"post\"><table width=\"70%\" align = \"center\"");
                pw.println("<br><br><br><br><br><tr><th style=\"text-align:center\">");
                pw.println(displaynameArray.getString(0));
                pw.println("</th><th style=\"text-align:center\">");
                pw.println(displaynameArray.getString(1));
                pw.println("</th><th style=\"text-align:center\">");
                pw.println(displaynameArray.getString(2));
                pw.println("</th><th style=\"text-align:center\">");
                pw.println(displaynameArray.getString(3));
                pw.println("</th><th style=\"text-align:center\">");
                pw.println(displaynameArray.getString(4));
                pw.println("</th></tr>");
                for (int i = 0; i < outerArray.size(); i++) {
                    JSONObject json = (JSONObject) outerArray.get(i);
                    JSONArray nameArray = json.names();
                    JSONArray valArray = json.toJSONArray(nameArray);
                    pw.println("<tr>");
                    for ( int j = 0; j < valArray.size(); j++) {
                         pw.println("<td id = "+valArray.getString(j)+" style=\"text-align:center\">");
                         pw.println(valArray.getString(j));
                         pw.println("</td>");
                    }
                    pw.println("<td style=\"text-align:center\"><input type=\"checkbox\" value=\"Add to Cart\">Add to Cart</td>");
                    pw.println("</tr>");
                    //pw.println(\n);
                    //pw.println(valArray);
                }
                pw.println("<tr><td></td></tr><tr><td></td></tr><tr><td colspan=\"5\" align = \"center\"><input type = \"submit\" value = \"BACK\" ></td></tr>");
pw.println("</table></form></body></html>");
                }
                else {
                    pw.println("<html><body bgcolor = \"#DDD9D9\" align = \"center\"><br><br><br><br><br>No Search Results are Found</body></html>");
                }
                //JSONArray jarray = json.getJSONArray("modelName");
                //int size = outerArray.size();
                //pw.println(valArray.getString(1));
                //output = response2.getEntity(JSONObject.class);

               /* //pw.println(output);
                StringTokenizer st = new StringTokenizer(output,"}");
                String str2 ="";
                String str3 = "";
                //String st2 = st.toString();
               // pw.println(response2.getEntityInputStream());
               // StringTokenizer st1 = new StringTokenizer(st,"{");
                pw.println("<html><body>");
                while(st.hasMoreTokens()) {
                    //pw.println(st.nextToken());
                    str2 = str2+st.nextToken();
                    //pw.println(" <br>");
                }
                //pw.println(st2);
                StringTokenizer st1 = new StringTokenizer(str2,"{");
                while(st1.hasMoreTokens()) {
                    //pw.println(st.nextToken());
                    str3 = str3+st1.nextToken();
                    //pw.println(" <br>");
                }
                pw.println(str3);
                pw.println("</body></html>");
                /*pw.println("<html>");
                pw.println("<body align=\"center\">");
                pw.println("<form action = \"http://172.20.105.121:8080/myShop1.0/index.html\" method = \"post\">");
                pw.println("<input type = \"text\" value = "+output+" />");
                pw.println("<input type = \"submit\" value = \"BACK\" >");
//              pw.println("<input type = \"button\" value = \"AddTOCART\" >");
                pw.println("</form>");
                pw.println("</body>");
                pw.println("</html>");*/
            } /****if not given***/else {
response.sendRedirect("http://172.20.105.121:8080/myShop1.0/index.jsp");
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException {
            doPost(request,response);
            throw new ServletException("GET method used with " +
                    getClass( ).getName( )+": POST method required.");
        }
}

