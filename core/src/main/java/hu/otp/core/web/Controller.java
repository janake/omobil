package hu.otp.core.web;

import hu.otp.core.dao.UserBankCard;
import hu.otp.core.dao.Users;
import hu.otp.core.model.UserDetails;
import hu.otp.core.service.BankCardService;
import hu.otp.core.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@Log4j2
@RestController
public class Controller {

    @Autowired
    private UserService userService;

    @Autowired
    private BankCardService bankCardService;

    @PostMapping("/getuserdetails")
    public UserDetails getUserDetails(@RequestBody Map<String, String> token) {
        log.info("getuserdetail request");
        return userService.getUserDetailsByToken(token.get("token"));
    }

    @PostMapping("/getuserid")
    public Users getUserName(@RequestBody Map<String, String> token) {
        log.info("getuserid request");
        return userService.getUserId(token.get("token"));
    }

    @GetMapping("/getbankcard/{id}")
    public UserBankCard getUserBankCard(@PathVariable("id") String cardId) {
        log.info("getbankcard request");
        return bankCardService.getBankCard(cardId);
    }

    @PostMapping("/istokenvalid")
    public Boolean isTokenValid(@RequestBody Map<String, String> token) {
        log.info("istokenvalid request");
        return userService.isTokenValid(token.get("token"));
    }

}
