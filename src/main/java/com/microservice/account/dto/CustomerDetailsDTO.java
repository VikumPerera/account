package com.microservice.account.dto;

import com.microservice.account.dto.external.CardDTO;
import com.microservice.account.dto.external.LoanDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "CustomerDetails",
        description = "Schema to hold Customer Account, Loan and Card information"
)
public class CustomerDetailsDTO {

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

    @Schema(
            description = "Card details of the Customer"
    )
    private CardDTO cardDTO;

    @Schema(
            description = "Loan details of the Customer"
    )
    private LoanDTO loanDTO;

}
