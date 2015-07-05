package com.goeuro.util;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Pelumi<pelumi@maven.ai>
 * A utility class to generate a CSV file based on a list of objects
 *         Created on 27/06/15 at 02:56.
 */
public class CSVTool {
    private static final Logger logger = Logger.getLogger(CSVTool.class.getName());
    public static CSVTool instance = null;

    public static CSVTool getInstance() {
        if (instance == null){
            instance = new CSVTool();
        }
        return instance;
    }

    private CSVTool() {
        //prevent external instantiation
    }

    /**
     * converts the fields in an object to a list of title strings
     *
     * @param classType the class whose fields should be retrieved
     * @return a string array containing field names as Title phrases
     */
    private String[] getFields(Class classType) {
        Field[] fields = classType.getDeclaredFields();
        String[] fieldNames = new String[fields.length];

        for (int i = 0; i < fields.length; i++) {
            //split camel case and capitalise words
            fieldNames[i] = WordUtils.capitalizeFully(StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(fields[i].getName()), " "));
        }
        return fieldNames;
    }

    /**
     * Takes in a raw list type and writes it fields to CSV.
     * It naively assumes that all the fields are primitive types and implement a proper toString() method.
     *
     * @param records the list of items to be written to csv
     * @return true or false depending on success
     */
    public void listToCSV(List records, Class classType) {
        FileWriter fileWriter = null;
        CSVPrinter csvFilePrinter = null;

        //Create the CSVFormat object with "\n" as a record delimiter
        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator("\n");
        String[] FILE_HEADER = getFields(classType);
        try {

            //initialize FileWriter object and save file with class name
            fileWriter = new FileWriter(classType.getSimpleName() + ".csv");

            //initialize CSVPrinter object
            csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);

            //Create CSV file header
            csvFilePrinter.printRecord(FILE_HEADER);

            //Write a new student object list to the CSV file
            for (Object record : records) {
                List<String> dataRecord = new ArrayList();
                //retrieve object fields
                Field[] fields = classType.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    dataRecord.add(field.get(record).toString());
                }
                csvFilePrinter.printRecord(dataRecord);
            }
            logger.info("CSV was written successfully to: " + classType.getSimpleName() + ".csv. It contains " + records.size() + "records.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in CsvFileWriter");
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
                csvFilePrinter.close();
            } catch (IOException e) {
                logger.log(Level.WARNING, "Error while flushing or closing fileWriter or csvPrinter");
                e.printStackTrace();
            }
        }
    }
}
