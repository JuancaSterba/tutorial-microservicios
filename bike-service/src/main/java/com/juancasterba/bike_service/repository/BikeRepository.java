package com.juancasterba.bike_service.repository;

import com.juancasterba.bike_service.entity.Bike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BikeRepository extends JpaRepository<Bike, Integer> {
    List<Bike> findByUserId(int userId);
}
