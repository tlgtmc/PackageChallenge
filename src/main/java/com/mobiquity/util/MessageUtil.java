package com.mobiquity.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * This class includes the messages that are used in tha application.
 *
 * @author tatmaca
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageUtil {

    public static final String ERROR_INVALID_LINE_CONTENT = "Invalid line, example input: %s";
    public static final String ERROR_INVALID_WEIGHT = "Invalid weight! Acceptable type is a decimal.";
    public static final String ERROR_INVALID_WEIGHT_RANGE = "Invalid weight! Weight should be a value between 0 - 100.";

    public static final String ERROR_INVALID_ITEM_ENTRY = "Invalid item entry! Items must be like '(1,53.38,€45)'.";

    public static final String ERROR_INVALID_ID_BELOW_ZERO = "Invalid id! Id should be greater than 0.";

    public static final String ERROR_INVALID_ID_SEQUENCE = "Invalid id sequence from %s to %s ! Id values should be sequential.";

    public static final String ERROR_INVALID_COST_RANGE = "Invalid cost! Cost should be a value between 0 - 100.";

}
