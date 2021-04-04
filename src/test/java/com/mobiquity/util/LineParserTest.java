package com.mobiquity.util;

import com.mobiquity.domain.Pack;
import com.mobiquity.domain.PackItem;
import com.mobiquity.exception.APIException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author tatmaca
 */

class LineParserTest {

    private static final Path filePathLocal = Paths.get("src","main","test","resources","example_input");

    private static String line = "";
    private static Pack pack = null;

    @BeforeAll
    static void prepareVariables() throws APIException {
        try (InputStream inputStream = Files.newInputStream(filePathLocal)) {
            try (Scanner scanner = new Scanner(inputStream)) {
                while (scanner.hasNextLine()) {
                    line = scanner.nextLine();
                    break;
                }
            }
        } catch (IOException e) {
        }

        LineParser lineParser = new LineParser();
        pack = lineParser.validateAndParse(line);
    }

    @Test
    @Order(1)
    void testValidateAndParseLine() {
        assertEquals(8100, pack.getWeight());
        assertEquals(6, pack.getPackItems().size());
    }

    @Test
    @Order(2)
    void testGetPackItemsWithValidation() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = LineParser.class.getDeclaredMethod("getPackItemsWithValidation", String.class);
        method.setAccessible(true);
        List<PackItem> packItems = (List<PackItem>) method.invoke(new LineParser(), line.split(":")[1]);
        assertEquals(6, packItems.size());
    }

    @Test
    @Order(3)
    void testGetWeightWithValidation() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = LineParser.class.getDeclaredMethod("getWeightWithValidation", String.class);
        method.setAccessible(true);
        Double value = (Double) method.invoke(new LineParser(), "20");
        assertEquals(20, value);
    }

}