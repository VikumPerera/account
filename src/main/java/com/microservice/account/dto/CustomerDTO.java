package com.microservice.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Schema(
        name = "Customer",
        description = "Schema to hold Customer and Account information"
)
public class CustomerDTO {

    @Schema(
            description = "Name of the customer", example = "Eazy Bytes"
    )
    @NotBlank(message = "Name cannot be a null or empty")
    @Size(message = "Name must be between 5 and 30 characters", min = 5, max = 30)
    private String name;

    @Schema(
            description = "Email address of the customer", example = "tutor@eazybytes.com"
    )
    @NotBlank(message = "Email cannot be a null or empty")
    @Email(message = "Email is not valid")
    private String email;

    @Schema(
            description = "Mobile Number of the customer", example = "9345432123"
    )
    @Pattern(regexp = "(^%|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @Schema(
            description = "Account details of the Customer"
    )
    private AccountDTO accountDTO;

}
