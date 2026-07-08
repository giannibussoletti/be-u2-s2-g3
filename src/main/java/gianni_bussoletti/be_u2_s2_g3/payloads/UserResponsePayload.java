package gianni_bussoletti.be_u2_s2_g3.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter // I GETTER SONO OBLIGATORI
//Per fare la conversione da Java a JSON c'è bisogno dei getter
public class UserResponsePayload {
    private UUID userId;
}
