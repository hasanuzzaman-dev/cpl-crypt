package com.hasan.cplcrypt.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@ToString
@Table
public class MyTransaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private String addressFrom;
    private String addressTo;
    private String amountEth;
    private String keyword;
    private String referenceText;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
