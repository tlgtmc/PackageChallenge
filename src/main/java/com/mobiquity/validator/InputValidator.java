package com.mobiquity.validator;

import com.mobiquity.exception.APIException;

/**
 * InputValidator interface, stands for input argument validation.
 *
 * @author tatmaca
 */

public interface InputValidator {

    boolean isValidArgs(String[] args) throws APIException;
}
