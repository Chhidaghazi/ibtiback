package com.sofrecom.cobli.DTO;

public class PrestationDTO {
    private String nomPrestation;
    private int deadline;

    public String getNomPrestation() {
        return nomPrestation;
    }

    public void setNomPrestation(String nomPrestation) {
        this.nomPrestation = nomPrestation;
    }

    public int getDeadline() {
        return deadline;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }
}
