package com.mobiquity.domain;

import lombok.*;

/**
 * @author tatmaca
 *
 * PackItem stands for pack items.
 * It has 3 parameters.
 * Id is the id of the item,
 * weight is the weight of the item,
 * cost is the cost of the item.
 *
 * @Value, @Builder and @AllArgsConstructor(access = AccessLevel.PRIVATE) tags
 * are used to make this class immutable.
 *
 */

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PackItem {
    private final int id;
    private final int weight;
    private final double cost;
}
