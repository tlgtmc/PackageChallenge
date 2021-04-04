package com.mobiquity.packer;

import com.mobiquity.domain.Pack;
import com.mobiquity.exception.APIException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * FileContentParser class gets the file from the given filePath variable,
 * and parses this file.
 *
 * @author tatmaca
 */

public class FileContentParser {

    protected FileContentParser() {
    }

    /**
     * parse() method gets file on given filePath.
     * File parsed line by line with the help of LineParser class.
     *
     * @param filePath path to the file that will be parsed
     * @return List of Pack(s)
     * @throws APIException if parsing process gets an exception, APIException will be thrown with the specified cause.
     */
    protected List<Pack> parse(String filePath) throws APIException {

        List<Pack> packList = new ArrayList<>();

        LineParser lineParser = new LineParser();
        try (InputStream inputStream = Files.newInputStream(Paths.get(filePath))) {
            try (Scanner scanner = new Scanner(inputStream)) {
                while (scanner.hasNextLine()) {
                    String inputLine = scanner.nextLine();
                    packList.add(lineParser.validateAndParse(inputLine));
                }
            }
        } catch (IOException e) {
            throw new APIException(e.getMessage());
        }
        return packList;
    }

}
