package com.gmail.kruglov25ks.db.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Adress {

    @Id
    @GeneratedValue
    private long id;

    private String country;
    private String region;
    private String city;
    private String houseNumber;
    private String flatNumber;

    @OneToMany(mappedBy = "adress", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Phone> phones = new HashSet<>();


}
