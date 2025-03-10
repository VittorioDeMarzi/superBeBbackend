package de.supercode.superBnB.entities.property;

import de.supercode.superBnB.entities.booking.Booking;
import de.supercode.superBnB.entities.booking.GenerateBookingsNumber;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    @Column(length = 1000)
    private String description;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Address address;
    private int maxNumGuests;
    private BigDecimal minPricePerNight;
    private boolean isBooked  = false;
    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Booking> bookings;
    private int rooms;
    private List<String> picUrls;
    private boolean isPublic;
    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    // Constructor
    public Property() {
        this.picUrls = new ArrayList<String>();
        this.isPublic=false;
        this.isBooked=false;
        this.address = new Address();
    }

    public Property(String title, String description, int maxNumGuests, BigDecimal minPricePerNight, Address address, int rooms) {
        this.title = title;
        this.description = description;
        this.maxNumGuests = maxNumGuests;
        this.minPricePerNight = minPricePerNight;
        this.address = address;
        this.rooms = rooms;
        this.picUrls = new ArrayList<String>();
        this.isPublic = false;
        this.isBooked = false;
    }

    public double getAverageRating() {
        if (reviews.isEmpty()) {
            return 0.0;  // Standardwert, wenn keine Bewertungen vorhanden sind
        }

        return reviews.stream()
                .mapToInt(Review::getStars)
                .average()
                .orElse(0.0);  // Durchschnitt der Sterne oder 0.0
    }

    public long getId() {
        return id;
    }

    // Getter and setter methods
    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public BigDecimal getMinPricePerNight() {
        return minPricePerNight;
    }

    public void setMinPricePerNight(BigDecimal minPricePerNight) {
        this.minPricePerNight = minPricePerNight;
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

    public int getRooms() {
        return rooms;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }

    public List<String> getPicUrls() {
        return picUrls;
    }

    public void setPicUrls(List<String> picUrls) {
        this.picUrls = picUrls;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    // methods
    public void addBooking(Booking booking) {
        if (bookings == null) {
            bookings = new ArrayList<>();
        }
        bookings.add(booking);
        booking.setProperty(this);
        isBooked = true;
    }
}
