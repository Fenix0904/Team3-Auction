package serviceTest;

import auction.Application;
import auction.domain.Auction;
import auction.domain.Lot;
import auction.domain.User;
import auction.repository.LotRepository;
import auction.repository.UserRepository;
import auction.service.LotService;
import auction.service.LotServiceImpl;
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
public class LotServiceImplTest  {

    @Autowired
    private LotService lotService;

    @Autowired
    private LotRepository lotRepository;

    @Autowired
    private UserRepository userRepository;

    private SecurityContext securityContext;
    private Authentication auth;
    private List<SimpleGrantedAuthority> authorities;
    private User user;
    private UserDetails userDetails;
    private Auction auctionFirst, auctionSecond;
    private final static String FAKE_USER_ROLE = "ROLE_USER";
    private final static String FAKE_ADMIN_ROLE = "ROLE_ADMIN";
    private final static String FAKE_USER_NAME = "UserTest";
    private final int fakeAuctionId = 2;

    @TestConfiguration
    static class LotServiceImplTestContextConfiguration {

        @Bean
        public LotService auctionService() {
            return new LotServiceImpl();
        }
    }

    @Before
    public void setUp() {
        securityContext = Mockito.mock(SecurityContext.class);
        auth = Mockito.mock(Authentication.class);
        authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(FAKE_USER_ROLE));
        authorities.add(new SimpleGrantedAuthority(FAKE_ADMIN_ROLE));

        user = new User();
        user.setUsername(FAKE_USER_NAME);
        userDetails = Mockito.mock(UserDetails.class);
        auctionFirst = new Auction();
        auctionFirst.setLots(Arrays.asList(new Lot(), new Lot(), new Lot()));
        auctionSecond = new Auction();
        auctionSecond.setLots(Arrays.asList(new Lot(), new Lot()));
        user.setAuctions(Arrays.asList(auctionFirst, auctionSecond));
    }

    @Test
    public void deleteLotTest()
    {
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);

        lotRepository.save(new Lot());
        lotRepository.save(new Lot());
        Mockito.doReturn(authorities).when(auth).getAuthorities();

        when(auth.getPrincipal()).thenReturn(new User());
        userRepository.save(user);
        when(userDetails.getUsername()).thenReturn(FAKE_USER_NAME);

        assertThat(lotService.deleteLot(2)).isEqualTo(true);
    }
}
