package com.conge.conge.controller;

import com.conge.conge.entity.Employeur;
import com.conge.conge.service.EmployeurService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employeur")
public class EmployeurController {
    private final EmployeurService employeurService;

    public EmployeurController(EmployeurService employeService) {
        this.employeurService = employeService;
    }

    @PostMapping
    public ResponseEntity<Employeur> create(Employeur employe){
        return ResponseEntity.ok(
                employeurService.saveEmployeur(employe)
        );
    }

    @PutMapping("{id}")
    public ResponseEntity<Employeur> update(@PathVariable long id, @RequestBody Employeur employe){
        return ResponseEntity.ok(
                employeurService.updateEmployeur(id, employe)
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<Employeur> findOne(@PathVariable long id){
        return ResponseEntity.ok(
                employeurService.findOne(id)
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        employeurService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<List<Employeur>> listAll(){
        return ResponseEntity.ok(employeurService.list());
    }
}
