package com.axians.eshop.dto.request.product;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UpdateStockRequest {

    @Min(0)
    private Integer stockQuantity;
}