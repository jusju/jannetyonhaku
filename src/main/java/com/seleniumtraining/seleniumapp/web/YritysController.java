package com.seleniumtraining.seleniumapp.web;

import org.springframework.web.bind.annotation.RestController;

import com.seleniumtraining.seleniumapp.domain.YritysDTO;
import com.seleniumtraining.seleniumapp.services.ControllerMethods;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@RequestMapping("api")
@RestController
public class YritysController {

    private ControllerMethods controllerMethods;

    public YritysController(ControllerMethods controllerMethods) {
        this.controllerMethods = controllerMethods;
    }

    @GetMapping("/public")
    public Page<YritysDTO> testPublic(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return controllerMethods.TryGetAllPublicYritykset(page, size);
    }

    @GetMapping("/userDetails")
    public String getUserDetails() {
        String username = controllerMethods.getUsernameFromJwt();
        String permissions = controllerMethods.getPermissionsFromJwt();
        System.out.println("Username: " + username);
        System.out.println("Permissions: " + permissions);
        return "User details retrieved successfully!";
    }

    @GetMapping("/secret")
    public String testAuthorization() {
        return "Authorization test successful!";
    }

    @GetMapping("/yritykset")
    public List<YritysDTO> getUserYritykset(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return controllerMethods.TryGetListOfYritykset(page, size);
    }

    @PatchMapping("/editYritys/{id}")
    public ResponseEntity<?> editYritysDetails(@PathVariable Long id, @RequestBody YritysDTO yritysDTO) {
        return controllerMethods.TryUpdateYritys(id, yritysDTO);
    }

    @DeleteMapping("/deleteYritys/{id}")
    public ResponseEntity<?> deleteYritys(@PathVariable Long id) {
        return controllerMethods.TryDeleteYritys(id);
    }

}
