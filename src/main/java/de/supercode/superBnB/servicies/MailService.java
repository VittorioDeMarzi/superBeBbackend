package de.supercode.superBnB.servicies;

import de.supercode.superBnB.dtos.BookingResponseDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private JavaMailSender mailSender;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendBookingConfirmationEmail(String email, BookingResponseDto bookingResponseDto) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            mimeMessage.setRecipients(MimeMessage.RecipientType.TO, email);
            mimeMessage.setSubject("SuperBnB Booking Confirmation");
            mimeMessage.setFrom("super@bnb.com");
            String htmlContent = String.format("""
                <html>
                <body>
                    <h1 style="font-size: 24px; color: #333;">Booking Confirmation</h1>
                    <p style="font-size: 16px; color: #555;">
                        Dear %s %s,<br>
                        Your booking has been successfully confirmed! Here are the details of your reservation:
                    </p>
                    <h2 style="font-size: 20px; color: #333;">Reservation Details</h2>
                    <p style="font-size: 16px; color: #555;">
                        <strong>Booking Code:</strong> %s<br>
                        <strong>Booking Date:</strong> %s<br>
                    </p>
                    <h2 style="font-size: 20px; color: #333;">Stay Details</h2>
                    <p style="font-size: 16px; color: #555;">
                        <strong>Check-in:</strong> %s<br>
                        <strong>Check-out:</strong> %s<br>
                        <strong>Guests:</strong> %d Adults, %d Children (Total: %d Guests)<br>
                        <strong>Total Nights:</strong> %d<br>
                        <strong>Total Price:</strong> %s €
                    </p>
                    <h2 style="font-size: 20px; color: #333;">Property Details</h2>
                    <p style="font-size: 16px; color: #555;">
                        <strong>Title:</strong> %s<br>
                        <strong>Description:</strong> %s<br>
                        <strong>Address:</strong> %s %s, %s %s, %s <br>
                        <strong>Rooms:</strong> %d<br>
                    </p>
                    <p style="font-size: 16px; color: #555;">
                        Thank you for booking with us! We look forward to welcoming you soon.
                    </p>
                    <p style="font-size: 16px; color: #555;">
                        Best regards,<br>
                        The SuperBnB Team
                    </p>
                </body>
                </html>
                """,
                bookingResponseDto.user().firstName(),
                bookingResponseDto.user().lastName(),
                bookingResponseDto.bookingCode(),
                bookingResponseDto.bookingDate().toString(),
                bookingResponseDto.checkInDate().toString(),
                bookingResponseDto.checkOutDate().toString(),
                bookingResponseDto.numAdults(),
                bookingResponseDto.numChildren(),
                bookingResponseDto.totGuests(),
                bookingResponseDto.totalNights(),
                bookingResponseDto.totalPrice().toString(),
                bookingResponseDto.property().title(),
                bookingResponseDto.property().description(),
                bookingResponseDto.property().street(),
                bookingResponseDto.property().houseNumber(),
                bookingResponseDto.property().zipCode(),
                bookingResponseDto.property().city(),
                bookingResponseDto.property().country(),
                bookingResponseDto.property().rooms()
            );
            mimeMessage.setContent(htmlContent, "text/html");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send booking confirmation email", e);
        }
    }
}
