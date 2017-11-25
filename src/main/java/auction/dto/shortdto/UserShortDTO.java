package auction.dto.shortdto;

import auction.domain.Role;
import auction.domain.User;

public class UserShortDTO {
    private int id;
    private String username;
    private Role role;
    private String name;

    public static UserShortDTO fromMode(User user) {
        UserShortDTO userShortDTO = new UserShortDTO();
        userShortDTO.setId(user.getId());
        userShortDTO.setUsername(user.getUsername());
        userShortDTO.setName(user.getName());
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
}
