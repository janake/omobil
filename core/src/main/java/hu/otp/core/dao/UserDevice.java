package hu.otp.core.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserDevice implements Serializable {

    @EmbeddedId
    private UserDeviceId userDeviceId;

}
