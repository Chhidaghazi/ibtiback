package com.sofrecom.cobli.DTO;

import java.util.Date;

public class ESIMBDTO extends ActeTraitementDTO{
    private String codeBanbou;
    private String codeIMB;
    private Date dateVerification;

    public String getCodeBanbou() {
        return codeBanbou;
    }

    public void setCodeBanbou(String codeBanbou) {
        this.codeBanbou = codeBanbou;
    }

    public String getCodeIMB() {
        return codeIMB;
    }

    public void setCodeIMB(String codeIMB) {
        this.codeIMB = codeIMB;
    }

    public Date getDateVerification() {
        return dateVerification;
    }

    public void setDateVerification(Date dateVerification) {
        this.dateVerification = dateVerification;
    }
}
