package com.conge.conge.controller;

import com.conge.conge.entity.Employe;
import com.conge.conge.model.ChangePassword;
import com.conge.conge.service.EmployeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employe")
public class EmployeController {
    private final EmployeService employeService;

    public EmployeController(EmployeService employeService) {
        this.employeService = employeService;
    }

    @PostMapping
    public ResponseEntity<Employe> create(Employe employe){
        return ResponseEntity.ok(
                employeService.save(employe)
        );
    }

    @PutMapping("{id}")
    public ResponseEntity<Employe> update(@PathVariable long id, @RequestBody Employe employe){
        return ResponseEntity.ok(
                employeService.update(id, employe)
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<Employe> findOne(@PathVariable long id){
        return ResponseEntity.ok(
                employeService.findOne(id)
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        employeService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<List<Employe>> listAll(){
        return ResponseEntity.ok(employeService.listAll());
    }

    @PutMapping
    public ResponseEntity<Void> change(@RequestBody ChangePassword changePassword){
        employeService.changePassword(changePassword.getUsername(), changePassword.getOldPassword(), changePassword.getNewPassword());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
