package com.api.thrill.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    private String status;
    private String initPoint;
    private String errorMessage;
    private String preferenceId;
    private Long ordenId;
}
