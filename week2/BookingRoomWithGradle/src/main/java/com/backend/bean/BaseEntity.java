package com.backend.bean;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass

public abstract class BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @CreatedBy
    private String name;

    @CreatedDate
    private LocalDateTime createTime;


    private LocalDateTime updateTime;

    @PrePersist
    public void setCreateTime() {
        this.createTime = LocalDateTime.now();
    }

    @PreUpdate
    public void setUpdateTime() {
        this.updateTime = LocalDateTime.now();
    }

    public BaseEntity() {
    }

    public BaseEntity(Integer id, String name, LocalDateTime createTime, LocalDateTime updateTime) {
        this.id = id;
        this.name = name;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
