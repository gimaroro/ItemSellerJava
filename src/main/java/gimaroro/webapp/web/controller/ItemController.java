package gimaroro.webapp.web.controller;

import gimaroro.webapp.web.model.Ad;
import gimaroro.webapp.web.model.Category;
import gimaroro.webapp.web.model.Item;
import gimaroro.webapp.web.model.Role;
import gimaroro.webapp.web.service.CategoryService;
import gimaroro.webapp.web.service.ItemService;
import gimaroro.webapp.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Controller
@RequestMapping("/")
public class ItemController {
    @Autowired
    private ItemService itemService = new ItemService();
    @Autowired
    private CategoryService categoryService = new CategoryService();
    @Autowired
    private UserService userService = new UserService();

    @GetMapping("")
    public String list(Model model){
        List<Item> items = itemService.list();

        model.addAttribute("isIndex", true);
        model.addAttribute("user", new gimaroro.webapp.web.model.User());
        //Get favourites from current user
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() != "anonymousUser") {
            User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("favorites", userService.findUserByUsername(currentUser.getUsername()).getFavourites());
        }else{
            model.addAttribute("favorites", null);
        }
        model.addAttribute("items", items);
        return "index";
    }
    @GetMapping(value="/item/{id}")
    public String get(@PathVariable int id, Model model){
        Item item = itemService.get(id).getBody();
        if(item == null)
            return "redirect:/";

        model.addAttribute("user", new gimaroro.webapp.web.model.User());
        model.addAttribute("item", item);
        //Get favourites from current user
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() != "anonymousUser") {
            User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("favorites", userService.findUserByUsername(currentUser.getUsername()).getFavourites());
        }else{
            model.addAttribute("favorites", null);
        }
        model.addAttribute("image", Base64.getEncoder().encodeToString(item.getImage()));
        return "itemDetails";
    }
    @GetMapping(value="/item/{id}/image",  produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getImageById(@PathVariable int id){
        return itemService.get(id).getBody().getImage();
    }

    @GetMapping(value="/item/new")
    public String getNewItem(Model model){
        model.addAttribute("user", new gimaroro.webapp.web.model.User());
        model.addAttribute("item", new Item());
        model.addAttribute("items", itemService.list());
        model.addAttribute("categories", categoryService.list());
        model.addAttribute("authors", userService.list());
        model.addAttribute("ads", Ad.values());
        model.addAttribute("action", "new");
        //Get favourites from current user
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() != "anonymousUser") {
            User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("favorites", userService.findUserByUsername(currentUser.getUsername()).getFavourites());
        }else{
            model.addAttribute("favorites", null);
        }
        return "createEditItemForm";
    }
    @PostMapping(value="/item/new")
    public String postNewItem(Item item, @RequestParam("file") MultipartFile file) throws IOException {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        item.setAuthor(userService.findUserByUsername(currentUser.getUsername()));
        if(!file.isEmpty())
            item.setImage(file.getBytes());
        itemService.post(item);
        return "redirect:/";
    }

    @GetMapping(value="/item/{id}/edit")
    public String getEditItem(@PathVariable int id, Model model){
        Item item = itemService.get(id).getBody();
        if(item == null)
            return "redirect:/";
        model.addAttribute("user", new gimaroro.webapp.web.model.User());
        model.addAttribute("item", item);
        model.addAttribute("categories", categoryService.list());
        model.addAttribute("authors", userService.list());
        model.addAttribute("ads", Ad.values());
        model.addAttribute("action", id +"/edit");
        //Get favourites from current user
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("favorites", userService.findUserByUsername(currentUser.getUsername()).getFavourites());
        return "createEditItemForm";
    }
    @PostMapping(value="/item/{id}/edit")
    public String postEditItem(@PathVariable int id, @RequestParam("file") MultipartFile file, Item item) throws IOException {
        Item itemToEdit = itemService.get(id).getBody();
        if(!file.isEmpty())
            item.setImage(file.getBytes());
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        item.setAuthor(userService.findUserByUsername(currentUser.getUsername()));
        if(itemToEdit != null)
            itemService.put(id, item);
        return "redirect:/";
    }

    @GetMapping(value="/item/{id}/delete")
    public String getDeleteItem(@PathVariable int id){
        Item item = itemService.get(id).getBody();
        if(item != null)
            itemService.delete(id);
        return "redirect:/";
    }

    @PostMapping(value="/register")
    public String register(gimaroro.webapp.web.model.User user) {
        user.setRole(new Role("ROLE_USER"));
        userService.post(user);
        return "redirect:/";
    }

    @GetMapping(value="/logout")
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "redirect:/";
    }

    @GetMapping(value="/login")
    public String login() {
        return "redirect:/";
    }

    @GetMapping(value="/item/search")
    @ResponseBody
    public List<Item> search(@RequestParam("q") String search) {
        return itemService.search(search);
    }

    @GetMapping(value="/item/{id}/addToFav")
    public String getAddToFav(@PathVariable int id, Model model){
        Item item = itemService.get(id).getBody();
        if(item == null)
            return "redirect:/";
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.addToFav(item, userService.findUserByUsername(currentUser.getUsername()));
        return "redirect:/item/"+ id;
    }

    @GetMapping(value="/item/{id}/removeFromFav")
    public String getRemoveFromFav(@PathVariable int id, Model model){
        Item item = itemService.get(id).getBody();
        if(item == null)
            return "redirect:/";
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.removeFromFav(item, userService.findUserByUsername(currentUser.getUsername()));
        return "redirect:/item/"+ id;
    }
    @GetMapping("/favorites")
    public String favorites(Model model){
        List<Item> items = itemService.list();

        model.addAttribute("isIndex", true);
        model.addAttribute("user", new gimaroro.webapp.web.model.User());
        //Get favourites from current user
        User currentUser;
        //Get favourites from current user
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() != "anonymousUser") {
            currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("favorites", userService.findUserByUsername(currentUser.getUsername()).getFavourites());
        }else{
            model.addAttribute("favorites", null);
        }
        model.addAttribute("items", items);
        return "favorites";
    }
}
