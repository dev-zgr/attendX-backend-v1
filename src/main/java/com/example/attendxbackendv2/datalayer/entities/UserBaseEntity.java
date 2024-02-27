package com.example.attendxbackendv2.datalayer.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * This class is used to map the fields that all the entities that has
 */

@Data
@ToString
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@EqualsAndHashCode
public class UserBaseEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "user_id")
    private Long userId;

    @NotEmpty(message = "Lecturer name cannot be null or empty")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @Size(max = 60, message = "Last name must be less than or equal to 60 characters")
    private String lastName;

    @Column(name = "e_mail")
    private String email;

    @Column(name = "password")
    private String password;
    @Column(name = "phone_number", nullable = false)
    @Pattern(regexp = "\\d{10}", message = "Invalid phone number format")
    private String phoneNumber;

    @Embedded
    private AddressEmbeddable address;

    public UserBaseEntity(String firstName, String lastName, String email,String password ,String phoneNumber, AddressEmbeddable address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }



}
