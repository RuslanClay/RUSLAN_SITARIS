package com.netcracker.denisik.controllers;

import com.netcracker.denisik.entity.*;
import com.netcracker.denisik.repository.FacilitiesRepository;
import com.netcracker.denisik.repository.ResidenceRepository;
import com.netcracker.denisik.repository.TypeRoomRepository;
import com.netcracker.denisik.services.RoomService;
import com.netcracker.denisik.userDetails.UserDetailsImpl;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.List;

@Controller
@Setter(onMethod_ = @Autowired)
public class RoomController {

    private RoomService roomService;
    private TypeRoomRepository typeRoomRepository;
    private ResidenceRepository residenceRepository;
    private FacilitiesRepository facilitiesRepository;

    @GetMapping(value = "/getFilteredRooms")
    public String getFilteredRooms(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam Integer dayCost, @RequestParam Integer seatsAmount,
                                   @RequestParam String checkInDate, @RequestParam String checkOutDate, Model model){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate checkIn = LocalDate.parse(checkInDate,formatter);
        LocalDate checkOut = LocalDate.parse(checkOutDate,formatter);
        model.addAttribute("rooms",roomService.findFilteredRoom(dayCost, seatsAmount,checkIn,checkOut));
        model.addAttribute("checkIn",checkIn);
        model.addAttribute("checkOut",checkOut);
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        boolean admin = authorities.contains(new SimpleGrantedAuthority("ADMIN"));
        model.addAttribute("userAuthorities", admin);
        model.addAttribute("days", ChronoUnit.DAYS.between(checkIn,checkOut));
        return "ResultSearch";
    }

    @PostMapping(value = "/bookRoom")
    public String bookRoom(@RequestParam Long roomId, @AuthenticationPrincipal UserDetailsImpl user,
                           @RequestParam String checkIn, @RequestParam String checkOut) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate checkInDate = LocalDate.parse(checkIn,formatter);
        LocalDate checkOutDate = LocalDate.parse(checkOut,formatter);
        if (!residenceRepository.existsByUserAndStatus(user.getUser(), Status.ACTIVE)){
            roomService.bookRoom(user.getUser(),roomId,checkInDate,checkOutDate);
        } else {
            throw new Exception("user already has active residence");
        }
        return "redirect:/getActiveResidence";
    }

    @GetMapping("/facility")
    public String getFacility(@RequestParam Long typeRoom,Model model) throws UnsupportedEncodingException {
        TypeRoom typeRoom1 = typeRoomRepository.findOne(typeRoom);
        model.addAttribute("facility", typeRoom1.getFacilitiesSet());
        model.addAttribute("typeRoom", typeRoom1);
        List<String> list = new ArrayList<>();
        for (DBFile pic: typeRoom1.getPictures()
             ) {
            byte[] encode = Base64.getEncoder().encode(pic.getData());
            String str = new String(encode,"UTF-8");
            list.add(str);
        }
        model.addAttribute("pics", list);
//        model.addAttribute("image", new String(encode, "UTF-8"));
        return "RoomTypeInfo";
    }
}
