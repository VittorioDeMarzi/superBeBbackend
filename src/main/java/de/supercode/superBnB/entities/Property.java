package de.supercode.superBnB.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
    private int maxNumGuests;
    private BigDecimal pricePerNight;
    private boolean isBooked  = false;
    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    private List<Booking> bookings = new ArrayList<Booking>();

    // Constructor
    public Property() {}

    public Property(int maxNumGuests, BigDecimal pricePerNight, Address address) {
        this.maxNumGuests = maxNumGuests;
        this.pricePerNight = pricePerNight;
        this.address = address;
    }

    public long getId() {
        return id;
    }

    // Getter and setter methods
    public void setId(long id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getMaxNumGuests() {
        return maxNumGuests;
    }

    public void setMaxNumGuests(int maxNumGuests) {
        this.maxNumGuests = maxNumGuests;
    }

    public BigDecimal getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(BigDecimal pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}
