package com.mobiquity.domain;

import lombok.*;

import java.util.List;

/**
 * @author tatmaca
 *
 * Pack model class with two variables.
 * weight stores the weight of the pack.
 * packItems stores the pack items of the pack.
 *
 */

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Pack {
    private final int weight;
    private final List<PackItem> packItems;
}