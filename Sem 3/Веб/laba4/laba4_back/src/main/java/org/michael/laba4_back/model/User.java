package org.michael.laba4_back.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "login", unique = true)
    private String login;
    @Column(name = "pass")
    private String pass;

    public User(String login, String pass) {
        this.login = login;
        this.pass = pass;
    }
}
