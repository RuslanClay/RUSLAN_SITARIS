package com.netcracker.denisik.controllers;

import com.netcracker.denisik.entity.CarStatus;
import com.netcracker.denisik.repository.CarRepository;
import com.netcracker.denisik.services.CarService;
import com.netcracker.denisik.services.ResidenceService;
import com.netcracker.denisik.services.ServicesService;
import com.netcracker.denisik.userDetails.UserDetailsImpl;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@Setter(onMethod_ = @Autowired)
public class ResidenceController {

    private ResidenceService residenceService;
    private ServicesService servicesService;
    private CarRepository carRepository;
    private CarService carService;

    @RequestMapping(value = "/getActiveResidence")
    public String getActiveResidence(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model){
        try {
            model.addAttribute("residence",residenceService.findActiveResidence(userDetails.getUser()));
            model.addAttribute("services",servicesService.findServices());
            model.addAttribute("cars",carRepository.findAllByCarStatus(CarStatus.FREE));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "resultOfBooking";
    }

    @PostMapping(value = "/addService")
    public String addService(@RequestParam Map<String,String> allRequestParams, @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        for (Map.Entry<String, String> request: allRequestParams.entrySet()
             ) {
            if (request.getKey().contains("serviceId")){
                residenceService.addService(Long.valueOf(request.getValue()),userDetails.getUser());
            }
            if (request.getKey().contains("carId")){
                carService.addCarRent(residenceService.findActiveResidence(userDetails.getUser()),Long.valueOf(request.getValue()));
            }
        }
        return "redirect:/hotel";
    }

    @PostMapping("/residence/checkOut")
    public String checkOut(@AuthenticationPrincipal UserDetailsImpl userDetails){
        try {
            residenceService.checkOut(userDetails);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/personalArea";
    }
}
