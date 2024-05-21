package az.job.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Company {
    @Id
    @GeneratedValue(generator = "uuid2")
    UUID id;
    String companyName;
    @Column(unique = true)
    String email;
    @JsonIgnore
    String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    String phoneNumber;
}