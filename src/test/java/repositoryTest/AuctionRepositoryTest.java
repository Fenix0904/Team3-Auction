package repositoryTest;

import static org.assertj.core.api.Assertions.assertThat;
import auction.Application;
import auction.domain.Auction;
import auction.repository.AuctionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@DataJpaTest
public class AuctionRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    AuctionRepository repository;

    @Test
    public void should_find_auctions_if_repository_is_not_empty() {
        entityManager.persist(new Auction());
        entityManager.flush();
        List<Auction> auctions = repository.findAll();
        assertThat(auctions).isNotEmpty();
    }

    @Test
    public void should_delete_all_auctions() {
        entityManager.persist(new Auction());
        entityManager.persist(new Auction());
        entityManager.flush();
        repository.deleteAll();
        assertThat(repository.findAll()).isEmpty();
    }

    @Test
    public void should_find_all_auctions() {
        Auction auctionOne = new Auction();
        entityManager.persist(auctionOne);

        Auction auctionTwo = new Auction();
        entityManager.persist(auctionTwo);

        Auction auctionThree = new Auction();
        entityManager.persist(auctionThree);

        List<Auction> auctions = repository.findAll();
        assertThat(auctions).hasSize(3).contains(auctionOne, auctionTwo, auctionThree);
    }

    @Test
    public void should_find_auction_by_id() {
        Auction auctionOne = new Auction();
        entityManager.persist(auctionOne);

        Auction auctionTwo = new Auction();
        entityManager.persist(auctionTwo);

        Auction foundAuction = repository.findOne(auctionTwo.getId());
        assertThat(foundAuction).isEqualTo(auctionTwo);
    }
}
