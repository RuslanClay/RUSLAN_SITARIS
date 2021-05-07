package com.netcracker.denisik.services;

import com.netcracker.denisik.entity.Car;
import com.netcracker.denisik.entity.CarRent;
import com.netcracker.denisik.entity.CarStatus;
import com.netcracker.denisik.entity.Residence;
import com.netcracker.denisik.repository.CarRentRepository;
import com.netcracker.denisik.repository.CarRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Setter(onMethod_ = @Autowired)
public class CarService {

    private CarRepository carRepository;
    private CarRentRepository carRentRepository;

    @Transactional
    public void addCarRent(Residence residence, Long carId){
        Car car = carRepository.findOne(carId);
        car.setCarStatus(CarStatus.BUSY);
        CarRent carRent = new CarRent();
        carRent.setCar(car);
        carRent.setResidence(residence);
        carRentRepository.save(carRent);
    }
}
