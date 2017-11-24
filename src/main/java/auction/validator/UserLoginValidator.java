package auction.validator;

import auction.domain.User;
import auction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserLoginValidator implements Validator {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserLoginValidator(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        User temp = userService.findByUsername(user.getUsername());
        if (temp == null) {
            errors.rejectValue("username", "User not found!");
            return;
        }
        if (!passwordEncoder.matches(user.getPassword(), temp.getPassword())){
            errors.rejectValue("confirmPassword", "Different password");
        }
    }
}
