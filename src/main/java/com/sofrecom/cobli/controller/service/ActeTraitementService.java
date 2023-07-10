package com.sofrecom.cobli.controller.service;

import java.util.ArrayList;
import java.util.List;

import com.sofrecom.cobli.models.Acte_traitement;
import com.sofrecom.cobli.models.Collaborateur;
import com.sofrecom.cobli.repository.CollaborateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sofrecom.cobli.repository.Acte_traitementRepository;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class ActeTraitementService {
	
	@Autowired
	Acte_traitementRepository acteTraitementRepository;
	@Autowired
	CollaborateurRepository collaborateurRepository;
	
	
	public List<Object[]> getGroupedActeTraitementByAffectation() {
		return acteTraitementRepository.countByAffectation(); }

	public List<String> getCUIDsMemeEquipe(int idEquipe) {
		List<Collaborateur> collaborateurs = collaborateurRepository.findByEquipeIdEquipe(idEquipe);
		List<String> cuids = new ArrayList<>();
		for (Collaborateur collaborateur : collaborateurs) {
			cuids.add(collaborateur.getCUID());
		}
		return cuids;
	}


	public List<Acte_traitement> getActeTraitementByAffectationList(List<String> cuids) {
		return acteTraitementRepository.findByAffectationIn(cuids);
	}
	public List<Acte_traitement> getActeTraitementByAffectationListIN(List<String> cuids , int idPrestation ,  String codeTarif) {
		return acteTraitementRepository.findPrestTarifAndAffectationIn(  idPrestation ,codeTarif, cuids);
	}
}
