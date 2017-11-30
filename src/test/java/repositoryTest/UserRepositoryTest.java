package repositoryTest;

import auction.Application;
import auction.domain.User;
import auction.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    UserRepository repository;

    @Test
    public void should_find_users_if_repository_is_not_empty() {
        entityManager.persist(new User());
        entityManager.flush();
        List<User> users = repository.findAll();
        assertThat(users).isNotEmpty();
    }

    @Test
    public void should_delete_all_users() {
        entityManager.persist(new User());
        entityManager.persist(new User());
        entityManager.flush();
        repository.deleteAll();
        assertThat(repository.findAll()).isEmpty();
    }

    @Test
    public void should_find_all_users() {
        User userOne = new User();
        entityManager.persist(userOne);

        User userTwo = new User();
        entityManager.persist(userTwo);

        User userThree = new User();
        entityManager.persist(userThree);

        List<User> users = repository.findAll();
        assertThat(users).hasSize(3).contains(userOne, userTwo, userThree);
    }

    @Test
    public void should_find_user_by_id() {
        User userOne = new User();
        entityManager.persist(userOne);

        User userTwo = new User();
        entityManager.persist(userTwo);

        User foundUser = repository.findOne(userTwo.getId());
        assertThat(foundUser).isEqualTo(userTwo);
    }
}
