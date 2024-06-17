package com.juancasterba.car_service.service;

import com.juancasterba.car_service.entity.Car;
import com.juancasterba.car_service.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Car getCarById(int id) {
        return carRepository.findById(id).orElse(null);
    }

    public Car saveCar(Car car) {
        return carRepository.save(car);
    }

    public List<Car> byUserId(int userId) {
        return carRepository.findByUserId(userId);
    }
}
