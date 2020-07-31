package com.comparethemarket.creditcard.web;

import com.comparethemarket.creditcard.service.CreditCardValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(CardValidatorController.class)
class CardValidatorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CreditCardValidator validator;

    @Test
    public void validCard() throws Exception {
        // Given
        String cardNumber = "4111111111111111";
        ResponseEntity response = ResponseEntity.status(HttpStatus.OK).build();
        when(validator.checkCreditCardValidity(cardNumber)).thenReturn(response);
        // Then Expect
        mvc.perform(get("/api/1.0/card/validate/" + cardNumber)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void inValidCard() throws Exception {
        // Given
        String cardNumber = "4111111111111112";
        ResponseEntity response = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        when(validator.checkCreditCardValidity(cardNumber)).thenReturn(response);
        // Then Expect
        mvc.perform(get("/api/1.0/card/validate/" + cardNumber)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void nonsenseCard() throws Exception {
        // Given
        String cardNumber = "411sdfgsgsf";
        ResponseEntity response = ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        when(validator.checkCreditCardValidity(cardNumber)).thenReturn(response);
        // Then Expect
        mvc.perform(get("/api/1.0/card/validate/" + cardNumber)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void missingCard() throws Exception {
        // Given
        String cardNumber = "";
        ResponseEntity response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        when(validator.checkCreditCardValidity(cardNumber)).thenReturn(response);
        // Then Expect
        mvc.perform(get("/api/1.0/card/validate/" + cardNumber)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void incorrectEndPoint() throws Exception {
        // Given
        String cardNumber = "4111111111111111";
        ResponseEntity response = ResponseEntity.status(HttpStatus.OK).build();
        when(validator.checkCreditCardValidity(cardNumber)).thenReturn(response);
        // Then Expect
        mvc.perform(get("/api/1.0/card/valid/" + cardNumber)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}