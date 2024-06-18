package com.conge.conge.service;

import com.conge.conge.entity.Employeur;
import com.conge.conge.repository.EmployeurRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeurService {
    private final EmployeurRepository employeurRepository;
    private final PasswordEncoder passwordEncoder;

    public EmployeurService(EmployeurRepository employeurRepository, PasswordEncoder passwordEncoder) {
        this.employeurRepository = employeurRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Employeur saveEmployeur(Employeur employeur){
        employeur.setMotDePasse(passwordEncoder.encode(employeur.getMotDePasse()));
        return employeurRepository.save(employeur);
    }

    public Employeur updateEmployeur(long id, Employeur employeur){
        Employeur tmpEmployeur = employeurRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Employeur introuvable"));

        tmpEmployeur.setMatricule(employeur.getMatricule());
        tmpEmployeur.setNom(employeur.getNom());
        tmpEmployeur.setPrenom(employeur.getPrenom());
        return employeurRepository.save(tmpEmployeur);
    }

    public void delete(long id){
        Employeur tmpEmployeur = employeurRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Employeur introuvable"));
        employeurRepository.delete(tmpEmployeur);
    }

    public List<Employeur> list(){
        return employeurRepository.findAll();
    }

    public Employeur findOne(long id){
        return employeurRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Employeur introuvable"));
    }
}
