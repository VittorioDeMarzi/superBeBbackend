# Superbnb Backend 🏠

Superbnb Backend is a robust backend solution for managing short-term rental properties. It offers features for user registration, property listing management, and booking operations. The platform includes an admin panel that empowers administrators to create, update, and manage property listings, including adding photos, setting seasonal prices, and controlling property visibility.

## 🌐 Frontend Repository

- [Superbnb Frontend](https://github.com/VittorioDeMarzi/SuperBnB_FrontEnd)

## ✨ Key Features

### 👤 User
- **User Registration and Profile Management**: Users can sign up, log in, and update their profile details.

### 🔍 Advanced Search
- **Property Search Filters**: Users can search for properties using filters like city, number of rooms, number of guests, and available dates.

### 🏡 Listings
- **Admin Panel**:
  - Create new property listings.
  - Add photos to properties.
  - Publish and manage listings.
  - Add seasonal prices to properties.

### 📅 Bookings
- **Booking System**: Allows users to create and manage bookings for properties.
- **Availability Check**: Verifies property availability in real-time.
- **Email Confirmation**: Users receive a confirmation email after making a booking.

### 🖼️ Media
- **Image Upload**: Supports image uploads via the external service Imgbb.

## 💻 Technologies Used

- **Java 21**: Main programming language.
- **Spring Boot**: Framework for backend development.
- **PostgreSQL**: Relational database for data persistence.
- **JWT (Json Web Token)**: Secure authentication using JwtEncoder.
- **OAuth2**: Authentication and authorization.
- **Redis**: Caching for improved performance.
- **Docker with Docker Compose**: Easy and quick local deployment.
- **Imgbb**: Service for image uploads.
- **Mailtrap**: Service for testing email sending.

## 🐳 Docker Setup

### Development Environment:
```bash
docker-compose -f docker-compose.yml up --build
```

### Production Environment:
```bash
docker-compose -f docker-compose-depl.yml up --build
```

📝 **Note**: Adjust the `.env` files according to the environment.

--- 

## 🚀 API Endpoints

### User Controller
- **GET** `/api/v1/user`
- **PUT** `/api/v1/user`

### Property Controller
- **GET** `/api/v1/superbeb/property/{id}`
- **PUT** `/api/v1/superbeb/property/{id}`
- **DELETE** `/api/v1/superbeb/property/{id}`
- **PUT** `/api/v1/superbeb/property/change-visibility/{propertyId}`
- **GET** `/api/v1/superbeb/property`
- **POST** `/api/v1/superbeb/property`
- **POST** `/api/v1/superbeb/property/save-properties`
- **POST** `/api/v1/superbeb/property/public/check-availability`
- **POST** `/api/v1/superbeb/property/addSeasonalPrice/{id}`
- **GET** `/api/v1/superbeb/property/seasonalPrices/{id}`
- **GET** `/api/v1/superbeb/property/public`
- **GET** `/api/v1/superbeb/property/public/filtered`
- **GET** `/api/v1/superbeb/property/public/cities`

### Admin Controller
- **PUT** `/api/v1/admin/change-role`

### ImgBB Controller
- **POST** `/api/v1/superbeb/imgbb/upload/{propertyId}`
- **POST** `/api/v1/superbeb/imgbb/upload-images/{propertyId}`

### Booking Controller
- **POST** `/api/v1/superbeb/booking`
- **GET** `/api/v1/superbeb/booking/userBookings/{id}`
- **GET** `/api/v1/superbeb/booking/personalBookings`

### Review Controller
- **GET** `/api/v1/review`
- **POST** `/api/v1/review`
- **GET** `/api/v1/review/property`

### Auth Controller
- **POST** `/api/v1/auth/signup`
- **POST** `/api/v1/auth/signin`

## 📚 API Documentation

The APIs are documented using Swagger/OpenAPI. Once the project is running, the documentation can be accessed at:
```
http://localhost:8080/swagger-ui.html
```

## 📩 Contact

For support or collaboration requests, feel free to reach out via email: vittoriodm7@gmail.com.
