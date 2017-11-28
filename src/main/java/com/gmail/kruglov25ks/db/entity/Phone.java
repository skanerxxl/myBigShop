package com.gmail.kruglov25ks.db.entity;

import javax.persistence.*;

@Entity
public class Phone {
    @Id
    @GeneratedValue
    private long id;

    private String phoneNumber;

    @ManyToOne
    @JoinColumn
    private Adress adress;

}
