package auction.service;


import auction.domain.Role;
import auction.domain.User;
import auction.repository.RoleRepository;
import auction.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    private final static int userRoleId = 1;

    @Override
    public void createUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        Role role = roleRepository.getOne(userRoleId);
        user.setRole(role);
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
