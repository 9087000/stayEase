package com.example.demo.service;

import com.example.demo.Repository.BookingRepository;
import com.example.demo.Repository.HotelRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.model.Booking;
import com.example.demo.model.Hotel;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private UserRepository userRepository;

    public Booking bookRoom(Long hotelId, String customerEmail) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        if (hotel.getAvailableRooms() <= 0) {
            throw new RuntimeException("No rooms available");
        }

        User customer = userRepository.findByEmail(customerEmail)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Booking booking = new Booking();
        booking.setHotel(hotel);
        booking.setCustomer(customer);
        booking.setBookingDate(LocalDate.now());

        hotel.setAvailableRooms(hotel.getAvailableRooms() - 1); // Reduce available rooms
        hotelRepository.save(hotel); // Save hotel updates

        return bookingRepository.save(booking);
    }

    public void cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        Hotel hotel = booking.getHotel();
        hotel.setAvailableRooms(hotel.getAvailableRooms() + 1); // Increase available rooms
        hotelRepository.save(hotel); // Save hotel updates

        bookingRepository.deleteById(bookingId);
    }
}
