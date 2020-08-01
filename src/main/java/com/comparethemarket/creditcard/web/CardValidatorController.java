package com.comparethemarket.creditcard.web;

import com.comparethemarket.creditcard.service.CreditCardValidator;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${credit-card.api-version}")
@Api(description = "Endpoint for performing Credit Card Validations.")
public class CardValidatorController {

    @Autowired
    private CreditCardValidator validator;

    @GetMapping("/card/validate/{cardNumber}")
    @ApiOperation("No text is returned by this service, only the response code is provided.")
    @ApiParam("cardNumber is validated, given a set number of supported card types.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The Credit Card is valid."),
            @ApiResponse(code = 400, message = "The Credit Card is invalid."),
            @ApiResponse(code = 422, message = "Either the Credit Card is nonsense, or it does not conform to the set of supported types.")})
    public ResponseEntity<String> checkCreditCardValidity(@PathVariable String cardNumber) {
        return validator.checkCreditCardValidity(cardNumber);
    }
}
