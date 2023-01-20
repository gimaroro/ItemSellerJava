package gimaroro.webapp.web.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity @AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = ("id"))
public class Role {
    @Id
    private String name;
}
