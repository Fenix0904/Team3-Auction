package auction.service;


import auction.domain.Role;
import auction.domain.User;
import auction.repository.RoleRepository;
import auction.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    @Override
    public void createUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        Role role = roleRepository.getOne(1);
        user.setRole(role);
        userRepository.save(user);
        log.info("createUser method executed");
    }

    @Override
    public User findByUsername(String username) {
        log.info("findByUserName method executed");
        return userRepository.findByUsername(username);
    }

    @Override
    public User findById(int id) {
        log.info("findById method executed");
        return userRepository.findById(id);
    }

    @Override
    public List<User> getAllUsers() {
        log.info("getAllUsers method executed");
        return userRepository.findAll();
    }
}
