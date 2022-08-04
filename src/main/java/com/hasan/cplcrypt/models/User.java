package com.hasan.cplcrypt.models;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Setter
@Getter
@ToString
@Table(name = "user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "Name field is required !!")
    private String name;

    @Column(unique = true)
    @NotBlank(message = "Email can't be empty!")
    @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$",message = "Invalid email !!")
    private String email;

    @NotBlank(message = "Password field is required !!")
    private String password;

    private String role;

    private boolean enabled;

    private String imageUrl;

    @Column(length = 500)
    private String about;

    private LocalDateTime createdAt = LocalDateTime.now();

    private String ethAccount;
    private String ethBalance;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,mappedBy = "user")
    private List<MyTransaction> myTransactions = new ArrayList<>();

}

