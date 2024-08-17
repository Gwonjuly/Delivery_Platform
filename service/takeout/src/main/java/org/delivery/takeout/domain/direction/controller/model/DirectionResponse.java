package org.delivery.takeout.domain.direction.controller.model;

import lombok.Builder;
import lombok.Getter;
import org.apache.coyote.http11.upgrade.UpgradeProcessorInternal;

@Builder
@Getter
public class DirectionResponse {

    private String storeName;

    private String addressName;

    private String distance;

    private String directionUrl;

    private String roadViewUrl;
}
