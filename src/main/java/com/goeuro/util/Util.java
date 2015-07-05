package com.goeuro.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Pelumi<pelumi@maven.ai>
 *         Created on 05/07/15 at 10:14.
 */
public class Util {
    private static Logger logger = Logger.getLogger(Util.class.getName());

    /**
     *
     * Queries an HTTP Service and returns the response as a string.
     * It handles only services that have a string as response.
     *
     * @param url the endpoint of the service to be queried
     *
     * @return a string containing the service response or null if there was an exception.
     */
    public static String queryService(String url) {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(url);
        String result = null;
        try {
            HttpResponse response = client.execute(httpGet);
            int code = response.getStatusLine().getStatusCode();
            if (code == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity);
            } else {
                logger.log(Level.SEVERE, "There was a problem querying the service. The status code in response was: " + code);
                return result;
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "There was a querying service as: " + url + "\nHere is the stacktrace:\n");
            e.printStackTrace();
        } finally {
            //close the client to release resources
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

/**
 * Deserialise json array to a list of objects
 *
 * @param classType the type of items in the list to be deserialised
 * @param jsonString the raw json string to be deserialised
 *
 * @return the list of deserialised items
 * */
    public static List fromJson(Class classType, String jsonString) {
       if (jsonString == null || classType == null) {
           logger.info("Attempting to deserialize json with illegal argument(s). The class type or jsonString cannot be null. Empty list will be returned");
           //return an empty list to ensure normal execution
           return new ArrayList();
        }
        ObjectMapper mapper = new ObjectMapper();
        List pojoList = null;
        try {
            pojoList = mapper.readValue(jsonString, TypeFactory.defaultInstance().constructCollectionType(List.class, classType));
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "There was an error deserialising Json for class: " + classType.getName() + "\nJson String was: " + jsonString);
            ex.printStackTrace();
        }
        return pojoList;
    }

}
