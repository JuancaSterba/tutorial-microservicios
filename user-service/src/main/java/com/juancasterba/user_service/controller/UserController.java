package com.juancasterba.user_service.controller;

import com.juancasterba.user_service.entity.User;
import com.juancasterba.user_service.model.Bike;
import com.juancasterba.user_service.model.Car;
import com.juancasterba.user_service.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") int id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        User u = userService.saveUser(user);
        return ResponseEntity.ok(u);
    }

    @CircuitBreaker(name = "carsCB", fallbackMethod = "fallbackGetCars")
    @GetMapping("/{userId}/cars")
    public ResponseEntity<List<Car>> getCars(@PathVariable("userId") int userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        List<Car> cars = userService.getCars(userId);
        if (cars.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cars);
    }

    @CircuitBreaker(name = "carsCB", fallbackMethod = "fallbackSaveCar")
    @PostMapping("/{userId}/cars")
    public ResponseEntity<Car> saveCar(@PathVariable("userId") int userId, @RequestBody Car car) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        Car c = userService.saveCar(userId, car);
        return ResponseEntity.ok(c);
    }

    @CircuitBreaker(name = "bikesCB", fallbackMethod = "fallbackGetBikes")
    @GetMapping("/{userId}/bikes")
    public ResponseEntity<List<Bike>> getBikes(@PathVariable("userId") int userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        List<Bike> bikes = userService.getBikes(userId);
        if (bikes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(bikes);
    }

    @CircuitBreaker(name = "bikesCB", fallbackMethod = "fallbackSaveBike")
    @PostMapping("/{userId}/bikes")
    public ResponseEntity<Bike> saveBike(@PathVariable("userId") int userId, @RequestBody Bike bike) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        Bike b = userService.saveBike(userId, bike);
        return ResponseEntity.ok(b);
    }

    @CircuitBreaker(name = "vehiclesCB", fallbackMethod = "fallbackGetUserAndVehicles")
    @GetMapping("/{userId}/vehicles")
    public ResponseEntity<Map<String, Object>> getUserAndVehicles(@PathVariable("userId") int userId) {
        Map<String, Object> userAndVehicles = userService.getUserAndVehicles(userId);
        if (userAndVehicles == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userAndVehicles);
    }

    private ResponseEntity<List<Car>> fallbackGetCars(@PathVariable int userId, RuntimeException e) {
        return new ResponseEntity("El usuario " + userId + " tiene los coches en el taller", HttpStatus.OK);
    }

    private ResponseEntity<Car> fallbackSaveCar(@PathVariable int userId, @RequestBody Car car, RuntimeException e) {
        return new ResponseEntity("El usuario " + userId + " no tiene dinero para coches", HttpStatus.OK);
    }

    private ResponseEntity<List<Bike>> fallbackGetBikes(@PathVariable int userId, RuntimeException e) {
        return new ResponseEntity("El usuario " + userId + " tiene las motos en el taller", HttpStatus.OK);
    }

    private ResponseEntity<Bike> fallbackSaveBike(@PathVariable int userId, @RequestBody Bike bike, RuntimeException e) {
        return new ResponseEntity("El usuario " + userId + " no tiene dinero para motos", HttpStatus.OK);
    }

    private ResponseEntity<Map<String, Object>> fallbackGetUserAndVehicles(@PathVariable int userId, RuntimeException e) {
        return new ResponseEntity("El usuario " + userId + " tiene los vehiculos en el taller", HttpStatus.OK);
    }
}
