package com.sofrecom.cobli.DTO;

import java.util.Date;

public class DESATDTO  extends  ActeTraitementDTO{


        private String zone;
        private String fi;
        private Date dateRefus;
        private String motifReaffectation;
        private String statutOperatonnels;
        private Date dateReaffectation;
        private String cog;
        private String ui;
        private Date dateLivReaffectation;

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getFi() {
        return fi;
    }

    public void setFi(String fi) {
        this.fi = fi;
    }

    public Date getDateRefus() {
        return dateRefus;
    }

    public void setDateRefus(Date dateRefus) {
        this.dateRefus = dateRefus;
    }

    public String getMotifReaffectation() {
        return motifReaffectation;
    }

    public void setMotifReaffectation(String motifReaffectation) {
        this.motifReaffectation = motifReaffectation;
    }

    public String getStatutOperatonnels() {
        return statutOperatonnels;
    }

    public void setStatutOperatonnels(String statutOperatonnels) {
        this.statutOperatonnels = statutOperatonnels;
    }

    public Date getDateReaffectation() {
        return dateReaffectation;
    }

    public void setDateReaffectation(Date dateReaffectation) {
        this.dateReaffectation = dateReaffectation;
    }

    public String getCog() {
        return cog;
    }

    public void setCog(String cog) {
        this.cog = cog;
    }

    public String getUi() {
        return ui;
    }

    public void setUi(String ui) {
        this.ui = ui;
    }

    public Date getDateLivReaffectation() {
        return dateLivReaffectation;
    }

    public void setDateLivReaffectation(Date dateLivReaffectation) {
        this.dateLivReaffectation = dateLivReaffectation;
    }
}
