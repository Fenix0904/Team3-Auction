package auction.controller;

import auction.domain.User;
import auction.dto.UserDTO;
import auction.service.UserService;
import auction.service.security.SecurityService;
import auction.validator.UserLoginValidator;
import auction.validator.UserRegistrationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class UserController {

    private final UserService userService;
    private final SecurityService securityService;
    private final UserRegistrationValidator registrationValidator;
    private final UserLoginValidator loginValidator;

    @Autowired
    public UserController(UserService userService, SecurityService securityService,
                          UserRegistrationValidator registrationValidator, UserLoginValidator loginValidator) {
        this.userService = userService;
        this.securityService = securityService;
        this.registrationValidator = registrationValidator;
        this.loginValidator = loginValidator;
    }

    @PostMapping(value = "/registration")
    public ResponseEntity<Void> registration(@RequestBody User user, BindingResult bindingResult) {
        registrationValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        userService.createUser(user);
        securityService.autoLogin(user.getUsername(), user.getConfirmPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping(value = "/login")
    public ResponseEntity<Void> login(@RequestBody User user, BindingResult bindingResult) {
        loginValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        securityService.autoLogin(user.getUsername(), user.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/do_logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
    }

    @GetMapping(value = "/all")
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    public List<UserDTO> getAllUsers() {
        return UserDTO.fromModel(userService.getAllUsers());
    }

    @PostMapping(value = "/ban/{userId}")
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    public void addUserToBlackList(@PathVariable int userId) {
        // TODO
    }
}
