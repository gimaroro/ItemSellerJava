package gimaroro.webapp.web.service;

import gimaroro.webapp.web.model.Category;
import gimaroro.webapp.web.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> list(){
        return categoryRepository.findAll();
    }

    public Category find(String name){
        return categoryRepository.findCategoryByNameContaining(name);
    }
}
