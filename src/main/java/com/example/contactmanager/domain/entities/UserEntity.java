package com.example.contactmanager.domain.entities;

import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "email",  unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private RoleType roleType;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @Column(name = "user_contacts")
    private List<ContactEntity> contacts = new ArrayList<>();

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity otherUser)) return false;
        return Objects.equals(getUsername(), otherUser.getUsername());
    }

    @Override
    public int hashCode() {
        return getUsername().hashCode();
    }
}
