package com.mobiquity.util;

import com.mobiquity.domain.Pack;
import com.mobiquity.domain.PackItem;
import com.mobiquity.exception.APIException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * LineParse class validates and parses the lines of the file.
 *
 * @author tatmaca
 */

public class LineParser {

    private static final String PACK_ITEM_PATTERN = "\\((?<id>\\d+)\\,(?<weight>\\d+(\\.\\d{1,2})?)\\,€(?<cost>\\d+(\\.\\d{1,2})?)\\)";

    /**
     * validateAndParse method validates the line input,
     * if it is valid then creates a Pack object by parsing the line.
     *
     * @param line input variable to be parsed
     * @return Pack object
     * @throws APIException if input line contains error
     */
    protected Pack validateAndParse(String line) throws APIException {
        var colonCount = line.chars().filter(ch -> ch == ':').count();

        if (colonCount != 1)
            throw new APIException(MessageUtil.ERROR_INVALID_LINE_CONTENT);

        var inputSplit = line.split(":");

        var weight = (int) (getWeightWithValidation(inputSplit[0].trim()) * 100);

        List<PackItem> packItems = getPackItemsWithValidation(inputSplit[1]);

        return Pack.builder().weight(weight).packItems(packItems).build();
    }

    /**
     * getWeightWithValidation checks the given weight string, if it's valid or not.
     * If valid, then returns a double of it.
     *
     * @param weight to be validated and converted
     * @return double
     * @throws APIException if weight is not valid
     */
    private double getWeightWithValidation(String weight) throws APIException {
        try {
            return Double.parseDouble(weight);
        } catch (NumberFormatException e) {
            throw new APIException(MessageUtil.ERROR_INVALID_WEIGHT);
        }
    }

    /**
     * getPackItemsWithValidation process inputItems
     * First, it creates an array and validates and process each item in this array.
     *
     * @param inputItems items that will be parsed and validated
     * @return Unmodifiable List of PackItem(s)
     * @throws APIException in case of an invalid input
     */
    private List<PackItem> getPackItemsWithValidation(String inputItems) throws APIException {

        if (inputItems.charAt(0) == ' ') {
            inputItems = inputItems.substring(1);
        }

        String[] itemsArr = inputItems.split(" ");

        Pattern pattern = Pattern.compile(PACK_ITEM_PATTERN);

        int prevId = 0;

        List<PackItem> packItems = new ArrayList<>();

        for (String item: itemsArr) {
            packItems.add(validateAndGetPackItem(prevId, pattern, item));
            prevId++;
        }

        return Collections.unmodifiableList(packItems);
    }

    /**
     * validateAndGetPackItem validates and prepares PackItem object.
     * Validation is done by the help of regex pattern.
     * PackItem object is created as last step.
     *
     * @param prevId previous pack item's id, 0 if first item.
     * @param pattern regex pattern to be used for validation
     * @param item item is beign processed
     * @return PackItem
     * @throws APIException if the input is invalid
     */
    private PackItem validateAndGetPackItem(int prevId, Pattern pattern, String item) throws APIException {
        Matcher matcher = pattern.matcher(item);

        if (matcher.matches()) {

            int id = isValidId(prevId, Integer.parseInt(matcher.group("id")));
            int itemWeight = isValidWeight(Double.parseDouble(matcher.group("weight")));
            double cost = isValidCost(Double.parseDouble(matcher.group("cost")));

            return  PackItem.builder().id(id).weight(itemWeight).cost(cost).build();
        } else {
            throw new APIException(MessageUtil.ERROR_INVALID_ITEM_ENTRY);
        }
    }

    /**
     * isValidId checks
     * 1 - If the id is less than 0
     * 2 - If id is increased in sequentially
     *
     * @param prevId id of previous item
     * @param currentId id of current item
     * @return int as current id
     * @throws APIException if the conditions are not satisfied
     */
    private int isValidId(int prevId, int currentId) throws APIException {
        if (currentId < 0 ) {
            throw new APIException(MessageUtil.ERROR_INVALID_ID_BELOW_ZERO);
        } else if (prevId + 1 != currentId) {
            throw new APIException(String.format(MessageUtil.ERROR_INVALID_ID_SEQUENCE, prevId, currentId));
        }

        return currentId;
    }

    /**
     * isValidWeight checks if weight is less than 0 or greater than 100 which are stated in the document.
     *
     * @param weight value to be processed
     * @return Int of given weight value
     * @throws APIException If the conditions are not satisfied
     */
    private int isValidWeight(double weight) throws APIException {
        if (weight < 0 || weight > 100) {
            throw new APIException(MessageUtil.ERROR_INVALID_WEIGHT_RANGE);
        }
        return (int) (weight * 100);
    }

    /**
     * isValidCost checks if cost is less than 0 or greater than 100 which are stated in the document.
     *
     * @param cost value to be processed
     * @return double of cost value
     * @throws APIException If the conditions are not satisfied
     */
    private double isValidCost(double cost) throws APIException {
        if (cost < 0 || cost > 100) {
            throw new APIException(MessageUtil.ERROR_INVALID_COST_RANGE);
        }
        return cost;
    }
}
