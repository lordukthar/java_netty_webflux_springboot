package com.example.demo.db.entity;

import javax.persistence.*;

@Entity
@Table(name = "myusers")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME", length = 36)
    private String name;

    public UserEntity() {
    }

    public UserEntity(String userName) {
        this.name = userName;
    }

    public String getName() {
        return name;
    }
}
