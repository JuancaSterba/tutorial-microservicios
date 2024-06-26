package com.juancasterba.user_service.service;

import com.juancasterba.user_service.entity.User;
import com.juancasterba.user_service.feignclients.BikeFeignClient;
import com.juancasterba.user_service.feignclients.CarFeignClient;
import com.juancasterba.user_service.model.Bike;
import com.juancasterba.user_service.model.Car;
import com.juancasterba.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CarFeignClient carFeignClient;

    @Autowired
    private BikeFeignClient bikeFeignClient;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<Car> getCars(int userId){
        return restTemplate.getForObject("http://localhost:8082/cars/by-user/"+userId, List.class);
    }

    public List<Bike> getBikes(int userId){
        return restTemplate.getForObject("http://localhost:8083/bikes/by-user/"+userId, List.class);
    }

    public Car saveCar(int userId, Car car) {
        car.setUserId(userId);
        return carFeignClient.save(car);
    }

    public Bike saveBike(int userId, Bike bike) {
        bike.setUserId(userId);
        return bikeFeignClient.save(bike);
    }

    public Map<String, Object> getUserAndVehicles(int userId) {
        User user = getUserById(userId);
        if (user == null) {
            return null;
        }
        List<Car> cars = carFeignClient.getCars(userId);
        List<Bike> bikes = bikeFeignClient.getBikes(userId);
        return Map.of("user", user, "cars", cars, "bikes", bikes);
    }

}
