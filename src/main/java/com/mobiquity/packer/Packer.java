package com.mobiquity.packer;

import com.mobiquity.domain.Pack;
import com.mobiquity.domain.PackItem;
import com.mobiquity.exception.APIException;
import com.mobiquity.util.FileContentParser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Packer class is responsible from the packing.
 * Public pack method is the entry point with a filePath variable.
 * It analyzes the pack items and find suitable ones.
 */
public class Packer {

    private Packer() {
    }

    /**
     * Entry point of the Packer class. It requires a filePath argument.
     * By the help of FileContentParser class, a list of parsed packs will be created.
     * Then, each pack will be processed individually.
     *
     * @param filePath: Path to the input file
     * @return a formatted string response.
     * @throws APIException in inappropriate conditions
     */
    public static String pack(String filePath) throws APIException {

        FileContentParser fileContentParser = new FileContentParser();
        List<Pack> packs = fileContentParser.parse(filePath);

        StringBuilder sb = new StringBuilder();

        for (Pack pack: packs) {
            if (sb.toString().length() > 0)
               sb.append("\n");

            sb.append(processPack(pack));
        }

        return sb.toString();
    }

    /**
     * This method process the given pack.
     * First defines the packWeight and packItemSize fields,
     * afterwards, prepareWeightCostArray method is called in order to get the calculated array.
     *
     * Once array is created, then findPackedItems method is triggered to get the items that are packed.
     *
     * Lastly, selectedPacks' ids' are sorted and joined with stream API. If there is no result, "-" character returns.
     *
     * @param pack Parameter that will be processed
     * @return String of selected packs
     */
    private static String processPack(Pack pack)
    {
        int packWeight = pack.getWeight() + 1;
        int packItemSize = pack.getPackItems().size() + 1 ;

        double[][] arr = prepareWeightCostArray(pack, packWeight, packItemSize);

        List<PackItem> selectedPacks = findPackedItems(pack, packWeight, packItemSize, arr);

        String result = selectedPacks.stream()
                .mapToInt(PackItem::getId)
                .sorted()
                .mapToObj(Integer::toString)
                .collect(Collectors.joining(","));
        return result.length() > 0 ? result : "-";
    }

    /**
     * prepareWeightCostArray is an implementation of 0-1 Knapsack problem solution.
     *
     * It creates a two dimensional array by packItemCount and packWeight.
     * And it will be filled with the maximum weight that is suitable for the current position.
     * Maximum weight is determined with the position of weight index, and current item that is processing.
     *
     * @param pack Pack that will be processed
     * @param packWeight Weight of the pack
     * @param packItemSize Items to be considered
     * @return a double array in double type
     */
    private static double[][] prepareWeightCostArray(Pack pack, int packWeight, int packItemSize) {
        double[][] arr = new double[packItemSize][packWeight];

        for (int index = 1; index < packItemSize; index++) {
            PackItem packItem = pack.getPackItems().get(index-1);

            for (int tempWeight = 1; tempWeight < packWeight; tempWeight++) {
                if (packItem.getWeight() <= tempWeight)
                    arr[index][tempWeight] = Math.max(packItem.getCost() + arr[index - 1][tempWeight - packItem.getWeight()], arr[index - 1][tempWeight]);
                else
                    arr[index][tempWeight] = arr[index - 1][tempWeight];
            }
        }
        return arr;
    }

    /**
     * findPackedItems stands for finding items that are selected.
     *
     * First it determines the weight and cost.
     * Than, moves index through the first occurrence in order to get the selected items easily.
     * And starts processing from the end of the array, if weights differentiates, than it puts item into list.
     *
     * @param pack pack that will be processed
     * @param packWeight weight of the pack
     * @param packItemSize item count of the pack
     * @param arr prepared 2D array for pack
     * @return List of selected PackItem(s)
     */
    private static List<PackItem> findPackedItems(Pack pack, int packWeight, int packItemSize, double[][] arr) {

        List<PackItem> selectedPacks = new ArrayList<>();
        int tempWeight = packWeight;
        double totalCost = arr[packItemSize - 1][packWeight - 1];

        for (; tempWeight > 0 && arr[packItemSize - 1][tempWeight - 1] == totalCost; tempWeight--);

        for (int i = packItemSize - 1; i > 0; i--) {
            if (arr[i][tempWeight] != arr[i - 1][tempWeight]) {
                selectedPacks.add(pack.getPackItems().get(i - 1));
                tempWeight -= pack.getPackItems().get(i - 1).getWeight();
            }
        }
        return selectedPacks;
    }
}
