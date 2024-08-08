package org.delivery.takeout.domain.store.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class StoreDto {

    private Long id;

    private String address;

    private String name;

    private double latitude;

    private double longitude;
}
