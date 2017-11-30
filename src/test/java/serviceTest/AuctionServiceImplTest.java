package serviceTest;

import auction.Application;
import auction.domain.Auction;
import auction.domain.Lot;
import auction.domain.User;
import auction.repository.AuctionRepository;
import auction.repository.UserRepository;
import auction.service.AuctionService;
import auction.service.AuctionServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@DataJpaTest
public class AuctionServiceImplTest {

    @Autowired
    private AuctionService auctionService;

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private UserRepository userRepository;

    private Authentication auth;
    private SecurityContext securityContext;
    private Auction auctionFirst, auctionSecond;
    private User user;
    private UserDetails userDetails;
    private List<SimpleGrantedAuthority> authorities;
    private final static String FAKE_USER_ROLE = "ROLE_USER";
    private final static String FAKE_ADMIN_ROLE = "ROLE_ADMIN";
    private final static String FAKE_USER_NAME = "UserTest";
    private final int fakeAuctionId = 2;
    @TestConfiguration
    static class AuctionServiceImplTestContextConfiguration {

        @Bean
        public AuctionService auctionService() {

            return new AuctionServiceImpl();
        }
    }

    @Before
    public void setUp() {
        auth = Mockito.mock(Authentication.class);
        securityContext = Mockito.mock(SecurityContext.class);

        auctionFirst = new Auction();
        auctionFirst.setLots(Arrays.asList(new Lot(), new Lot(), new Lot()));

        auctionSecond = new Auction();
        auctionSecond.setLots(Arrays.asList(new Lot(), new Lot()));

        authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(FAKE_USER_ROLE));
        authorities.add(new SimpleGrantedAuthority(FAKE_ADMIN_ROLE));

        userDetails = Mockito.mock(UserDetails.class);
        user = new User();
        user.setUsername(FAKE_USER_NAME);
        user.setAuctions(Arrays.asList(auctionFirst, auctionSecond));
    }

    @Test
    public void deleteAuctionTest() {
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);
        auctionRepository.save(auctionFirst);
        auctionRepository.save(auctionSecond);
        Mockito.doReturn(authorities).when(auth).getAuthorities();

        when(auth.getPrincipal()).thenReturn(new User());
        userRepository.save(user);
        when(userDetails.getUsername()).thenReturn(FAKE_USER_NAME);

        assertThat(auctionService.deleteAuction(fakeAuctionId)).isEqualTo(true);
    }
}
