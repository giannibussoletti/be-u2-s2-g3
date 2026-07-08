package gianni_bussoletti.be_u2_s2_g3.services;

import gianni_bussoletti.be_u2_s2_g3.entities.User;
import gianni_bussoletti.be_u2_s2_g3.exceptions.BadRequestException;
import gianni_bussoletti.be_u2_s2_g3.exceptions.NotFoundException;
import gianni_bussoletti.be_u2_s2_g3.payloads.PasswordUpdatePayload;
import gianni_bussoletti.be_u2_s2_g3.payloads.UserPayload;
import gianni_bussoletti.be_u2_s2_g3.payloads.UserUpdatePayload;
import gianni_bussoletti.be_u2_s2_g3.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(UserPayload userPayload) {
//        1. Verifichiamo che l'email sia unica
        if (this.userRepository.existsByMail(userPayload.getMail()))
            throw new BadRequestException("L'email " + userPayload.getMail() + " esiste già");
//        2. TODO: Ulteriori controlli
//        3. Creo il nuovo oggetto User leggendo i valori dal payload
        User newUser = new User(userPayload.getName(), userPayload.getSurname(), userPayload.getPassword(), userPayload.getMail(), userPayload.getBirthDate());
//        4. Salviamo il nuovo utente
        return this.userRepository.save(newUser);
    }

    //SE invece ci List usiamo Page, possiamo creare una pagination
    public Page<User> getAll(int page, int size, String orderBy) {
        // Andando a creare un oggetto di tipo Pageable andiamo a comunicare con il client di voler creare una pagination
//        L'oggetto pageable contiene diverse informazioni, per esempio il numero di pagine totali, utili al front-end
//        la prima prorietà è il numero di pagine, il secondo è il numero massimo di risultati, e la terza è il sorting
//        Tutti e tre possono essere parametrici
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return this.userRepository.findAll(pageable);
    }

    public User findById(UUID userId) {
        return this.userRepository.findById(userId).orElseThrow(() -> new NotFoundException("L'utente non è stato trovato"));
    }

    public User findByIdAndUpdate(UUID userId, UserUpdatePayload body) {
        User found = this.findById(userId);
        if (!found.getMail().equals(body.getMail()))
            if (this.userRepository.existsByMail(body.getMail()))
                throw new BadRequestException("L'indirizzo " + body.getMail() + " è già utilizzato");

        found.setName(body.getName());
        found.setSurname(body.getSurname());
        found.setMail(body.getMail());
        found.setBirthDate(body.getBirthDate());

        return this.userRepository.save(found);
    }

    public void findyByIdAndDelete(UUID userId) {
        User found = this.findById(userId);
        this.userRepository.delete(found);
    }

    public void updatePassword(UUID userId, PasswordUpdatePayload payload) {
        User found = this.findById(userId);

        if (!found.getPassword().equals(payload.getOldPassword()))
            throw new BadRequestException("Le password non corrispondono!");

        found.setPassword(payload.getNewPassword());

        this.userRepository.save(found);
    }


}
