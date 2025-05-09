package com.seleniumtraining.seleniumapp.services;

import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.seleniumtraining.seleniumapp.domain.Yritys;
import com.seleniumtraining.seleniumapp.domain.YritysDTO;
import com.seleniumtraining.seleniumapp.domain.YritysRepository;

@Service
public class ControllerMethods {

    @Autowired
    private YritysRepository yritysRepository;

    public ControllerMethods() {
    }

    public String getUsernameFromJwt() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        // tarkastetaan onko principal jwt-tyyppinen ja saadaan siitä username
        if (authentication.getPrincipal() instanceof Jwt jwt) {
            username = jwt.getClaimAsString("sub");
        }
        return username;
    }

    public String getPermissionsFromJwt() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String permissions = null;
        // tarkastetaan onko principal jwt-tyyppinen ja saadaan siitä permissions
        if (authentication.getPrincipal() instanceof Jwt jwt) {
            permissions = jwt.getClaimAsString("permissions");
        }
        return permissions;
    }

    public Page<YritysDTO> TryGetAllPublicYritykset(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return yritysRepository.findByUsername("publicTest", pageable)
                .map(yritys -> new YritysDTO(
                        yritys.getId(),
                        yritys.getYritysNimi(),
                        yritys.getToimitusjohtaja(),
                        yritys.getPostitoimipaikka(),
                        yritys.getPuhelinnumero(),
                        yritys.getSahkoposti(),
                        yritys.getToimialakuvaus(),
                        yritys.getInterestStatus()));

    }

    // luodaan metodi yritysten hakemiseen käyttäjän mukaan
    public List<YritysDTO> TryGetListOfYritykset(int page, int size) {
        Pageable pageable = PageRequest.of(page, size); // tai haluttu koko
        String username = getUsernameFromJwt();

        return yritysRepository.findByUsername(username, pageable)
                .map(yritys -> new YritysDTO(
                        yritys.getId(),
                        yritys.getYritysNimi(),
                        yritys.getToimitusjohtaja(),
                        yritys.getPostitoimipaikka(),
                        yritys.getPuhelinnumero(),
                        yritys.getSahkoposti(),
                        yritys.getToimialakuvaus(),
                        yritys.getInterestStatus()))
                .getContent();
    }

    // luodaan metodi yrityksen tilan päivittämiseen
    public ResponseEntity<?> TryUpdateYritys(Long id, YritysDTO dto) {
        String username = getUsernameFromJwt();
        Optional<Yritys> optionalYritys = yritysRepository.findById(id);

        if (optionalYritys.isPresent() && optionalYritys.get().getUsername().equals(username)) {
            Yritys yritys = optionalYritys.get();

            if (dto.getYritysNimi() != null) {
                yritys.setYritysNimi(dto.getYritysNimi());
            }
            if (dto.getToimitusjohtaja() != null) {
                yritys.setToimitusjohtaja(dto.getToimitusjohtaja());
            }
            if (dto.getPostitoimipaikka() != null) {
                yritys.setPostitoimipaikka(dto.getPostitoimipaikka());
            }
            if (dto.getPuhelinnumero() != null) {
                yritys.setPuhelinnumero(dto.getPuhelinnumero());
            }
            if (dto.getSahkoposti() != null) {
                yritys.setSahkoposti(dto.getSahkoposti());
            }
            if (dto.getToimialakuvaus() != null) {
                yritys.setToimialakuvaus(dto.getToimialakuvaus());
            }
            if (dto.getInterestStatus() != null) {
                yritys.setInterestStatus(dto.getInterestStatus());
            }
            yritysRepository.save(yritys);
            return ResponseEntity.ok("Yritys: " + yritys.getYritysNimi() + " updated successfully!");
        }
        return ResponseEntity.status(403).body("Unauthorized to update this company.");

    }

    // luodaan metodi yrityksen poistamiseen
    public ResponseEntity<?> TryDeleteYritys(Long id) {
        String username = getUsernameFromJwt();
        Optional<Yritys> optionalYritys = yritysRepository.findById(id);

        if (optionalYritys.isPresent() && optionalYritys.get().getUsername().equals(username)) {
            yritysRepository.deleteById(id);
            return ResponseEntity.ok("Yritys deleted successfully!");
        }
        return ResponseEntity.status(403).body("Unauthorized to delete this company.");
    }
}