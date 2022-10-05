package com.example.demo.db.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "USERS")
public class UserEntity {

    @Id
    @Column("ID")
    private Integer id;

    @Column("NAME")
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
