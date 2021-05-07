package com.netcracker.denisik.controllers;

import com.netcracker.denisik.entity.Residence;
import com.netcracker.denisik.entity.User;
import com.netcracker.denisik.repository.ResidenceRepository;
import com.netcracker.denisik.repository.UserRepository;
import com.netcracker.denisik.services.UserService;
import com.netcracker.denisik.userDetails.UserDetailsImpl;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/personalArea")
@Setter(onMethod_ = @Autowired)
public class UserController {

    private UserRepository userRepository;
    private ResidenceRepository residenceRepository;
    private UserService userService;

    @GetMapping()
    public String personalArea(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model){

        Optional<Residence> activeByUser = residenceRepository.findActiveByUser(userDetails.getUser());
        if (activeByUser.isPresent()){
            Residence o = activeByUser.get();
            model.addAttribute("activeResidence", o);
        } else {
            model.addAttribute("activeResidence", null);
        }
        model.addAttribute("residences",residenceRepository.findAllByUser(userDetails.getUser()));
        model.addAttribute("user", userRepository.findOne(userDetails.getId()));
        return "personalArea";
    }

    @PostMapping("/update")
    public String update(@AuthenticationPrincipal UserDetailsImpl userDetails,
                         @RequestParam String surname, @RequestParam String name,
                         @RequestParam String country, @RequestParam String phone){
        userService.updateUser(userDetails.getId(),surname,name,country,phone);
        return "redirect:/personalArea";
    }
}
