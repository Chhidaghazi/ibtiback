package com.sofrecom.cobli.DTO;

import com.sofrecom.cobli.models.Prestation;

import java.util.Date;

public class ActeTraitementDTO {
    private String idacte;
    private String refTacheBPU;
    private Prestation type_prestation;
    private String type_element;
    private int quantite;
    private Date dateReception;
    private Date dateLivraison;
    private Date dateValidation;
    private String affectation;
    private int duree;
    private String commentaire;
    private String motif;
    private String statutFacturation;
    private Date dateReprise;
    private String repriseFacturable;
    private Date dateDeadline;
    private String priorite;

    public String getIdacte() {
        return idacte;
    }

    public void setIdacte(String idacte) {
        this.idacte = idacte;
    }

    public String getRefTacheBPU() {
        return refTacheBPU;
    }

    public void setRefTacheBPU(String refTacheBPU) {
        this.refTacheBPU = refTacheBPU;
    }

    public Prestation getType_prestation() {
        return type_prestation;
    }

    public void setType_prestation(Prestation type_prestation) {
        this.type_prestation = type_prestation;
    }

    public String getType_element() {
        return type_element;
    }

    public void setType_element(String type_element) {
        this.type_element = type_element;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public Date getDateReception() {
        return dateReception;
    }

    public void setDateReception(Date dateReception) {
        this.dateReception = dateReception;
    }

    public Date getDateLivraison() {
        return dateLivraison;
    }

    public void setDateLivraison(Date dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    public Date getDateValidation() {
        return dateValidation;
    }

    public void setDateValidation(Date dateValidation) {
        this.dateValidation = dateValidation;
    }

    public String getAffectation() {
        return affectation;
    }

    public void setAffectation(String affectation) {
        this.affectation = affectation;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public String getStatutFacturation() {
        return statutFacturation;
    }

    public void setStatutFacturation(String statutFacturation) {
        this.statutFacturation = statutFacturation;
    }

    public Date getDateReprise() {
        return dateReprise;
    }

    public void setDateReprise(Date dateReprise) {
        this.dateReprise = dateReprise;
    }

    public String getRepriseFacturable() {
        return repriseFacturable;
    }

    public void setRepriseFacturable(String repriseFacturable) {
        this.repriseFacturable = repriseFacturable;
    }

    public Date getDateDeadline() {
        return dateDeadline;
    }

    public void setDateDeadline(Date dateDeadline) {
        this.dateDeadline = dateDeadline;
    }

    public String getPriorite() {
        return priorite;
    }

    public void setPriorite(String priorite) {
        this.priorite = priorite;
    }
}
