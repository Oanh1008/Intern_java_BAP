package com.backend.bookingroom.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "role")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Role {

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    @Id
    private Integer id;
    @Column(name = "name")
    private String name;
}
