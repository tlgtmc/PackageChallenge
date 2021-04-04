package com.mobiquity;

import com.mobiquity.exception.APIException;
import com.mobiquity.util.MessageUtil;
import com.mobiquity.validator.impl.InputValidatorImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author tatmaca
 */

public class ValidatorTest {

    @Test
    public void testWhenInputArgsCountZero_thenThrowApiException() {

        Throwable exception = assertThrows(APIException.class, () -> {
            InputValidatorImpl.validator.isValidArgs(new String[]{});
        });

        assertEquals(MessageUtil.ERROR_NO_ARG, exception.getMessage());
    }

    @Test
    public void testWhenTooManyInputArgs_thenThrowApiException() {

        Throwable exception = assertThrows(APIException.class, () -> {
            InputValidatorImpl.validator.isValidArgs(new String[]{"~/home/file.txt", "~/home/file2.txt"});
        });

        assertEquals(MessageUtil.ERROR_TOO_MANY_ARGS, exception.getMessage());
    }

    @Test
    public void testWhenArgsIsCorrect_thenValidationPassed() {
        try {
            boolean validArgs = InputValidatorImpl.validator.isValidArgs(new String[]{"~/home/file.txt"});
            assertTrue(validArgs);
        } catch (APIException e) {
            e.printStackTrace();
        }
    }
}
