package Smallcare.Controllers;


import Smallcare.Models.Pet;
import Smallcare.Models.User;
import Smallcare.Services.PetService;
import Smallcare.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;


@Controller
public class GlobalPetController {

    @Autowired
    UserService userService;


    @Autowired
    PetService petService;

    @Value("${upload.path}")
    private String upload_path;

    private User getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken) {
            return null;
        }
        return ((User) auth.getPrincipal());
    }
    @GetMapping("/pets")
    public String pets(Model model) {
        User user = getCurrentUser();
        if(user == null){
            return "/index";
        }
        Iterable<Pet> petList = userService
                .findById(
                        (user).getId())
                .getPetList();
        if (petList != null) {
            model.addAttribute("pets", petList);
        }
        return "pets";
    }

    @GetMapping("/pets/{id}")
    public String pet(Model model, @PathVariable Long id) {
        Pet pet = petService.findById(id).orElse(null);
        if (pet != null) {
            model.addAttribute("pet", pet);
            User user = getCurrentUser();
            if(user == null){
                return "/pet";
            }
            if (user.getId().equals(pet.getUser().getId())){
                model.addAttribute("owner", true);
            }
            else{
                model.addAttribute("owner", false);
            }
        }
        return "/pet";
    }

}