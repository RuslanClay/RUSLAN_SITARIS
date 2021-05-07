package com.netcracker.denisik.repository;

import com.netcracker.denisik.entity.Car;
import com.netcracker.denisik.entity.CarRent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRentRepository extends CrudRepository<CarRent,Long> {
}
