package com.juancasterba.user_service.controller;

import com.juancasterba.user_service.entity.User;
import com.juancasterba.user_service.model.Bike;
import com.juancasterba.user_service.model.Car;
import com.juancasterba.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/{userId}/cars")
    public ResponseEntity<Car> saveCar(@PathVariable("userId") int userId, @RequestBody Car car) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        Car c = userService.saveCar(userId, car);
        return ResponseEntity.ok(c);
    }

    @PostMapping("/{userId}/bikes")
    public ResponseEntity<Bike> saveBike(@PathVariable("userId") int userId, @RequestBody Bike bike) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        Bike b = userService.saveBike(userId, bike);
        return ResponseEntity.ok(b);
    }

    @GetMapping("/{userId}/vehicles")
    public ResponseEntity<Map<String, Object>> getUserAndVehicles(@PathVariable("userId") int userId) {
        Map<String, Object> userAndVehicles = userService.getUserAndVehicles(userId);
        if (userAndVehicles == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userAndVehicles);
    }
}
