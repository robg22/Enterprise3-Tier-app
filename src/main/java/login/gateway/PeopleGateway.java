package login.gateway;

import login.exception.UnauthorizedException;
import mvc.controllers.MainController;
import mvc.models.People;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



public class PeopleGateway {

    //Responsible for moving data back and forth between the database and the client
    public List<People> fetchPeople(String token){
        CloseableHttpClient httpClient = null; //Creating client
        CloseableHttpResponse response = null;
        try {
            httpClient = HttpClients.createDefault(); //Creating Client

            HttpGet getRequest = new HttpGet("http://localhost:8080/people"); //Create Get request form URL
            getRequest.setHeader("Authorization",token); //Authorize
            response = httpClient.execute(getRequest);


            switch(response.getStatusLine().getStatusCode()){
                case 200:
                    System.out.println("SUCCESS in pplGateway");
                    List<People> people = new ArrayList<>();

                    HttpEntity entity = response.getEntity(); //get entity so you can access the body
                    String responseString = EntityUtils.toString(entity,StandardCharsets.UTF_8); //Create a response String so you can extract info
                    EntityUtils.consume(entity);

                    JSONArray pplList = new JSONArray(responseString);
                    for(Object rawPpl: pplList){
                        JSONObject peopleInfo = (JSONObject) rawPpl;
                        People people2 = new People(peopleInfo.getInt("id"),peopleInfo.getString("firstName"),"lastName", LocalDate.now(),0);
                        people.add(people2);
                    }
                    return people;
                case 401:
                    System.out.println("401 FAIL in pplGateway");
                    throw new UnauthorizedException("fetch people failed");
                default:
                    System.out.println("OTHER in pplGateway");
                    //throw new UnauthorizedException("Other error");
            }


        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new UnauthorizedException(e);
        }
        return new ArrayList<>();
    }
}
