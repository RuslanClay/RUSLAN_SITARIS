package com.netcracker.denisik.repository;

import com.netcracker.denisik.entity.Car;
import com.netcracker.denisik.entity.Services;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicesRepository extends CrudRepository<Services,Long> {
}
