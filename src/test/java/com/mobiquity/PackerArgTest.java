package com.mobiquity;

import com.mobiquity.exception.APIException;
import com.mobiquity.util.MessageUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author tatmaca
 */
public class PackerArgTest {

    @Test
    public void testWhenInputArgsCountZero_thenThrowApiException() {

        Throwable exception = assertThrows(APIException.class, () -> {
            PackerApplication.main(new String[]{});
        });

        assertEquals(MessageUtil.ERROR_NO_ARG, exception.getMessage());
    }

    @Test
    public void testWhenTooManyInputArgs_thenThrowApiException() {

        Throwable exception = assertThrows(APIException.class, () -> {
            PackerApplication.main(new String[]{"~/home/file.txt", "~/home/file2.txt"});
        });

        assertEquals(MessageUtil.ERROR_TOO_MANY_ARGS, exception.getMessage());
    }

    @Test
    public void testWhenInputArgIsCorrect_thenProcess() throws APIException {
        String filePath = "/Users/tatmaca/Development/inputSample.txt";

        PackerApplication.main(new String[]{filePath});

    }
}