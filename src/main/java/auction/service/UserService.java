package auction.service;

import auction.domain.User;
import java.util.List;

public interface UserService {

    void createUser(User user);

    User findByUsername(String username);

    User findById(int id);

    List<User> getAllUsers();
}
