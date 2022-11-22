package com.example.contactmanager.domain.entities;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "contacts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class ContactEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "adrress")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToOne
    private UserEntity user;

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContactEntity otherContact)) return false;
        return id != null && id.equals(otherContact.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

