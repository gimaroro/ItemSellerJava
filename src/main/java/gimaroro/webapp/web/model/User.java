package gimaroro.webapp.web.model;

import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = ("id"))
@ToString
public class User {
    @Id
    private String username;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String password;

    @ManyToMany
    @JoinColumn(name = "fk_favourites")
    private List<Item> favourites;

    public User(String username, String name, String surname, String password, Role role) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.password = encoder.encode(password);
        this.role = role;
    }

    public void encodePass(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.password = encoder.encode(this.password);
    }

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "fk_role")
    private Role role;

    public void addFavorite(Item item){
        if(favourites == null) {
            favourites = new ArrayList<>();
        }
        favourites.add(item);
        item.addUser(this);
    }

    public void removeFavorite(Item item){
        if(favourites == null) {
            return;
        }
        favourites.remove(item);
        item.removeUser(this);
    }

}
