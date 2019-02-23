package akkaudom.oranat.th.ac.su.reg.homecarese.Detail;

public class DoctorDetail {
    private String nameDoctor;
    private String nameHospital;
    private String timeDoc;

    public DoctorDetail(String nameDoctor, String nameHospital, String timeDoc) {
        this.nameDoctor = nameDoctor;
        this.nameHospital = nameHospital;
        this.timeDoc = timeDoc;
    }

    public String getNameDoctor() {
        return nameDoctor;
    }

    public void setNameDoctor(String nameDoctor) {
        this.nameDoctor = nameDoctor;
    }

    public String getNameHospital() {
        return nameHospital;
    }

    public void setNameHospital(String nameHospital) {
        this.nameHospital = nameHospital;
    }

    public String getTimeDoc() {
        return timeDoc;
    }

    public void setTimeDoc(String timeDoc) {
        this.timeDoc = timeDoc;
    }
}
