package com.goeuro.util;

import com.goeuro.model.Position;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.List;

/**
 * Util Tester.
 *
 * @author <Pelumi>
 * @version 1.0
 * @since <pre>Jul 5, 2015</pre>
 */
public class UtilTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: queryService(String url)
     */
    @Test
    public void testQueryService() throws Exception {
        String sampleQuery = "http://api.goeuro.com/api/v2/position/suggest/en/berlin";
        String sampleQuery1 = "http://api.goeuro.com/api/v2/position/suggest/en/creepynonexistentcity";

        //testing service with valid city
        String response = Util.queryService(sampleQuery);
        Assert.assertNotNull("Test failed for query url: " + sampleQuery1, response);

        //testing service query with non existent city
        String response1 = Util.queryService(sampleQuery1);
        Assert.assertNotNull("Test failed for query with non-existent city at url: " + sampleQuery1, response1);
    }

    /**
     * Method: fromJson(Class classType, String jsonString)
     */
    @Test
    public void testFromJson() throws Exception {
        String sampleJson1 = "\n" +
                "[{\"_id\":398764,\"key\":null,\"name\":\"Lagos\",\"fullName\":\"Lagos, Portugal\",\"iata_airport_code\":null,\"type\":\"location\",\"country\":\"Portugal\",\"geo_position\":{\"latitude\":37.10202,\"longitude\":-8.67422},\"locationId\":30980,\"inEurope\":true,\"countryCode\":\"PT\",\"coreCountry\":false,\"distance\":null},{\"_id\":446557,\"key\":null,\"name\":\"Lagosanto\",\"fullName\":\"Lagosanto, Italy\",\"iata_airport_code\":null,\"type\":\"location\",\"country\":\"Italy\",\"geo_position\":{\"latitude\":44.75861,\"longitude\":12.14361},\"locationId\":146172,\"inEurope\":true,\"countryCode\":\"IT\",\"coreCountry\":true,\"distance\":null},{\"_id\":381657,\"key\":null,\"name\":\"Lagos\",\"fullName\":\"Lagos, Greece\",\"iata_airport_code\":null,\"type\":\"location\",\"country\":\"Greece\",\"geo_position\":{\"latitude\":41.45,\"longitude\":26.46667},\"locationId\":13851,\"inEurope\":true,\"countryCode\":\"GR\",\"coreCountry\":false,\"distance\":null},{\"_id\":6184154,\"key\":null,\"name\":\"Lagos (malaga)\",\"fullName\":\"Lagos (malaga), Spain\",\"iata_airport_code\":null,\"type\":\"location\",\"country\":\"Spain\",\"geo_position\":{\"latitude\":36.744722,\"longitude\":-4.019444},\"locationId\":171549,\"inEurope\":true,\"countryCode\":\"ES\",\"coreCountry\":true,\"distance\":null},{\"_id\":314055,\"key\":null,\"name\":\"Lagos\",\"fullName\":\"Lagos (LOS), Nigeria\",\"iata_airport_code\":\"LOS\",\"type\":\"airport\",\"country\":\"Nigeria\",\"geo_position\":{\"latitude\":6.574722,\"longitude\":3.318611},\"locationId\":null,\"inEurope\":false,\"countryCode\":\"NG\",\"coreCountry\":false,\"distance\":null}]";
        String sampleJson2 = "[]";

        //testing deserialization of normal response
        List<Position> positions1 = Util.fromJson(Position.class, sampleJson1);
        Assert.assertEquals(5, positions1.size());
        Assert.assertEquals(398764, positions1.get(0).get_id());

        //testing deserialization of empty response
        List<Position> positions2 = Util.fromJson(Position.class, sampleJson2);
        Assert.assertEquals("Test failed for empty response", 0, positions2.size());

        //testing deserialization using null class
        List<Position> positions3 = Util.fromJson(null, sampleJson1);
        Assert.assertEquals("Test failed for null class type", 0, positions3.size());

        //testing deserialization using null json string
        List<Position> positions4 = Util.fromJson(Position.class, null);
        Assert.assertEquals("Test failed for null json string", 0, positions4.size());
    }
} 
