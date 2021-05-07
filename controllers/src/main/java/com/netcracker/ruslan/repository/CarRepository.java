package com.netcracker.denisik.repository;

import com.netcracker.denisik.entity.Car;
import com.netcracker.denisik.entity.CarStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends CrudRepository<Car,Long> {

    List<Car> findAllByCarStatus(CarStatus carStatus);
}
