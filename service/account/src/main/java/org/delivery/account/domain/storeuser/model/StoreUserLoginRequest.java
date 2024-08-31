package org.delivery.account.domain.storeuser.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreUserLoginRequest {

    @NotNull
    private String email;

    @NotNull
    private String password;
}
