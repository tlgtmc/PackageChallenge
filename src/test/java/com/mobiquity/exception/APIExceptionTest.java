package com.mobiquity.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author tatmaca
 */

class APIExceptionTest {

    private static final String message = "Exception Test Message";
    @Test
    void testApiExcetionMessage() {
        APIException exception = new APIException(message);
        assertEquals(message, exception.getMessage());
    }

    @Test
    void testApiExcetionConstructor() {
        APIException exception = new APIException(message, new Exception());
        assertEquals(message, exception.getMessage());
    }

}