package com.juancasterba.user_service.feignclients;

import com.juancasterba.user_service.model.Bike;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name="bike-service", url="http://localhost:8083/bike")
public interface BikeFeignClient {

    @PostMapping
    Bike save(@RequestBody Bike bike);

    @GetMapping("/by-user/{userId}")
    List<Bike> getBikes(@PathVariable("userId") int userId);
}
