package gianni_bussoletti.be_u2_s2_g3.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class UserPayload {
    private String name;
    private String surname;
    private String mail;
    private String password;
    private LocalDate birthDate;


}
