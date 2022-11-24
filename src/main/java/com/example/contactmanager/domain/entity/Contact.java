package com.example.contactmanager.domain.entity;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "contacts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private String address;

    private String phoneNumber;

    @ManyToOne
    private User user;

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contact otherContact)) return false;
        return id != null && id.equals(otherContact.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

