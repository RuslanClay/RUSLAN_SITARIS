package com.netcracker.denisik.controllers;

import com.netcracker.denisik.entity.*;
import com.netcracker.denisik.repository.*;
import com.netcracker.denisik.services.DBFileService;
import com.netcracker.denisik.services.RoomService;
import com.netcracker.denisik.services.ServicesService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
@Setter(onMethod_ = @Autowired)
public class AdminController {

    private CarRepository carRepository;
    private ServicesService servicesService;
    private RoomService roomService;
    private TypeRoomRepository typeRoomRepository;
    private DBFileRepository fileRepository;
    private DBFileService dbFileService;
    private FacilitiesRepository facilitiesRepository;
    private RoomRepository roomRepository;

    @GetMapping("/addEntity")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addEntity(){
        return "admin/addEntity";
    }

    @PostMapping("/addCar")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addCar(@RequestParam String mark, @RequestParam Integer price){
        Car car = new Car();
        car.setMark(mark);
        car.setPrice(price);
        car.setCarStatus(CarStatus.FREE);
        carRepository.save(car);
        return "redirect:/admin/addEntity";
    }

    @PostMapping(value = "/addService")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addService(@RequestParam String name, @RequestParam Integer price){
        servicesService.addService(name,price);
        return "redirect:/admin/addEntity";
    }

    @PostMapping("/addRoom")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addRoom(@RequestParam Integer costDay,@RequestParam Integer numSeats, @RequestParam Long typeId){
        TypeRoom typeRoom = typeRoomRepository.findOne(typeId);
        roomService.addRoom(costDay, numSeats, typeRoom);
        return "redirect:/admin/addEntity";
    }

    @PostMapping("/addTypeRoom")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addTypeRoom(@RequestParam String name, @RequestParam MultipartFile[] picture) throws Exception {
        if(!typeRoomRepository.existsByTypeRoom(name)){
            TypeRoom typeRoom = new TypeRoom();
            typeRoom.setTypeRoom(name);
            Set<DBFile> files = new HashSet<>();
            for (MultipartFile pic: picture
                 ) {
                DBFile file = new DBFile();
                file.setData(pic.getBytes());
                file.setFileType(pic.getContentType());
                file.setFileName(pic.getOriginalFilename());
                dbFileService.save(file);
                files.add(file);
            }
            typeRoom.setPictures(files);
            typeRoomRepository.save(typeRoom);

        } else {
            throw new Exception("TypeRoom with such name already exists");
        }
        return "admin/addEntity";
    }

    @PostMapping("/addFacility")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addFacility(@RequestParam String name){
        Facilities facilities = new Facilities();
        facilities.setFacility(name);
        facilitiesRepository.save(facilities);
        return "admin/addEntity";
    }

    @PostMapping("/bindFacilityAndType")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String bindFacilities(@RequestParam Long fId,@RequestParam Long tId) throws Exception {
        Facilities facilities = facilitiesRepository.findOne(fId);
        TypeRoom typeRoom = typeRoomRepository.findOne(tId);
        if(facilities != null && typeRoom != null){
            facilities.getTypeRoomSet().add(typeRoom);
            typeRoom.getFacilitiesSet().add(facilities);
            facilitiesRepository.save(facilities);
            typeRoomRepository.save(typeRoom);
        } else {
            throw new Exception("not found");
        }
        return "admin/addEntity";
    }

    @PostMapping("/deleteRoom")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteRoom(@RequestParam Long roomId) {
        Room room = roomRepository.findOne(roomId);
        room.setIsActive(false);
        roomRepository.save(room);
        return "redirect:/hotel";
    }
}

