package hu.otp.core.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserToken {

    @Id
    private String token;
    @ManyToOne
    @JoinColumn(name="userId")
    private Users users;

}
