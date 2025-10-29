package com.ogs.shopping.dto.request;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class AddProductDto {

    @NotBlank(message = "Product name is required.") // @NotNull + trimmed space > 0
    // => reject null values, empty strings and strings with only white characters
    private String productName;

    @NotNull(message = "Product price is required.")
    @Min(value = 0,message = "Price must be positive.")
    private Double productPrice;

    @NotNull(message = "Product quantity is required.")
    @Min(value = 0, message = "Quantity must be positive.")
    private int productQty;

}// AddProductDto class ends
