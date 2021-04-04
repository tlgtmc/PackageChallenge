package com.mobiquity.validator.impl;

import com.mobiquity.exception.APIException;
import com.mobiquity.util.MessageUtil;
import com.mobiquity.validator.InputValidator;

/**
 * InputValidatorImpl is the implementation class of InputValidator
 *
 * It is a singleton class
 *
 * @author tatmaca
 */

public class InputValidatorImpl implements InputValidator {

    public static final InputValidatorImpl validator;

    static {
        validator = new InputValidatorImpl();
    }

    private InputValidatorImpl() {
    }

    /**
     * isValidArgs validates the execution arguments of the application.
     *
     * @param args values that will be validated
     * @return boolean status of the validation
     * @throws APIException in case of an invalid input
     */
    @Override
    public boolean isValidArgs(String[] args) throws APIException {
        if (args.length == 0) {
            throw new APIException(MessageUtil.ERROR_NO_ARG);
        } else if (args.length > 1) {
            throw new APIException(MessageUtil.ERROR_TOO_MANY_ARGS);
        }
        return true;
    }
}
