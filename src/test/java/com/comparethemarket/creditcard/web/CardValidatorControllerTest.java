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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(CardValidatorController.class)
public class CardValidatorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CreditCardValidator validator;

    @Test
    public void validCard() throws Exception {
        // Given
        String cardNumber = "4111111111111111";
        String message = "VISA: 4111111111111111 (valid)";
        ResponseEntity<String> response = new ResponseEntity<>(message, HttpStatus.OK);
        when(validator.checkCreditCardValidity(cardNumber)).thenReturn(response);
        // Then Expect
        mvc.perform(get("/api/1.0/card/validate/" + cardNumber)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(message));
    }

    @Test
    public void inValidCard() throws Exception {
        // Given
        String cardNumber = "4111111111111112";
        String message = "VISA: 4111111111111112 (invalid)";
        ResponseEntity<String> response = new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        when(validator.checkCreditCardValidity(cardNumber)).thenReturn(response);
        // Then Expect
        mvc.perform(get("/api/1.0/card/validate/" + cardNumber)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(message));
    }

    @Test
    public void nonsenseCard() throws Exception {
        // Given
        String cardNumber = "411sdfgsgsf";
        String message = "Unknown: 411sdfgsgsf (invalid)";
        ResponseEntity<String> response = new ResponseEntity<>(message, HttpStatus.UNPROCESSABLE_ENTITY);
        when(validator.checkCreditCardValidity(cardNumber)).thenReturn(response);
        // Then Expect
        mvc.perform(get("/api/1.0/card/validate/" + cardNumber)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(message));
    }

    @Test
    public void missingCard() throws Exception {
        // Given
        String cardNumber = "";
        ResponseEntity<String> response = new ResponseEntity<>("", HttpStatus.NOT_FOUND);
        when(validator.checkCreditCardValidity(cardNumber)).thenReturn(response);
        // Then Expect
        mvc.perform(get("/api/1.0/card/validate/" + cardNumber)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}