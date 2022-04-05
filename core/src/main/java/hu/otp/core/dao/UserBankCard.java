package hu.otp.core.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserBankCard {

    @Id
    private String cardNumber;
    private String cardId;
    @ManyToOne
    @JoinColumn(name="userId")
    private Users users;
    private String cvc;
    private String name;
    private Long amount;
    private String currency;

}
