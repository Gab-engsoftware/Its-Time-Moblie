package br.com.gabriel.itstime;

public class MedicamentsItem {

    String medicamentID;
    String medicamentName;
    String medicamentHours;
    String medicamentPeriod;

    public MedicamentsItem() {
    }

    public MedicamentsItem(String medicamentID, String medicamentName, String medicamentHours, String medicamentPeriod) {
        this.medicamentID = medicamentID;
        this.medicamentName = medicamentName;
        this.medicamentHours = medicamentHours;
        this.medicamentPeriod = medicamentPeriod;
    }

    public String getMedicamentID() {
        return medicamentID;
    }

    public void setMedicamentID(String medicamentID) {
        this.medicamentID = medicamentID;
    }

    public String getMedicamentName() {
        return medicamentName;
    }

    public void setMedicamentName(String medicamentName) {
        this.medicamentName = medicamentName;
    }

    public String getMedicamentHours() {
        return medicamentHours;
    }

    public void setMedicamentHours(String medicamentHours) {
        this.medicamentHours = medicamentHours;
    }

    public String getMedicamentPeriod() {
        return medicamentPeriod;
    }

    public void setMedicamentPeriod(String medicamentPeriod) {
        this.medicamentPeriod = medicamentPeriod;
    }
}
