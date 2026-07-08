package gianni_bussoletti.be_u2_s2_g3.controllers;

import gianni_bussoletti.be_u2_s2_g3.entities.User;
import gianni_bussoletti.be_u2_s2_g3.payloads.PasswordUpdatePayload;
import gianni_bussoletti.be_u2_s2_g3.payloads.UserPayload;
import gianni_bussoletti.be_u2_s2_g3.payloads.UserResponsePayload;
import gianni_bussoletti.be_u2_s2_g3.payloads.UserUpdatePayload;
import gianni_bussoletti.be_u2_s2_g3.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //    1. POST http://localhost:PORT/users (+req.body) --> 201
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponsePayload saveUSer(@RequestBody UserPayload body) {
        User saved = this.userService.save(body);
        return new UserResponsePayload(saved.getId());
    }

    @GetMapping
    public Page<User> getUsers(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "5") int size,
                               @RequestParam(defaultValue = "name") String orderBy) {
        return this.userService.getAll(page, size, orderBy);
    }

    @GetMapping("/{userId}")
    public User getById(@PathVariable UUID userId) {
        return this.userService.findById(userId);
    }

    @PutMapping("/{userId}")
    public User getByIdAndUpdate(@PathVariable UUID userId, @RequestBody UserUpdatePayload body) {
        return this.userService.findByIdAndUpdate(userId, body);
    }

    @DeleteMapping()
    public void findByIdAndDelete(UUID userId) {
        this.userService.findyByIdAndDelete(userId);
    }

    @PatchMapping("/{userId}/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePassword(@PathVariable UUID userId, @RequestBody PasswordUpdatePayload body) {
        this.userService.updatePassword(userId, body);

    }

}
