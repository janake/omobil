package hu.otp.core.service;

import hu.otp.core.dao.UserBankCard;
import hu.otp.core.dao.UserDevice;
import hu.otp.core.dao.UserToken;
import hu.otp.core.dao.Users;
import hu.otp.core.exception.RestException;
import hu.otp.core.model.UserDetails;
import hu.otp.core.repository.UserBankCardRepository;
import hu.otp.core.repository.UserDeviceRepository;
import hu.otp.core.repository.UserTokenRepository;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserTokenRepository userTokenRepository;

    @Autowired
    private UserBankCardRepository userBankCardRepository;

    @Autowired
    private UserDeviceRepository userDeviceRepository;

    public UserDetails getUserDetailsByToken(String originalToken) {
        UserDetails userDetails = new UserDetails();
        String[] userData = new String(Base64.decodeBase64(originalToken.getBytes())).split("&");
        validateUserData(userData);
        Optional<UserToken> userToken = getUserTokenFromDB(originalToken);
        validateTokenIsExist(userToken);
        Users userFromDB = userToken.get().getUsers();
        validateUserFromDB(userFromDB);
        UserBankCard userBankCard = userBankCardRepository.findByUserId(userFromDB);
        List<UserDevice> userDevices = userDeviceRepository.getUserDevicesByUserId(userFromDB.getUserId());
        String tokenFromDB = userToken.get().getToken();
        validateTokenFromDB(tokenFromDB);
        setResponse(userDetails, userFromDB, userBankCard, userDevices, tokenFromDB);
        return userDetails;
    }

    private void setResponse(UserDetails userDetails, Users userFromDB, UserBankCard userBankCard, List<UserDevice> userDevices, String tokenFromDB) {
        userDetails.setToken(tokenFromDB);
        userDetails.setUsers(userFromDB);
        userDetails.setUserBankCard(userBankCard);
        userDetails.setUserDevices(userDevices);
    }

    public Users getUserId(String originalToken) {
        Optional<UserToken> userToken = getUserTokenFromDB(originalToken);
        String[] userData = new String(Base64.decodeBase64(originalToken.getBytes())).split("&");
        validateUserData(userData);
        Users userFromDB = checkUserFromDB(userToken);
        return userFromDB;
    }

    public Boolean isTokenValid(String token) {
        if (!getUserTokenFromDB(token).isPresent()) {
            throwTokenException();
        };
        return true;
    }

    private Optional<UserToken> getUserTokenFromDB(String originalToken) {
        return userTokenRepository.findById(originalToken);
    }

    private Users checkUserFromDB(Optional<UserToken> userToken) {
        Users userFromDB;
        if (userToken.isPresent()) {
            userFromDB = userToken.get().getUsers();
            validateUserFromDB(userFromDB);
        } else {
            throw new RestException(
                "A beérkezett kérésben a felhasználó token nem szerepel",
                "A felhasználói token nem szerepel",
                "10050");
        }
        return userFromDB;
    }

    private void validateUserData(String[] userData) {
        if(userData.length != 3) {
            throwTokenException();
        }
    }

    private void throwTokenException() {
        throw new RestException(
            "A beérkezett kérésben szereplő felhasználó token lejárt vagy nem értelmezhető",
            "A felhasználói token lejárt vagy nem értelmezhető",
            "10051"
        );
    }

    private void validateUserFromDB(Users userFromDB) {
        if (userFromDB == null || "".equals(userFromDB)) {
            throw new RestException(
                "A beérkezett kérésben a felhasználó token nem szerepel",
                "A felhasználói token nem szerepel",
                "10050");
        }
    }

    private void validateTokenFromDB(String tokenFromDB) {
        if (tokenFromDB == null || "".equals(tokenFromDB)) {
            throwTokenException();
        }
    }

    private void validateTokenIsExist(Optional<UserToken> userToken) {
        if (!userToken.isPresent()) {
            throw new RestException(
                    "A beérkezett kérésben a felhasználó token nem szerepel",
                    "A felhasználói token nem szerepel",
                    "10050");
        }
    }

}
