package org.delivery.storeadmin.domain.store.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.delivery.db.store.enums.StoreCategory;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreRegisterRequest {

    @NotBlank
    private String name;

    @NotNull
    private StoreCategory storeCategory;

    @NotNull
    private BigDecimal minimumAmount;
    @NotNull
    private BigDecimal minimumDeliveryAmount;
}
