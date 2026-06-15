package dev.sorokin.eventmanager.model.entity;

import dev.sorokin.eventmanager.model.enums.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<RegistrationEntity> registrations = new ArrayList<>();

    @OneToMany(mappedBy = "owner")
    private List<EventEntity> ownedEvents = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    public UserEntity(
            String login,
            Integer age,
            String password,
            UserRole role
    ) {
        this.login = login;
        this.age = age;
        this.password = password;
        this.role = role;
    }

}