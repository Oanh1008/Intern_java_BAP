package com.backend.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "room")
public class Room extends BaseEntity{
    @Column(name = "room_code")
    private String roomCode;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "price")
    private Double price;

    @Column(name = "state")
    private String state;

    @Column(name = "description")
    private String description;

    @Column(name = "image")
    private String image;

    @OneToMany(mappedBy = "room")
    List<Booking> bookings;

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Double getPrice() {
        return price;
    }

    public String getState() {
        return state;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

}
