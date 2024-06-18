package com.conge.conge.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.conge.conge.entity.Employe;
import com.conge.conge.model.AuthResponse;
import com.conge.conge.repository.EmployeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class EmployeService {
    private final EmployeRepository employeRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public EmployeService(EmployeRepository employeRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.employeRepository = employeRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public Employe save(Employe employe){
        employe.setMotDePasse(passwordEncoder.encode(employe.getMotDePasse()));
        return employeRepository.save(employe);
    }

    public Employe findOne(long id){
        return employeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Employe introuvable"));
    }

    public Employe update(long id, Employe employe) {
        Employe tmp = employeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Employe introuvable"));
        tmp.setNom(employe.getNom());
        tmp.setMatricule(employe.getMatricule());
        tmp.setPrenom(employe.getPrenom());
        tmp.setSexe(employe.getSexe());
        return employeRepository.save(tmp);
    }

    public void delete(long id){
        Employe tmp = employeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Employe introuvable"));
        employeRepository.delete(tmp);
    }

    public List<Employe> listAll(){
        return employeRepository.findAll();
    }

    public void changePassword(String username, String oldPassword, String newPassword){
        Employe tmp = employeRepository.findByNomUtilisateur(username).orElseThrow(() -> new EntityNotFoundException("Employe introuvable"));
        if (!passwordEncoder.matches(oldPassword, tmp.getPassword())){
            throw new RuntimeException("Mot de passe incorrecte");
        }
        tmp.setMotDePasse(passwordEncoder.encode(newPassword));
        employeRepository.save(tmp);
    }

    public AuthResponse loginEmploye(String username, String password){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        Employe employe = (Employe) authentication.getPrincipal();
        String token = JWT.create().withSubject(username)
                .withClaim("role", employe.getAuthorities().stream().toList().get(0).getAuthority())
                .withClaim("id", employe.getId())
                .withClaim("nom", employe.getNom())
                .withClaim("prenom", employe.getPrenom())
                .withExpiresAt(Instant.now().plus(24, TimeUnit.HOURS.toChronoUnit()))
                .sign(Algorithm.HMAC512("s54qZ0edSOe4qOutaa1jyFHZPz38E-Zdfe4sSWj5hSDUvff_O1o2dl2l5I5lHt58Qu-Lkpe1nNvm0jkyiygBKQ"));
        return new AuthResponse(token);
    }
}
