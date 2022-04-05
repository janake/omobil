package hu.otp.core.configuration;

import hu.otp.core.dao.*;
import hu.otp.core.repository.UserBankCardRepository;
import hu.otp.core.repository.UserDeviceRepository;
import hu.otp.core.repository.UserTokenRepository;
import hu.otp.core.repository.UsersRepository;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitDB {

    public InitDB(UsersRepository usersRepository, UserBankCardRepository userBankCardRepository,
                  UserTokenRepository userTokenRepository, UserDeviceRepository userDeviceRepository){
        Users users1 = new Users("1000", "Teszt Aladár", "teszt.aladar@otpmobil.com");
        usersRepository.save(users1);
        Users users2 = new Users("2000", "Teszt Benedek", "teszt.benedek@otpmobil.com");
        usersRepository.save(users2);
        Users users3 = new Users("3000", "Teszt Cecília", "teszt.cecilia@otpmobil.com");
        usersRepository.save(users3);

        UserToken userToken1 = new UserToken("dGVzenQuYWxhZGFyQG90cG1vYmlsLmNvbSYxMDAwJkY2N0MyQkNCRkNGQTMwR kNDQjM2RjcyRENBMjJBODE3", users1);
        userTokenRepository.save(userToken1);
        UserToken userToken2 = new UserToken("dGVzenQuYmVuZWRla0BvdHBtb2JpbC5jb20mMjAwMCZGQURERkVBNTYyRjNDOTE 0RENDODE5NTY2ODJEQjBGQw==",  users2);
        userTokenRepository.save(userToken2);
        UserToken userToken3 = new UserToken("dGVzenQuY2VjaWxpYUBvdHBtb2JpbC5jb20mMzAwMCZFNjg1NjA4NzJCREIyREYyRk ZFN0FEQzA5MTc1NTM3OA==", users3);
        userTokenRepository.save(userToken3);
        UserToken userToken4 = new UserToken("dGVzenQuYWxhZGFyQG90cG1vYmlsLmNvbSYxMDAwJjBGMTY3NEJEMTlEM0JCREQ 0QzM5RTE0NzM0RkZCODc2", users1);
        userTokenRepository.save(userToken4);
        UserToken userToken5 = new UserToken("dGVzenQuYWxhZGFyQG90cG1vYmlsLmNvbSYxMDAwJjNBRTVFOTY1OEZCRDdEND A0OEJENDA4MjBCN0QyMjdE", users1);
        userTokenRepository.save(userToken5);

        UserBankCard userBankCard1 = new UserBankCard("5299706965433676",
                "C0001", users1, "123", "Teszt Aladár", 1000L, "HUF");
        UserBankCard userBankCard2 = new UserBankCard("5390508354245119",
                "C0002", users2, "456", "Teszt Benedek", 2000L, "HUF");
        UserBankCard userBankCard3 = new UserBankCard("4929088924014470",
                "C0003", users3, "789", "Teszt Cecília", 3000L, "HUF");
        userBankCardRepository.save(userBankCard1);
        userBankCardRepository.save(userBankCard2);
        userBankCardRepository.save(userBankCard3);

        UserDevice userDevice1 = new UserDevice(new UserDeviceId(users1.getUserId(), "F67C2BCBFCFA30FCCB36F72DCA22A817"));
        UserDevice userDevice2 = new UserDevice(new UserDeviceId(users1.getUserId(), "0F1674BD19D3BBDD4C39E14734FFB876"));
        UserDevice userDevice3 = new UserDevice(new UserDeviceId(users1.getUserId(), "3AE5E9658FBD7D4048BD40820B7D227D"));
        UserDevice userDevice4 = new UserDevice(new UserDeviceId(users2.getUserId(), "FADDFEA562F3C914DCC81956682DB0FC"));
        UserDevice userDevice5 = new UserDevice(new UserDeviceId(users3.getUserId(), "E68560872BDB2DF2FFE7ADC091755378"));
        userDeviceRepository.save(userDevice1);
        userDeviceRepository.save(userDevice2);
        userDeviceRepository.save(userDevice3);
        userDeviceRepository.save(userDevice4);
        userDeviceRepository.save(userDevice5);

    };

}
