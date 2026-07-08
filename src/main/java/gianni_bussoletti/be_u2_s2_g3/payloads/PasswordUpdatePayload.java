package gianni_bussoletti.be_u2_s2_g3.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PasswordUpdatePayload {
    private String oldPassword;
    private String newPassword;
}
