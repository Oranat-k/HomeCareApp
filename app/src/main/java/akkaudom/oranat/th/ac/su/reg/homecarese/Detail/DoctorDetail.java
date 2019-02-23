package akkaudom.oranat.th.ac.su.reg.homecarese.Detail;

public class DoctorDetail {
    private String nameDoctor;
    private String nameHospital;

    public DoctorDetail(String namePatient,String nameHospital) {
        this.nameDoctor = namePatient;
        this.nameHospital = nameHospital;
    }

    public String getNameDoctor() { return nameDoctor; }

    public void setNameDoctor(String nameDoctor) { this.nameDoctor = nameDoctor; }

    public String getNameHospital() { return nameHospital; }

    public void setNameHospital(String nameHospital) { this.nameHospital = nameHospital; }

}
