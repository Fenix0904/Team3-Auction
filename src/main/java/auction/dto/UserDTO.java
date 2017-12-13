package auction.dto;

import auction.domain.User;
import auction.dto.shortdto.AuctionShortDTO;

import java.util.ArrayList;
import java.util.List;

public class UserDTO {

    private int id;
    private String username;
    private User.Role role;
    private String name;
    private List<BidDTO> bids;
    private List<AuctionShortDTO> auctions;
    private List<AuctionShortDTO> subscribedAuctions;

    public static UserDTO fromModel(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setName(user.getName());
        userDTO.setRole(user.getRole());
        userDTO.setBids(BidDTO.fromModel(user.getBids()));
        userDTO.setSubscribedAuctions(AuctionShortDTO.fromModel(user.getSubscribedAuctions()));
        userDTO.setAuctions(AuctionShortDTO.fromModel(user.getAuctions()));
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

    public User.Role getRole() {
        return role;
    }

    public void setRole(User.Role role) {
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

    public List<AuctionShortDTO> getAuctions() {
        return auctions;
    }

    public void setAuctions(List<AuctionShortDTO> auctions) {
        this.auctions = auctions;
    }

    public List<AuctionShortDTO> getSubscribedAuctions() {
        return subscribedAuctions;
    }

    public void setSubscribedAuctions(List<AuctionShortDTO> subscribedAuctions) {
        this.subscribedAuctions = subscribedAuctions;
    }
}
