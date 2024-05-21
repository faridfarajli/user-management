package az.job.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(generator = "uuid2")
    UUID id;
    String name;
    String surname;
    @Column(unique = true)
    String email;
    @JsonIgnore
    String password;
    String phoneNumber;
    Date birthDate;
    @Enumerated(EnumType.STRING)
    private Role role;

}
