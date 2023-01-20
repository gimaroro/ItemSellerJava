package gimaroro.webapp.web.repository;

import gimaroro.webapp.web.model.Category;
import gimaroro.webapp.web.model.Item;
import gimaroro.webapp.web.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
    List<Item> findItemsByTitleContainingOrCategoryOrDescriptionContaining(String title, Category category, String description);
    List<Item> findItemsByCategoryAndUserListContaining(Category category, User user);
}
