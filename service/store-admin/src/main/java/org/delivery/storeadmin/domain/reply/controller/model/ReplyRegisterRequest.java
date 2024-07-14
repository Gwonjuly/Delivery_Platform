package org.delivery.storeadmin.domain.reply.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplyRegisterRequest {
    private Long storeId;
    private Long reviewId;
    private String replyText;
}
