package auction.dto.shortdto;

import auction.domain.User;

public class UserShortDTO {
    private int id;
    private String username;
    private User.Role role;

    public static UserShortDTO fromMode(User user) {
        UserShortDTO userShortDTO = new UserShortDTO();
        userShortDTO.setId(user.getId());
        userShortDTO.setUsername(user.getUsername());
        userShortDTO.setRole(user.getRole());
        return userShortDTO;
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
}
