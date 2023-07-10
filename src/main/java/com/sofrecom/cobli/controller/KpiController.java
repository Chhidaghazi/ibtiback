package com.sofrecom.cobli.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

import com.sofrecom.cobli.models.Tarification;
import com.sofrecom.cobli.repository.TarificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sofrecom.cobli.controller.service.ActeTraitementService;
import com.sofrecom.cobli.controller.service.AttachementsService;
import com.sofrecom.cobli.controller.service.KpiService;
import com.sofrecom.cobli.models.Acte_traitement;
import com.sofrecom.cobli.models.Attachements;
import com.sofrecom.cobli.models.Prestation;
import com.sofrecom.cobli.repository.Acte_traitementRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/kpi")
public class KpiController {

	@Autowired
	ActeTraitementService acteTraitementService;

	@Autowired
	TarificationRepository tarificationRepo;

	@Autowired
	KpiService kpiService;

	@Autowired
	private Acte_traitementRepository acteTraitementRepo;


	@GetMapping("/group-by-affectation")
	public ResponseEntity<List<Object[]>> getGroupedByAffectation() {
		List<Object[]> groupedResults = acteTraitementService.getGroupedActeTraitementByAffectation();
		return ResponseEntity.ok(groupedResults);
	}


	/*@PostMapping("/Staffing")
	public Double getSommeDuree( @RequestBody List<Attachements> actes  )
 {
		int sommeDuree=0;
		for(Attachements list :actes ){
			sommeDuree =+list.getDuree() ;
		}
		Double Staffing= kpiService.CalculStaffing(Double.valueOf(sommeDuree));
	    return  Staffing;
		}*/


	@GetMapping("/Staffingdebfin")
	public Double getSttaffingDebFin(@RequestParam String date_Debut_s, @RequestParam String date_Fin_s) throws ParseException {
		List<Attachements> response = new ArrayList<>();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

		Date date_Debut = dateFormat.parse(date_Debut_s);
		Date date_Fin = dateFormat.parse(date_Fin_s);
		System.out.println("Date début : " + date_Debut);
		System.out.println("Date fin : " + date_Fin);

		List<Acte_traitement> actes = acteTraitementRepo.findActeAttach(date_Debut, date_Fin);


		int sommeDuree=0;
		for(Acte_traitement list :actes ){

			sommeDuree =+list.getDuree() ;
		}
		Double Staffing= kpiService.CalculStaffing(Double.valueOf(sommeDuree));

		return  Staffing;
	}

	@PostMapping("/Staffing")
	public Double getSttaffing(@RequestBody List<Acte_traitement> actes) throws ParseException {

		int sommeDuree=0;
		for(Acte_traitement list :actes ){

			sommeDuree =+list.getDuree() ;
		}
		Double Staffing= kpiService.CalculStaffing(Double.valueOf(sommeDuree));

		return  Staffing;
	}


	@GetMapping("/CTJdebfin")
	public Double CTJdebfin(@RequestParam String date_Debut_s, @RequestParam String date_Fin_s) throws ParseException {
		List<Attachements> response = new ArrayList<>();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		Date date_Debut = dateFormat.parse(date_Debut_s);
		Date date_Fin = dateFormat.parse(date_Fin_s);
		System.out.println("Date début : " + date_Debut);
		System.out.println("Date fin : " + date_Fin);

		List<Acte_traitement> actes = acteTraitementRepo.findActeAttach(date_Debut, date_Fin);


		int sommeDuree=0;
		for(Acte_traitement list :actes ){

			sommeDuree =+list.getDuree() ;
		}
		long nbJr= kpiService.getDaysBetween(date_Debut_s, date_Fin_s);

		Double Staffing= kpiService.CalculStaffing(Double.valueOf(sommeDuree));
		Double CTJ = kpiService.CalculCTJ(actes, (int) nbJr, Staffing);

		return  CTJ;
	}

	@PostMapping("/CTJ")
	public Double CTJ(@RequestBody List<Acte_traitement> actes) throws ParseException {

		int sommeDuree=0;
		for(Acte_traitement list :actes ){

			sommeDuree =+list.getDuree() ;
		}
	//	long nbJr= kpiService.getDaysBetween(date_Debut_s, date_Fin_s);

		Double Staffing= kpiService.CalculStaffing(Double.valueOf(sommeDuree));
		Double CTJ = kpiService.CalculCTJ(actes, (int) 22, Staffing);

		return  CTJ;
	}

	@GetMapping("/Capacitedebfin")
	public Double getCapacitedebfin(@RequestParam String date_Debut_s, @RequestParam String date_Fin_s) throws ParseException {
		List<Attachements> response = new ArrayList<>();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

		Date date_Debut = dateFormat.parse(date_Debut_s);
		Date date_Fin = dateFormat.parse(date_Fin_s);
		System.out.println("Date début : " + date_Debut);
		System.out.println("Date fin : " + date_Fin);

		List<Acte_traitement> actes = acteTraitementRepo.findActeAttach(date_Debut, date_Fin);
		System.out.println("Actes trouvés : " + actes);
		System.out.println("Nombre d'actes trouvés : " + actes.size());

		Double CapaciteQte_Mois= kpiService.CapaciteQte_Mois(actes);
		return  CapaciteQte_Mois;

	}

	@PostMapping("/Capacite")
	public Double getCapacite(@RequestBody List<Acte_traitement> actes) throws ParseException {

		Double CapaciteQte_Mois= kpiService.CapaciteQte_Mois(actes);
		return  CapaciteQte_Mois;
	}

	@GetMapping("/dmtDeclareedebfin")
	public Float getkpiByPrestdebfin(@RequestParam String date_Debut_s, @RequestParam String date_Fin_s) throws ParseException {
		List<Attachements> response = new ArrayList<>();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

		Date date_Debut = dateFormat.parse(date_Debut_s);
		Date date_Fin = dateFormat.parse(date_Fin_s);
		System.out.println("Date début : " + date_Debut);
		System.out.println("Date fin : " + date_Fin);

		List<Acte_traitement> actes = acteTraitementRepo.findActeAttach(date_Debut, date_Fin);
		System.out.println("Actes trouvés : " + actes);
		System.out.println("Nombre d'actes trouvés : " + actes.size());

		int sommeDuree=0;
		for(Acte_traitement list :actes ){

			sommeDuree =+list.getDuree() ;
		}


		long nbJr= kpiService.getDaysBetween(date_Debut_s, date_Fin_s);

		Float  Dmt_Declare = kpiService.Dmt_Declare(actes, nbJr);
		return Dmt_Declare;
	}

	@PostMapping("/dmtDeclaree")
	public Float getkpiByPrest(@RequestBody List<Acte_traitement> actes) throws ParseException {

		int sommeDuree=0;
		for(Acte_traitement list :actes ){

			sommeDuree =+list.getDuree() ;
		}
		Float  Dmt_Declare = kpiService.Dmt_Declare(actes, 22L);
		return Dmt_Declare;
	}


	@GetMapping("/dmtCalculeedebfin")
	public Float getDmtCalculeedebfin(@RequestParam String date_Debut_s, @RequestParam String date_Fin_s) throws ParseException {
		List<Attachements> response = new ArrayList<>();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

		Date date_Debut = dateFormat.parse(date_Debut_s);
		Date date_Fin = dateFormat.parse(date_Fin_s);
		System.out.println("Date début : " + date_Debut);
		System.out.println("Date fin : " + date_Fin);

		List<Acte_traitement> actes = acteTraitementRepo.findActeAttach(date_Debut, date_Fin);
		System.out.println("Actes trouvés : " + actes);
		System.out.println("Nombre d'actes trouvés : " + actes.size());
		long nbJr= kpiService.getDaysBetween(date_Debut_s, date_Fin_s);
		int sommeDuree=0;
		for(Acte_traitement list :actes ){

			sommeDuree =+list.getDuree() ;
		}
		Double Staffing= kpiService.CalculStaffing(Double.valueOf(sommeDuree));

		double CTJ = kpiService.CalculCTJ(actes, (int) nbJr, Staffing);
		float  Dmt_Calculee = kpiService.Dmt_calcule((float) CTJ);
		return  Dmt_Calculee;

	}

	@PostMapping("/dmtCalculee")
	public Float getDmtCalculee(@RequestBody List<Acte_traitement> actes) throws ParseException {

		int sommeDuree=0;
		for(Acte_traitement list :actes ){

			sommeDuree =+list.getDuree() ;
		}
		Double Staffing= kpiService.CalculStaffing(Double.valueOf(sommeDuree));

		double CTJ = kpiService.CalculCTJ(actes, (int) 22, Staffing);
		float  Dmt_Calculee = kpiService.Dmt_calcule((float) CTJ);
		return  Dmt_Calculee;

	}

	@GetMapping("/ETPdebfin")
	public Float getETPdebfin(@RequestParam String date_Debut_s, @RequestParam String date_Fin_s) throws ParseException {
		List<Attachements> response = new ArrayList<>();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

		Date date_Debut = dateFormat.parse(date_Debut_s);
		Date date_Fin = dateFormat.parse(date_Fin_s);
		System.out.println("Date début : " + date_Debut);
		System.out.println("Date fin : " + date_Fin);

		List<Acte_traitement> actes = acteTraitementRepo.findActeAttach(date_Debut, date_Fin);
		System.out.println("Actes trouvés : " + actes);
		System.out.println("Nombre d'actes trouvés : " + actes.size());
		long nbJr= kpiService.getDaysBetween(date_Debut_s, date_Fin_s);
		int sommeDuree=0;
		for(Acte_traitement list :actes ){

			sommeDuree =+list.getDuree() ;

		}
		Double Staffing= kpiService.CalculStaffing(Double.valueOf(sommeDuree));

		long nbJOuvre = (long) actes.size();
		float  ETP = kpiService.ETP(Staffing, (int) nbJOuvre);
		return  ETP;

	}

	@PostMapping("/ETP")
	public Float getETP(@RequestBody List<Acte_traitement> actes) throws ParseException {
		int sommeDuree=0;
		for(Acte_traitement list :actes ){

			sommeDuree =+list.getDuree() ;

		}
		Double Staffing= kpiService.CalculStaffing(Double.valueOf(sommeDuree));

		long nbJOuvre = (long) actes.size();
		float  ETP = kpiService.ETP(Staffing, (int) nbJOuvre);
		return  ETP;

	}

	/*@PostMapping("/group-actes-by-prest")
    public Map<Prestation, List<Acte_traitement>> groupActesByPrest(@RequestBody List<Acte_traitement> actes) {
        actTraitement.clearElements();
        for (Acte_traitement acte : actes) {
            actTraitement.addElement(acte);
        }
        return actTraitement.groupElementsByPrestation();
    }*/


	/*@PostMapping("/ActesByPrest")
	public List<Acte_traitement> ActesByPrestt(@RequestBody List<Acte_traitement> actes) {
	    return attachService.groupActesByPrestt(actes);
	}*/
	@GetMapping("/ActesByPrest")
	public Map<Prestation, List<Acte_traitement>> ActesByPrest(@RequestParam String date_Debut_s, @RequestParam String date_Fin_s) throws ParseException {
		List<Attachements> response = new ArrayList<>();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

		Date date_Debut = dateFormat.parse(date_Debut_s);
		Date date_Fin = dateFormat.parse(date_Fin_s);
		System.out.println("Date début : " + date_Debut);
		System.out.println("Date fin : " + date_Fin);

		List<Acte_traitement> actes = acteTraitementRepo.findActeAttach(date_Debut, date_Fin);
		System.out.println("Actes trouvés : " + actes);
		System.out.println("Nombre d'actes trouvés : " + actes.size());
		return kpiService.groupActesByPrest(actes);

	}

	//---------------ByMonth-----------
	@GetMapping("/group-by-month")
	public Map<Month, List<Acte_traitement>> groupActesByMonth(@RequestParam int idPrestation, @RequestParam String codeTarif) {
		return kpiService.groupActsByMonth(idPrestation, codeTarif);
	}


	@PostMapping("/countbypres")
	public Long actesCountbytarif(@RequestParam int idPrestation) {
		return kpiService.getcountbypres(idPrestation);
	}

	/*@GetMapping("/filtered-elements/{year}")
	public Map<Month, List<Acte_traitement>> getFilteredElementsByYear(@PathVariable int year) {
		return kpiService.filterByYear(year);
	}*/


	@PostMapping("/actfilterbyyear")
	public Map<Month, List<Acte_traitement>> filteractsbyyear(@RequestParam int idPrestation ,@RequestParam String  codetarif,@RequestParam int year) {
		return kpiService.MonthActesByIdprest(idPrestation,codetarif,year);
	}


	@GetMapping("/groupbymonth")
	public List<LocalDate>  LgroupActsDate(){
		return kpiService.LgroupActsDate();
	}

	@PostMapping("/dur")
	public int getdur(@RequestBody List<Acte_traitement> acts) throws ParseException {

		return  kpiService.getSM( acts);
	}
	@GetMapping("/byprestationn")
	public List<Acte_traitement> getbypr(@RequestParam int  idPrestation, @RequestParam String codeTarif) throws ParseException {
List<Acte_traitement> acts = acteTraitementRepo.findPrestTarif(idPrestation,codeTarif);
		return acts;

	}


	@PostMapping("/presttarif")
	public List<Tarification> getpresttarif(@RequestBody Prestation prest) throws ParseException {

		List<Tarification> acts = tarificationRepo.findByTypeprestation(prest);
		return acts;

	}

	@GetMapping("/CTJactfilterbyyear")
	public Map<Month, Double> CTJactsbyyear(@RequestParam int idPrestation, @RequestParam int year ,@RequestParam int nbjour,  @RequestParam String codeTarif) {
		Map<Month, Double> ctjlist = new HashMap<>();
		Map<Month, List<Acte_traitement>> actesmap = kpiService.MonthActesByIdprest(idPrestation,codeTarif, year);

		for (Map.Entry<Month, List<Acte_traitement>> entry : actesmap.entrySet()) {
			Month month = entry.getKey();
			List<Acte_traitement> actes = entry.getValue();

			int sm = kpiService.getSM(actes);
			Double Staffing= kpiService.CalculStaffing(Double.valueOf(sm));
			Double ctj = kpiService.CalculCTJ(actes,nbjour,Staffing);

			ctjlist.put(month, ctj);
		}

		return ctjlist;
	}

	@GetMapping("/Staffingactfilterbyyear")
	public Map<Month, Double> Staffingactsbyyear(@RequestParam int idPrestation, @RequestParam int year,  @RequestParam String codeTarif) {
		Map<Month, Double> stafflist = new HashMap<>();
		Map<Month, List<Acte_traitement>> actesmap = kpiService.MonthActesByIdprest(idPrestation,codeTarif, year);

		for (Map.Entry<Month, List<Acte_traitement>> entry : actesmap.entrySet()) {
			Month month = entry.getKey();
			List<Acte_traitement> actes = entry.getValue();

			int sm = kpiService.getSM(actes);
			Double Staffing= kpiService.CalculStaffing(Double.valueOf(sm));

			stafflist.put(month, Staffing);
		}

		return stafflist;
	}

	@PostMapping("/CapaciteQteMoistfilterbyyear")
	public Map<Month, Double> CapaciteQte_Mois(@RequestParam int idPrestation, @RequestParam int year, @RequestParam String codeTarif) {
		Map<Month, Double> stafflist = new HashMap<>();
		Map<Month, List<Acte_traitement>> actesmap = kpiService.MonthActesByIdprest(idPrestation, codeTarif,year);

		for (Map.Entry<Month, List<Acte_traitement>> entry : actesmap.entrySet()) {
			Month month = entry.getKey();
			List<Acte_traitement> actes = entry.getValue();

			//int sm = kpiService.getSM(actes);
			Double Staffing= kpiService.CapaciteQte_Mois(actes);

			stafflist.put(month, Staffing);
		}

		return stafflist;
	}

	@GetMapping("/DmtDeclarefilterbyyear")
	public Map<Month, Float> Dmtdeclareyear(@RequestParam int idPrestation, @RequestParam int year , @RequestParam String codeTarif) {
		Map<Month, Float> stafflist = new HashMap<>();
		Map<Month, List<Acte_traitement>> actesmap = kpiService.MonthActesByIdprest(idPrestation, codeTarif,year);

		for (Map.Entry<Month, List<Acte_traitement>> entry : actesmap.entrySet()) {
			Month month = entry.getKey();
			List<Acte_traitement> actes = entry.getValue();

			//int sm = kpiService.getSM(actes);
			float dmt= kpiService.Dmt_Declare(actes, Long.valueOf(22));

			stafflist.put(month, dmt);
		}

		return stafflist;
	}

	@GetMapping("/RealiseParequipeYear")
	public Map<Month, Double> RealiseQte_MoisParequipe(@RequestParam int idequipe, @RequestParam int year, @RequestParam int nbjours ) {
		Map<Month, Double> realQteEqui = new HashMap<>();


		//	Map<Month, List<Acte_traitement>> actesmap = kpiService.MonthActesByIdprest(idPrestation, year);
		List<String> cuids = acteTraitementService.getCUIDsMemeEquipe(idequipe);
		List<Acte_traitement> actes =acteTraitementService.getActeTraitementByAffectationList(cuids);

		Map<Month, List<Acte_traitement>> actElements = kpiService.FiltredByearOfListAct(actes,year);
		for (Map.Entry<Month, List<Acte_traitement>> entry : actElements.entrySet()) {
			Month month = entry.getKey();
			List<Acte_traitement> actess = entry.getValue();


			Double reaEq= kpiService.RealiseQte_MoisParequipe(actess,nbjours);

			realQteEqui.put(month, reaEq);
		}

		return realQteEqui;
	}
	@GetMapping("/RealiseParequipeYear1")
	public Map<Month, Double> RealiseQte_MoisParequipe1(@RequestParam int idequipe, @RequestParam int year, @RequestParam int nbjours , @RequestParam int idPrestation , @RequestParam String codeTarif) {
		Map<Month, Double> realQteEqui = new HashMap<>();


		//	Map<Month, List<Acte_traitement>> actesmap = kpiService.MonthActesByIdprest(idPrestation, year);
		List<String> cuids = acteTraitementService.getCUIDsMemeEquipe(idequipe);
		List<Acte_traitement> actes =acteTraitementService.getActeTraitementByAffectationListIN(cuids,idPrestation,codeTarif);

		Map<Month, List<Acte_traitement>> actElements = kpiService.FiltredByearOfListAct(actes,year);
		for (Map.Entry<Month, List<Acte_traitement>> entry : actElements.entrySet()) {
			Month month = entry.getKey();
			List<Acte_traitement> actess = entry.getValue();


			Double reaEq= kpiService.RealiseQte_MoisParequipe(actess,nbjours);

			realQteEqui.put(month, reaEq);
		}

		return realQteEqui;
	}
	@PostMapping("/PourcentageEquipe")
	public Map<Month, Double> PourcentageEquipe(@RequestParam int idequipe, @RequestParam int year, @RequestParam  int staffingdef) {
		Map<Month, Double> CalcPour = new HashMap<>();


		//	Map<Month, List<Acte_traitement>> actesmap = kpiService.MonthActesByIdprest(idPrestation, year);
		List<String> cuids = acteTraitementService.getCUIDsMemeEquipe(idequipe);
		List<Acte_traitement> actes =acteTraitementService.getActeTraitementByAffectationList(cuids);

		Map<Month, List<Acte_traitement>> actElements = kpiService.FiltredByearOfListAct(actes,year);
		for (Map.Entry<Month, List<Acte_traitement>> entry : actElements.entrySet()) {
			Month month = entry.getKey();
			List<Acte_traitement> actess = entry.getValue();


			Double Pource= kpiService.CalculPourcentage(actess,staffingdef);

			CalcPour.put(month, Pource);
		}

		return CalcPour;
	}


	@GetMapping("/dmtcalculefilterbyyear")
	public Map<Month, Float> dmtcalculebyyear(@RequestParam int idPrestation, @RequestParam int year, @RequestParam String codeTarif) {
		Map<Month, Float> dmtlist = new HashMap<>();
		Map<Month, List<Acte_traitement>> actesmap = kpiService.MonthActesByIdprest(idPrestation,codeTarif, year);

		for (Map.Entry<Month, List<Acte_traitement>> entry : actesmap.entrySet()) {
			Month month = entry.getKey();
			List<Acte_traitement> actes = entry.getValue();

			int sm = kpiService.getSM(actes);
			Double Staffing= kpiService.CalculStaffing(Double.valueOf(sm));
			Double ctj = kpiService.CalculCTJ(actes,22,Staffing);

			float myFloatctj = ctj.floatValue();
			float  Dmt_Calculee = kpiService.Dmt_calcule( myFloatctj);

			dmtlist.put(month, Dmt_Calculee);
		}

		return dmtlist;
	}
	@PostMapping("/Etpfilterbyyear")
	public Map<Month, Float> etpbyyear(@RequestParam int idPrestation, @RequestParam int year , @RequestParam String codeTarif) {
		Map<Month, Float> dmtlist = new HashMap<>();
		Map<Month, List<Acte_traitement>> actesmap = kpiService.MonthActesByIdprest(idPrestation,codeTarif, year);

		for (Map.Entry<Month, List<Acte_traitement>> entry : actesmap.entrySet()) {
			Month month = entry.getKey();
			List<Acte_traitement> actes = entry.getValue();

			int sm = kpiService.getSM(actes);
			Double Staffing= kpiService.CalculStaffing(Double.valueOf(sm));
			Float etp = kpiService.ETP(Staffing,22);



			dmtlist.put(month, etp);
		}

		return dmtlist;
	}

	@GetMapping("/Prodfilterbyyear")
	public Map<Month, Double> Prodbyyear(@RequestParam int idPrestation, @RequestParam int year ,@RequestParam Double prix,@RequestParam int staffingdef, @RequestParam String codeTarif, @RequestParam int ideq) {
		Map<Month, Double> dmtlist = new HashMap<>();

		//ligne a changer si on veut filtrer by prest et tarif
		List<String> cuids = acteTraitementService.getCUIDsMemeEquipe(ideq);
		List<Acte_traitement> acte =acteTraitementService.getActeTraitementByAffectationListIN(cuids,idPrestation,codeTarif);
		//Map<Month, List<Acte_traitement>> AC = kpiService.MonthActesByIdprest(idPrestation,codeTarif, year);
		Map<Month, List<Acte_traitement>> actesmap = kpiService.FiltredByearOfListAct(acte,year);

		for (Map.Entry<Month, List<Acte_traitement>> entry : actesmap.entrySet()) {
			Month month = entry.getKey();
			List<Acte_traitement> actes = entry.getValue();

			int sm = kpiService.getSM(actes);
			Double Staffing= kpiService.CalculStaffing(Double.valueOf(sm));
			Double prod = kpiService.Productivite(actes,prix,staffingdef);



			dmtlist.put(month, prod);
		}

		return dmtlist;
	}

	@GetMapping("/ReaCaparbyyear")
	public Map<Month, Double> reaSurCapacite(@RequestParam int idPrestation, @RequestParam int year , @RequestParam String codeTarif,@RequestParam int nbjours ,@RequestParam int idequipe) {
		Map<Month, Double> dmtlist = new HashMap<>();

		//ligne a changer si on veut filtrer by prest et tarif
	//	List<String> cuids = acteTraitementService.getCUIDsMemeEquipe(ideq);
		//List<Acte_traitement> acte =acteTraitementService.getActeTraitementByAffectationListIN(cuids,idPrestation,codeTarif);
		//Map<Month, List<Acte_traitement>> AC = kpiService.MonthActesByIdprest(idPrestation,codeTarif, year);
	//	Map<Month, List<Acte_traitement>> actesmap = kpiService.FiltredByearOfListAct(AC,year);
		List<String> cuids = acteTraitementService.getCUIDsMemeEquipe(idequipe);
		List<Acte_traitement> actes =acteTraitementService.getActeTraitementByAffectationListIN(cuids,idPrestation,codeTarif);

		Map<Month, List<Acte_traitement>> actElements = kpiService.FiltredByearOfListAct(actes,year);
		for (Map.Entry<Month, List<Acte_traitement>> entry : actElements.entrySet()) {
			Month month = entry.getKey();
			List<Acte_traitement> actess = entry.getValue();


			Double reaEq= kpiService.RealiseQte_MoisParequipe(actess,nbjours);
			Double capa= kpiService.CapaciteQte_Mois(actess);

		Double reaSurCap = reaEq / capa ;



			dmtlist.put(month, reaSurCap);
		}

		return dmtlist;
	}

}
