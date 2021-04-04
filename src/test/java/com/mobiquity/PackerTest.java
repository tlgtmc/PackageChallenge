package com.mobiquity;

import com.mobiquity.exception.APIException;
import com.mobiquity.packer.Packer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author tatmaca
 */

public class PackerTest {

    private static final String inputFilePathLocal = Paths.get("src","main","test","resources","example_input").toString();
    private static final Path outputFilePathLocal = Paths.get("src","main","test","resources","example_output");

    private static String expectedOutputTest = "";

    @BeforeAll
    static void prepareVariables() throws APIException {
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
    }

    @Test
    public void packerTest() throws APIException {
        String packed = Packer.pack(inputFilePathLocal);
        assertEquals(expectedOutputTest, packed);

    }
}
