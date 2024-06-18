package com.conge.conge.controller;

import com.conge.conge.entity.Demande;
import com.conge.conge.service.DemandeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/demande")
public class DemandeController {
    private final DemandeService demandeService;

    public DemandeController(DemandeService employeService) {
        this.demandeService = employeService;
    }

    @PostMapping
    public ResponseEntity<Demande> create(Demande employe){
        return ResponseEntity.ok(
                demandeService.save(employe)
        );
    }

    @PutMapping("{id}")
    public ResponseEntity<Demande> update(@PathVariable long id, @RequestBody Demande employe){
        return ResponseEntity.ok(
                demandeService.updateDemande(id, employe)
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<Demande> findOne(@PathVariable long id){
        return ResponseEntity.ok(
                demandeService.findOne(id)
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        demandeService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<List<Demande>> listAll(){
        return ResponseEntity.ok(demandeService.list());
    }
}
