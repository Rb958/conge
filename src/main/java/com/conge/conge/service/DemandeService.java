package com.conge.conge.service;

import com.conge.conge.entity.Demande;
import com.conge.conge.entity.enums.DemandeStatut;
import com.conge.conge.repository.DemandeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemandeService {
    private final DemandeRepository demandeRepository;

    public DemandeService(DemandeRepository demandeRepository) {
        this.demandeRepository = demandeRepository;
    }

    public Demande save(Demande demande){
        return demandeRepository.save(demande);
    }

    public Demande findOne(long id){
        return demandeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Demande introuvable"));
    }

    public Demande updateDemande(long id, Demande demande){
        if (demande.getDateDebut().isBefore(demande.getDateFin())){
            throw new RuntimeException("Periode invalide: la date de debut doit etre inferieur a la date de fin");
        }
        Demande tmp = demandeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Demande introuvable"));
        tmp.setEmployeur(demande.getEmployeur());
        tmp.setDateDebut(demande.getDateDebut());
        tmp.setDateFin(demande.getDateFin());
        tmp.setMessage(demande.getMessage());
        tmp.setObjet(demande.getObjet());
        return demandeRepository.save(demande);
    }

    public void delete(long id){
        Demande demande = demandeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Demande introuvable"));
        demandeRepository.delete(demande);
    }

    public void valider(long id){
        Demande tmp = demandeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Demande introuvable"));
        tmp.setStatut(DemandeStatut.VALIDE);
        demandeRepository.save(tmp);
    }

    public void rejeter(long id){
        Demande tmp = demandeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Demande introuvable"));
        tmp.setStatut(DemandeStatut.REJETE);
        demandeRepository.save(tmp);
    }

    public List<Demande> list(){
        return demandeRepository.findAll();
    }
}
