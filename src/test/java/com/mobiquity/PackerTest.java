package com.mobiquity;

import com.mobiquity.exception.APIException;
import com.mobiquity.packer.MessageUtil;
import com.mobiquity.packer.Packer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author tatmaca
 */

public class PackerTest {

    private static final String inputFilePathLocal = Paths.get("src","main","test","resources","example_input").toString();
    private static final Path outputFilePathLocal = Paths.get("src","main","test","resources","example_output");

    private static final String inputFileInvalidWeightPathLocal = Paths.get("src","main","test","resources","example_input_invalid_pack_weight").toString();
    private static final String inputFileInvalidIdPathLocal = Paths.get("src","main","test","resources","example_input_invalid_id").toString();
    private static final String inputFileInvalidCostPathLocal = Paths.get("src","main","test","resources","example_input_invalid_cost").toString();


    @Test
    public void packerValidInputTest() throws APIException {
        String expectedOutputTest = "";
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStream inputStream = Files.newInputStream(outputFilePathLocal)) {
            try (Scanner scanner = new Scanner(inputStream)) {
                while (scanner.hasNextLine()) {
                    if (stringBuilder.toString().length() > 0)
                        stringBuilder.append("\n");

                    stringBuilder.append(scanner.nextLine());
                }
                expectedOutputTest = stringBuilder.toString();
            }
        } catch (IOException e) {
        }
        String packed = Packer.pack(inputFilePathLocal);
        assertEquals(expectedOutputTest, packed);

    }

    @Test
    public void packerInvalidWeightTest() throws APIException {
        Exception exception = assertThrows(APIException.class, () -> {
            String packed = Packer.pack(inputFileInvalidWeightPathLocal);
        });

        String expectedMessage = "Invalid weight! Weight should be a value between 0 - 100.";
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void packerInvalidId() throws APIException {
        Exception exception = assertThrows(APIException.class, () -> {
            String packed = Packer.pack(inputFileInvalidIdPathLocal);
        });

        String expectedMessage = "Invalid item entry! Items must be like '(1,53.38,€45)'.";
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void packerInvalidCost() throws APIException {
        Exception exception = assertThrows(APIException.class, () -> {
            String packed = Packer.pack(inputFileInvalidCostPathLocal);
        });

        String expectedMessage = "Invalid cost! Cost should be a value between 0 - 100.";
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(actualMessage, expectedMessage);
    }

}
