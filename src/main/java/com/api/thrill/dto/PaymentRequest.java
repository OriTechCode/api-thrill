package com.api.thrill.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class PaymentRequest {
    private Long ordenId;
    private BigDecimal amount;
    private String description;
}
