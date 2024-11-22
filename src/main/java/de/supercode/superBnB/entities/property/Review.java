package de.supercode.superBnB.entities.property;

import de.supercode.superBnB.entities.user.User;
import jakarta.persistence.*;

@Entity
public class Review {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private int stars;  // 1-5

        private String content;

        @ManyToOne
        @JoinColumn(name = "book_id")
        private Property property;

        @ManyToOne
        @JoinColumn(name = "user_id")
        private User user;

        // Getter und Setter
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public int getStars() {
            return stars;
        }

        public void setStars(int stars) {
            if (stars < 1 || stars > 5) {
                throw new IllegalArgumentException("Stars must be between 1 and 5");
            }
            this.stars = stars;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Property getProperty() {
            return property;
        }

        public void setProperty(Property property) {
            this.property = property;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

}
