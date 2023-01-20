package gimaroro.webapp.web.service;

import gimaroro.webapp.web.model.Item;
import gimaroro.webapp.web.model.User;
import gimaroro.webapp.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> list(){
        return userRepository.findAll();
    }

    public User findUserByUsername(String username){return userRepository.findUserByUsername(username);}

    public void post(@RequestBody User user) {
        System.out.println(user.getPassword());
        user.encodePass();
        userRepository.saveAndFlush(user);
    }

    public void addToFav(Item item, User user) {
        user.addFavorite(item);
        userRepository.saveAndFlush(user);
    }

    public void removeFromFav(Item item, User user) {
        user.removeFavorite(item);
        userRepository.saveAndFlush(user);
    }
}
