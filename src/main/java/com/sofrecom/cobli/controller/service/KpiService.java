package com.sofrecom.cobli.controller.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.sofrecom.cobli.models.Acte_traitement;
import com.sofrecom.cobli.models.Attachements;
import com.sofrecom.cobli.models.Prestation;
import com.sofrecom.cobli.repository.Acte_traitementRepository;
import com.sofrecom.cobli.repository.ESIMBRepository;
import com.sofrecom.cobli.repository.Graphic_Repository;
import com.sofrecom.cobli.repository.PrestationRepository;

@Service
public class KpiService {

	@Autowired
	private Graphic_Repository Graphic_repository;

	@Autowired
	ESIMBRepository ESIMBRepo;

	@Autowired
	PrestationRepository prestationRepository;

	@Autowired
	private Acte_traitementRepository acteTraitementRepo;


	public Integer getSM( @RequestBody List<Acte_traitement> actes)  {
		int sommeDuree=0;
		for(Acte_traitement list :actes ) {
			sommeDuree =sommeDuree+list.getDuree() ;
		}
		return  sommeDuree;
	}


	public Double CalculStaffing (Double sommeMin  ){
		return sommeMin/(60*7.5) ;
	}
	public Double CalculCTJ (List<Acte_traitement>  List ,int nbJour,double staffing){

		return (List.size()/nbJour)/staffing ;
	}
	public Double CapaciteQte_Mois (List<Acte_traitement>  List ){

		return Double.valueOf(List.size());
	}
	public Double RealiseQte_MoisParequipe (List<Acte_traitement>  List ,int nbJour){

		return Double.valueOf(List.size()/nbJour);
	}
	public Double RealiseSurCapactie (Double realise ,Double capacite){

		return realise/capacite;
	}
	public Float Dmt_Declare (List<Acte_traitement>  ListAct  , Long nbjour){
		float sommeDuree=0;
		for(Acte_traitement list :ListAct ){

			sommeDuree =sommeDuree +list.getDuree() ;
		}

		return Float.valueOf((sommeDuree/ListAct.size()));
	}
	public Float Dmt_calcule(Float ctj) {
		if (ctj == 0) {
			return 0f; // Return 0 instead of infinity when ctj is 0
		}

		return Float.valueOf((float) ((7.5 * 60) / ctj));
	}
	public Float ETP ( Double staffing , int nbJoursOuvre){

		return Float.valueOf((float) (staffing/nbJoursOuvre));
	}
	public Double DollarETP ( Double staffing , Double prixJour){

		return staffing*prixJour;
	}
	/////// manque explication
	public Double DollarBPU ( List<Acte_traitement> Listact , Double prixUO){
		return Listact.size()*prixUO;
	}


	public Double Productivite ( List<Acte_traitement> Listact , Double prixUO , int staffingdef){
		int sommeDuree=0;
		for(Acte_traitement list :Listact ){
			sommeDuree =+list.getDuree() ;
		}
		//Double.valueOf(ListAct.size()/sommeDuree)
		return Double.valueOf(sommeDuree/(staffingdef*7.5*60)) ;
	}


	public Double CalculPourcentage ( List<Acte_traitement> ListactParequipe  , int staffingdef){
		int sommeDuree=0;
		for(Acte_traitement list :ListactParequipe ){
			sommeDuree =+list.getDuree() ;
		}

		return Double.valueOf(((sommeDuree/(staffingdef*7.5*60)))*100);
		//(sommeDuree//dureeFormulaire°*100;
	}


	public static long getDaysBetween(String startDateStr, String endDateStr) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate startDate = LocalDate.parse(startDateStr, formatter);
		LocalDate endDate = LocalDate.parse(endDateStr, formatter);

		return ChronoUnit.DAYS.between(startDate, endDate);
	}


	public Map<Prestation, List<Acte_traitement>> groupActesByPrest(List<Acte_traitement> actes) {
		Map<Prestation, List<Acte_traitement>> groupedElements = new HashMap<>();


		List<Prestation> prestation = prestationRepository.findAll().stream()
				.distinct()
				.collect(Collectors.toList());
		// Initialize the map with all the months of the year
		for (Prestation prest : prestation) {

			List<Acte_traitement> acteee = acteTraitementRepo.findByTypeprestation(prest) ;
			groupedElements.put(prest, acteee);

		}

/*
        for (Acte_traitement acte : actes) {
        	String prest = acte.getType_prestation().getNomPrestation();
        	groupedElements.get(prest).add(acte);
        }
        */
    /*    for (Map.Entry<Prestation, List<Acte_traitement>> entry : groupedElements.entrySet()) {
        	String key = entry.getKey().getNomPrestation();
        	List<Acte_traitement> elements = entry.getValue();
        	System.out.println("Key: " + key);
        	System.out.println("Elements: " + elements); System.out.println();
        	}
        */

		return groupedElements;



	}



	public List<Acte_traitement> groupActesByPrestt(List<Acte_traitement> actes) {
		Map<String, List<Acte_traitement>> groupedElements = new HashMap<>();
		List<Prestation> prestation = prestationRepository.findAll().stream()
				.distinct()
				.collect(Collectors.toList());
		// Initialize the map with all the months of the year
		List <Acte_traitement> tajribi = new ArrayList<Acte_traitement>() ;
		List<String> ibtihel= new ArrayList<String>();
		for (Prestation prest : prestation) {
			List<Acte_traitement> acteee = acteTraitementRepo.findByTypeprestation(prest) ;
			tajribi.addAll(acteee);
			groupedElements.put(prest.getNomPrestation(), new ArrayList<>());
			ibtihel.add(prest.getNomPrestation());
		}
		List<String> gh= new ArrayList<String>();

		for (Acte_traitement acte : actes) {
			String prest = acte.getType_prestation().getNomPrestation();

			gh.add(prest);
			groupedElements.get(prest).add(acte);
		}

		return tajribi;

	}

	//-----------Filter by Month----------------
	public Map<Month, List<Acte_traitement>> groupActsByMonth(int idPrestation, String codeTarif) {
		Map<Month, List<Acte_traitement>> actElements = new TreeMap<>();

		List<Acte_traitement> actes = acteTraitementRepo.findPrestTarif(idPrestation, codeTarif);

		for (Acte_traitement act : actes) {
			Date dateLivraison = act.getDateLivraison();
			LocalDate localDate = dateLivraison.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

			Month month = Month.valueOf(localDate.getMonth().name().toUpperCase());
			actElements.computeIfAbsent(month, k -> new ArrayList<>()).add(act);
		}

		return actElements;
	}
	public Long getcountbypres(int idPrestation) {
		Long s = acteTraitementRepo.findPrestTarifCount(idPrestation);
		return s ;
	}


	/*public Map<Month, List<Acte_traitement>> filterByYear(int year) {
		Map<Month, List<Acte_traitement>> groupedElements = groupActsByMonth();

		Map<Month, List<Acte_traitement>> filteredElements = new HashMap<>();

		for (Month month : Month.values()) {
			filteredElements.put(month, new ArrayList<>());
		}

		for (Map.Entry<Month, List<Acte_traitement>> entry : groupedElements.entrySet()) {
			Month month = entry.getKey();
			List<Acte_traitement> acts = entry.getValue();

			for (Acte_traitement act : acts) {
				Date dateLivraison = act.getDateLivraison();
				LocalDate localDate = dateLivraison.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

				int actYear = localDate.getYear();

				if (actYear == year) {
					filteredElements.get(month).add(act);
				}
			}
		}

		return filteredElements;
	}*/



		/*public List<LocalDate>  LgroupActsDate() {
			   Map<Month, List<Acte_traitement>> actElements = new HashMap<>();

			   // Initialize the map with all the months of the year
			   for (Month month : Month.values()) {
			      actElements.put(month, new ArrayList<>());
			   }
			   List<Acte_traitement> actes = acteTraitementRepo.findAll();
			   List<LocalDate> local = new ArrayList<LocalDate>() ;
			   for (Acte_traitement act : actes) {
			      Date dateLivraison = act.getDateLivraison();
			     LocalDate localDate = dateLivraison.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			      local.add(localDate);
			      Month month = localDate.getMonth();
			      actElements.computeIfAbsent(month, k -> new ArrayList<>()).add(act);

			   }

			   return local;
			}*/

		/*public List<LocalDate> LgroupActsDate() {
			   Map<Month, List<Acte_traitement>> actElements = new HashMap<>();

			   // Initialize the map with all the months of the year
			   for (Month month : Month.values()) {
			      actElements.put(month, new ArrayList<>());
			   }

			   List<Acte_traitement> actes = acteTraitementRepo.findAll();
			   List<LocalDate> local = new ArrayList<LocalDate>() ;
			   DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

			   for (Acte_traitement act : actes) {
			      Date dateLivraison = act.getDateLivraison();
			      LocalDateTime dateTime = LocalDateTime.ofInstant(dateLivraison.toInstant(), ZoneId.systemDefault());
			      LocalDate localDate = dateTime.toLocalDate();
			      local.add(localDate);
			      Month month = localDate.getMonth();
			      actElements.computeIfAbsent(month, k -> new ArrayList<>()).add(act);
			   }

			   return local;
			}*/

	public List<LocalDate> LgroupActsDate() {
		Map<Month, List<Acte_traitement>> actElements = new HashMap<>();

		// Initialize the map with all the months of the year
		for (Month month : Month.values()) {
			actElements.put(month, new ArrayList<>());
		}

		List<Acte_traitement> actes = acteTraitementRepo.findAll();
		List<LocalDate> local = new ArrayList<>();

		for (Acte_traitement act : actes) {
			Date dateLivraison = act.getDateLivraison();
			LocalDate localDate = dateLivraison.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			local.add(localDate);
			Month month = localDate.getMonth();
			actElements.computeIfAbsent(month, k -> new ArrayList<>()).add(act);
		}

		local.sort(Comparator.naturalOrder());
		return local;
	}

	public static int getDaysWithoutWeekends(int year, Month month) {
		int days = month.length(isLeapYear(year));

		LocalDate start = LocalDate.of(year, month, 1);
		LocalDate end = LocalDate.of(year, month, days);

		int daysWithoutWeekends = 0;
		while (!start.isAfter(end)) {
			if (!isWeekend(start.getDayOfWeek())) {
				daysWithoutWeekends++;
			}
			start = start.plusDays(1);
		}

		return daysWithoutWeekends;
	}
	public static boolean isLeapYear(int year) {
		return LocalDate.ofYearDay(year, 1).isLeapYear();
	}

	public static boolean isWeekend(DayOfWeek dayOfWeek) {
		return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
	}

	public Map<Month, List<Acte_traitement>> MonthActesByIdprest(int idPrestation ,String codreTarif , int annee) {
		Map<Month, List<Acte_traitement>> actElements = new TreeMap<>();
		Map<Month, List<Acte_traitement>> mapObjetsParMois = new HashMap<>();
		List<Acte_traitement> actes = acteTraitementRepo.findPrestTarif(idPrestation,codreTarif);

		//List<Acte_traitement> actes = acteTraitementRepo.findPrest(idPrestation);
		for (Acte_traitement act : actes) {
			Date dateLivraison = act.getDateLivraison();

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dateLivraison);

			int anneeObjet = calendar.get(Calendar.YEAR);

			if (anneeObjet == annee) {
				int moisObjet = calendar.get(Calendar.MONTH);
				Month mois = Month.of(moisObjet + 1);

				List<Acte_traitement> objetsParMois = mapObjetsParMois.getOrDefault(mois, new ArrayList<>());
				objetsParMois.add(act);
				mapObjetsParMois.put(mois, objetsParMois);
			}

		}

		return mapObjetsParMois;
	}
	public Map<Month, List<Acte_traitement>> FiltredByearOfListAct(List<Acte_traitement> actes,int annee) {
		Map<Month, List<Acte_traitement>> actElements = new TreeMap<>();
		Map<Month, List<Acte_traitement>> mapObjetsParMois = new HashMap<>();


		//	List<Acte_traitement> actes = acteTraitementRepo.findPrest(idPrestation);
		//List<Acte_traitement> actes = acteTraitementRepo.findPrest(idPrestation);
		//List<Acte_traitement> actes = acteTraitementRepo.findAll();

		for (Acte_traitement act : actes) {
			Date dateLivraison = act.getDateLivraison();

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dateLivraison);

			int anneeObjet = calendar.get(Calendar.YEAR);

			if (anneeObjet == annee) {
				int moisObjet = calendar.get(Calendar.MONTH);
				Month mois = Month.of(moisObjet + 1);

				List<Acte_traitement> objetsParMois = mapObjetsParMois.getOrDefault(mois, new ArrayList<>());
				objetsParMois.add(act);
				mapObjetsParMois.put(mois, objetsParMois);
			}
		}

		return mapObjetsParMois;
	}

}
