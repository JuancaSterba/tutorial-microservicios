package com.juancasterba.car_service.controller;

import com.juancasterba.car_service.entity.Car;
import com.juancasterba.car_service.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/car")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping("/list")
    public ResponseEntity<List<Car>> getAllCars() {
        List<Car> cars = carService.getAllCars();
        if (cars.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable("id") int id) {
        Car car = carService.getCarById(id);
        if (car == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(car);
    }

    @PostMapping
    public ResponseEntity<Car> saveCar(@RequestBody Car user) {
        Car c = carService.saveCar(user);
        return ResponseEntity.ok(c);
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<Car>> getCarsByUserId(@PathVariable("userId") int userId) {
        List<Car> cars = carService.byUserId(userId);
        if (cars.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cars);
    }
}
