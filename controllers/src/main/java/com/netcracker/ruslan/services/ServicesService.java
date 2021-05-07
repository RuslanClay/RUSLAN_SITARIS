package com.netcracker.denisik.services;

import com.netcracker.denisik.entity.Services;
import com.netcracker.denisik.repository.ServicesRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Setter(onMethod_ = @Autowired)
public class ServicesService {

    private ServicesRepository servicesRepository;

    public Iterable<Services> findServices(){
        return servicesRepository.findAll();
    }
    @Transactional
    public void addService(String name, Integer price){
        Services services = new Services();
        services.setServiceName(name);
        services.setPrice(price);
        servicesRepository.save(services);
    }

}
