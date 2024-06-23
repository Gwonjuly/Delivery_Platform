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
    /**
     * NotBlank: "", " ", null (x) =빈 문자, blank 문자, null 안됨
     * NotNull: null 값이 있으면 안됨
     * * enum은 문자가 아니기에 NotBlank가 아닌 NotNull
     */
    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @NotNull
    private StoreCategory storeCategory;

    @NotBlank
    private String thumbnailUrl;

    @NotNull
    private BigDecimal minimumAmount;
    @NotNull
    private BigDecimal minimumDeliveryAmount;

    @NotBlank
    private String phoneNumber;
}
