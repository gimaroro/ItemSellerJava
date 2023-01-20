package gimaroro.webapp.web;

import gimaroro.webapp.web.model.Category;
import gimaroro.webapp.web.model.Role;
import gimaroro.webapp.web.model.User;
import gimaroro.webapp.web.repository.CategoryRepository;
import gimaroro.webapp.web.repository.RoleRepository;
import gimaroro.webapp.web.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class WebApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(RoleRepository roleRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
		return (args) -> {
			Role role = new Role("ROLE_ADMIN");
			Role role2 = new Role("ROLE_USER");
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			User user = new User("admin", "Admin", "Istrator", "admin", role);
			Category category = new Category("important");
			if(roleRepository.count() == 0) {
				roleRepository.saveAndFlush(role);
				roleRepository.saveAndFlush(role2);
			}
			if(userRepository.count() == 0) {
				userRepository.saveAndFlush(user);
			}
			if(categoryRepository.count() == 0) {
				categoryRepository.saveAndFlush(category);
			}
		};
	}
}
