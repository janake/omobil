package hu.otp.core.service;

import hu.otp.core.dao.UserBankCard;
import hu.otp.core.exception.RestException;
import hu.otp.core.repository.UserBankCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BankCardService {

    @Autowired
    private UserBankCardRepository userBankCardRepository;

    public UserBankCard getBankCard(String cardId) {
        UserBankCard card;
        Optional<UserBankCard> cardFromDb = userBankCardRepository.findById(cardId);
        if (cardFromDb.isPresent()){
            card = cardFromDb.get();
        } else {
            throw new RestException(
                    "A beérkezett kérésben szereplő bankkártya nem az adott felhasználóhoz tartozik",
                    "Ez a bankkártya nem ehhez a felhasználóhoz tartozik",
                    "10100"
            );
        }
        return card;
    }
}
