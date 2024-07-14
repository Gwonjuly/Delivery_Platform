package org.delivery.db.review.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ReviewStatus {

    REGISTERED("등록"),
    UNREGISTERED("해제"),;

    private String description;
}
