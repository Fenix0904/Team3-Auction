package repositoryTest;

import auction.Application;
import auction.domain.Role;
import auction.repository.RoleRepository;
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
public class RoleRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    RoleRepository repository;

    @Test
    public void should_find_roles_if_repository_is_not_empty() {
        entityManager.persist(new Role());
        entityManager.flush();
        List<Role> roles = repository.findAll();
        assertThat(roles).isNotEmpty();
    }

    @Test
    public void should_delete_all_roles() {
        entityManager.persist(new Role());
        entityManager.persist(new Role());
        entityManager.flush();
        repository.deleteAll();
        assertThat(repository.findAll()).isEmpty();
    }

    @Test
    public void should_find_all_roles() {
        Role roleOne = new Role();
        entityManager.persist(roleOne);

        Role roleTwo = new Role();
        entityManager.persist(roleTwo);

        Role roleThree = new Role();
        entityManager.persist(roleThree);

        List<Role> roles = repository.findAll();
        assertThat(roles).hasSize(3).contains(roleOne, roleTwo, roleThree);
    }

    @Test
    public void should_find_role_by_id() {
        Role roleOne = new Role();
        entityManager.persist(roleOne);

        Role roleTwo = new Role();
        entityManager.persist(roleTwo);

        Role foundRole = repository.findOne(roleTwo.getId());
        assertThat(foundRole).isEqualTo(roleTwo);
    }
}
