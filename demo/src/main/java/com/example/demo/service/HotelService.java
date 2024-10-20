package com.example.demo.service;

import com.example.demo.Repository.HotelRepository;
import com.example.demo.model.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    public Hotel createHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    public Hotel updateHotel(Long hotelId, Hotel hotel) {
        Optional<Hotel> existingHotelOpt = hotelRepository.findById(hotelId);
        if (existingHotelOpt.isPresent()) {
            Hotel existingHotel = existingHotelOpt.get();
            existingHotel.setName(hotel.getName());
            existingHotel.setLocation(hotel.getLocation());
            existingHotel.setDescription(hotel.getDescription());
            existingHotel.setAvailableRooms(hotel.getAvailableRooms());
            return hotelRepository.save(existingHotel);
        }
        throw new RuntimeException("Hotel not found");
    }

    public void deleteHotel(Long hotelId) {
        hotelRepository.deleteById(hotelId);
    }

    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    public Optional<Hotel> findHotelById(Long hotelId) {
        return hotelRepository.findById(hotelId);
    }
}
