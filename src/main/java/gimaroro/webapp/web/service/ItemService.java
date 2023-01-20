package gimaroro.webapp.web.service;

import gimaroro.webapp.web.model.Category;
import gimaroro.webapp.web.model.Item;
import gimaroro.webapp.web.model.User;
import gimaroro.webapp.web.repository.CategoryRepository;
import gimaroro.webapp.web.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/*
 * All'inserimento di un nuovo Item (POST) o alla modifica (PUT) bisogna inserire nel JSON solo gli id
 *  di Categoria utente e ruolo nella struttura JSON perché vengono create all boot se non esistono
 *  grazie a CommandLineRunner e bisogna usare quelli
 */

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public void post(@RequestBody Item item) {
        item.setDate(new Date());
        itemRepository.saveAndFlush(item);
    }

    public List<Item> list(){
        return itemRepository.findAll();
    }
    public ResponseEntity<Item> get(@PathVariable int id){
        Optional<Item> item = itemRepository.findById(id);
        return item.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public void put(@PathVariable int id, @RequestBody Item item) {
        Optional<Item> itemToModify = itemRepository.findById(id);
        if(itemToModify.isEmpty()) {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return;
        }
        itemToModify.get().modify(item);
        itemRepository.save(itemToModify.get());
    }

    public void delete(@PathVariable int id) {
        Optional<Item> itemToDelete = itemRepository.findById(id);
        if (itemToDelete.isEmpty()) {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return;
        }
        itemRepository.delete(itemToDelete.get());
    }

    public List<Item> search(String q){
        //Research items
        return new ArrayList<>(itemRepository.findItemsByTitleContainingOrCategoryOrDescriptionContaining(q, categoryRepository.findCategoryByNameContaining(q), q));
    }

    public List<Item> findByCategoryAndUser(Category category, User user){
        //Research items
        return new ArrayList<>(itemRepository.findItemsByCategoryAndUserListContaining(category, user));
    }
}
