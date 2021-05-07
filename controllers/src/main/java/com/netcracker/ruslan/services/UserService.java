package com.netcracker.denisik.services;

import com.netcracker.denisik.entity.User;
import com.netcracker.denisik.repository.UserRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@Setter(onMethod_ = @Autowired)
public class UserService {

    private UserRepository userRepository;

    @Transactional
    public void updateUser(Long id,String surname, String name, String country,  String phone){
        User user = userRepository.findOne(id);
        user.setName(name);
        user.setCountry(country);
        user.setPhone(phone);
        user.setSurname(surname);
        userRepository.save(user);
    }
}
