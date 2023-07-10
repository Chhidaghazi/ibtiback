package com.sofrecom.cobli.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sofrecom.cobli.models.Prestation;
import com.sofrecom.cobli.repository.PrestationRepository;

@RestController
@RequestMapping("/api/prestations")
public class PrestationController {
	
	@Autowired
    PrestationRepository prestationRepo;
	@GetMapping("/getPrestations/{typePrestation}")
	  public ResponseEntity<Prestation> getPrestationByNom(@PathVariable("typePrestation") String typePrestation) {
	    try {
	      Optional<Prestation> prestation = prestationRepo.findByNomPrestation(typePrestation);
	      if (prestation.isPresent()) {
	        return ResponseEntity.ok(prestation.get());
	      } else {
	        return ResponseEntity.notFound().build();
	      }
	    } catch (Exception e) {
	      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	  }

}
