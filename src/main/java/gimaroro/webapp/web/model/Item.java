package gimaroro.webapp.web.model;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = ("id"))
public class Item {
    @Id
    @GeneratedValue
    private int id;

    @Column
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column
    private Date date;

    @Column
    private Ad ad;

    @ManyToOne
    @JoinColumn(name = "fk_category")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "fk_author")
    private User author;

    //User that has this item as a favourite
    @ManyToMany(mappedBy = "favourites")
    private List<User> userList;

    @Lob
    private byte[] image;

    public void modify(Item item){
        this.description = item.getDescription();
        this.title = item.getTitle();
        this.author = item.getAuthor();
        this.category = item.getCategory();
        this.ad = item.getAd();
        if(item.image != null)
            this.image = item.image;
    }

    void addUser(User user){
        if(userList == null) {
            userList = new ArrayList<>();
        }
        userList.add(user);
    }

    void removeUser(User user){
        if(userList == null) {
            return;
        }
        userList.remove(user);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id == item.id && Objects.equals(title, item.title) && Objects.equals(description, item.description) && Objects.equals(date, item.date) && ad == item.ad && Objects.equals(category, item.category) && Objects.equals(author, item.author) && Objects.equals(userList, item.userList) && Arrays.equals(image, item.image);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, title, description, date, ad, category, author, userList);
        result = 31 * result + Arrays.hashCode(image);
        return result;
    }
}
