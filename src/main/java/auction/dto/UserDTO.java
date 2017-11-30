package auction.dto;

import auction.domain.Auction;
import auction.domain.Role;
import auction.domain.User;
import auction.dto.shortdto.AuctionShortDTO;

import java.util.ArrayList;
import java.util.List;

public class UserDTO {

    private int id;
    private String username;
    private Role role;
    private String name;
    private List<BidDTO> bids;
    private List<Integer> auctionsId;
    private List<AuctionShortDTO> subscribedAuctions;

    public static UserDTO fromModel(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setName(user.getName());
        userDTO.setRole(user.getRole());
        userDTO.setBids(BidDTO.fromModel(user.getBids()));
        userDTO.setSubscribedAuctions(AuctionShortDTO.fromModel(user.getSubscribedAuctions()));
        List<Integer> ids = new ArrayList<>();
        for (Auction auction : user.getAuctions()) {
            ids.add(auction.getId());
        }
        userDTO.setAuctionsId(ids);

        return userDTO;
    }

    public static List<UserDTO> fromModel(List<User> users) {
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : users) {
            userDTOS.add(UserDTO.fromModel(user));
        }
        return userDTOS;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BidDTO> getBids() {
        return bids;
    }

    public void setBids(List<BidDTO> bids) {
        this.bids = bids;
    }

    public List<AuctionShortDTO> getSubscribedAuctions() {
        return subscribedAuctions;
    }

    public void setSubscribedAuctions(List<AuctionShortDTO> subscribedAuctions) {
        this.subscribedAuctions = subscribedAuctions;
    }

    public List<Integer> getAuctionsId() {
        return auctionsId;
    }

    public void setAuctionsId(List<Integer> auctionsId) {
        this.auctionsId = auctionsId;
    }
}
