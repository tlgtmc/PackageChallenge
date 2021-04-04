package com.mobiquity;

import com.mobiquity.exception.APIException;
import com.mobiquity.packer.Packer;
import com.mobiquity.validator.impl.InputValidatorImpl;

import java.util.logging.Logger;

/**
 * Main method of the application. Processes the input arguments, logs the result to the console.
 *
 * @author tatmaca
 */

public class PackerApplication {

    private static final Logger LOGGER = Logger.getLogger(PackerApplication.class.getName());

    public static void main(String[] args) throws APIException {

        InputValidatorImpl.validator.isValidArgs(args);
        String result = Packer.pack(args[0]);
        LOGGER.info(result);
    }
}
