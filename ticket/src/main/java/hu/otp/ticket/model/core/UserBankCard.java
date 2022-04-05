package hu.otp.ticket.model.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBankCard {

    private String cardNumber;
    private String cardId;
    private Users users;
    private String cvc;
    private String name;
    private Long amount;
    private String currency;

}
