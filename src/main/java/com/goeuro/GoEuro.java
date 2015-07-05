package com.goeuro;

import com.goeuro.model.Position;
import com.goeuro.model.PositionData;
import com.goeuro.util.CSVTool;
import com.goeuro.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Pelumi<pelumi@maven.ai>
 *         Created on 05/07/15 at 10:08.
 */
public class GoEuro {
    private static final Logger logger = Logger.getLogger(GoEuro.class.getName());
    private static final String POSITION_ENDPOINT = "http://api.goeuro.com/api/v2/position/suggest/en/%s";

    public void fetchPositions(String cityName) {
        logger.info("Querying position Service at: " + String.format(POSITION_ENDPOINT, cityName));
        String response = Util.queryService(String.format(POSITION_ENDPOINT, cityName));
        if (response == null) {
            logger.info("There was a problem querying the position service.");
        } else {
            //deserialise json
            List<Position> positions = Util.fromJson(Position.class, response);
            if (positions.size() == 0) {
                logger.info("The position list returned was empty, CSV will not be created");
            } else {
                List<PositionData> positionDataList = new ArrayList<>();
                for (Position position : positions) {
                    positionDataList.add(position.toPositionData());
                }
                //write to csv file
                CSVTool.getInstance().listToCSV(positionDataList, PositionData.class);
            }
        }
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            logger.info("Location not specified, expected usage format is: java -jar GoEuroTest.jar CITY_NAME\nExample: java -jar GoEuroTest.jar berlin");
            System.exit(0);
        }
        else if(args.length > 1){
            logger.info("Only");
        }
        else {
            String cityName = args[0];
            GoEuro goEuro = new GoEuro();
            goEuro.fetchPositions(cityName);
        }
    }
}
