package com.animo.animorise.controller;

import com.animo.animorise.dto.RendezVousDto;
import com.animo.animorise.dto.RendezVousRequest;
import com.animo.animorise.service.RendezVousService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/rendezvous")
@RequiredArgsConstructor
public class RendezVousController {
    private final RendezVousService rendezVousService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<RendezVousDto> createRendezVous(@RequestBody RendezVousRequest request) {
        return ResponseEntity.ok(rendezVousService.createRendezVous(
                request.getAnimalId(),
                request.getDateTime()
        ));
    }

    @PreAuthorize("hasRole('VETERINERE')")
    @PutMapping("/{id}/accept")
    public ResponseEntity<RendezVousDto> acceptRendezVous(@PathVariable Long id) {
        return ResponseEntity.ok(rendezVousService.acceptRendezVous(id));
    }

    @PreAuthorize("hasRole('USER') or hasRole('VETERINERE')")
    @PutMapping("/{id}/cancel")
    public ResponseEntity<RendezVousDto> cancelRendezVous(@PathVariable Long id) {
        return ResponseEntity.ok(rendezVousService.cancelRendezVous(id));
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/animal/{animalId}")
    public ResponseEntity<List<RendezVousDto>> getRendezVousByAnimal(@PathVariable Long animalId) {
        return ResponseEntity.ok(rendezVousService.getRendezVousByAnimal(animalId));
    }
}