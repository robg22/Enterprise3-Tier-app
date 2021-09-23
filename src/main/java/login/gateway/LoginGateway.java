package login.gateway;

import login.exception.UnauthorizedException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
                                                                //**** THIS IS A POST ****//
//When I login on the screen the login controller will call this function.
public class LoginGateway {
    //We could use this with a singleton
    /*
     * This will make a post request to wiremock and send the request and get a response
     */
    public static Session login(String userName, String password){
       /*   if(userName.equals("kiwi") && password.equals("123")){ return "i am a session token"; }
            throw new UnauthorizedException("login failed "); //Class I made
        */
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        try {
            httpClient = HttpClients.createDefault();
            HttpPost postRequest = new HttpPost("http://localhost:8080/login");

            //Use ths for submitting form data as raw json
            JSONObject formData = new JSONObject();
            formData.put("username",userName);
            formData.put("password",password);
            String formDataString = formData.toString();

            StringEntity reqEntity = new StringEntity(formDataString);
            postRequest.setEntity(reqEntity);

            //Execute it on client
            response = httpClient.execute(postRequest);

            //get status code to do work with it
            int statusCode = response.getStatusLine().getStatusCode();
            switch(statusCode){
                case 200:
                    //Get html result
                    HttpEntity entity = response.getEntity();
                    String strResponse = EntityUtils.toString(entity, StandardCharsets.UTF_8);
                    EntityUtils.consume(entity);
                    System.out.println("200 SUCCESSS");
                    Session session = new Session(strResponse); //Create Session object
                    return session;
                case 401:
                    System.out.println("401 FAIL");
                    throw new UnauthorizedException("login failed");
                default:
                    System.out.println("OTHER");
                    throw new UnauthorizedException("Other error");
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new UnauthorizedException(e);
        }finally {
            try{
                    if(response != null)
                        response.close();
                    if(httpClient != null)
                        httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                throw new UnauthorizedException(e);
                }
        }
    }
}
