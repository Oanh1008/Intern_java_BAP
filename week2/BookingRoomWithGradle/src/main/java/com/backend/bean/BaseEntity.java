package com.backend.bean;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
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
}
