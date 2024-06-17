package com.juancasterba.bike_service.controller;

import com.juancasterba.bike_service.entity.Bike;
import com.juancasterba.bike_service.service.BikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bike")
public class BikeController {

    @Autowired
    private BikeService bikeService;

    @GetMapping("/list")
    public ResponseEntity<List<Bike>> getAllBikes() {
        List<Bike> bikes = bikeService.getAllBikes();
        if (bikes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(bikes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bike> getBikeById(@PathVariable("id") int id) {
        Bike bike = bikeService.getBikeById(id);
        if (bike == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bike);
    }

    @PostMapping
    public ResponseEntity<Bike> saveBike(@RequestBody Bike bike) {
        Bike b = bikeService.saveBike(bike);
        return ResponseEntity.ok(b);
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<Bike>> getByUserId(@PathVariable("userId") int userId) {
        List<Bike> bikes = bikeService.byUserId(userId);
        if (bikes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(bikes);
    }
}
