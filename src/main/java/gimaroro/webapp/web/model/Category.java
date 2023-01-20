package gimaroro.webapp.web.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity @AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = ("id"))
public class Category {
    @Id
    private String name;
}
