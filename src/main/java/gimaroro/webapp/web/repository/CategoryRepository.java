package gimaroro.webapp.web.repository;

import gimaroro.webapp.web.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findCategoryByNameContaining(String name);
}
