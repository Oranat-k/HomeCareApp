package akkaudom.oranat.th.ac.su.reg.homecarese.Detail;

public class ProfileDetail {
    private String namePatient;
    private String imagePatient;
    private String statusPatient;

    public ProfileDetail(String namePatient, String imagePatient, String statusPatient) {
        this.namePatient = namePatient;
        this.imagePatient = imagePatient;
        this.statusPatient = statusPatient;
    }

    public String getNamePatient() {
        return namePatient;
    }

    public void setNamePatient(String namePatient) {
        this.namePatient = namePatient;
    }

    public String getImagePatient() {
        return imagePatient;
    }

    public void setImagePatient(String imagePatient) {
        this.imagePatient = imagePatient;
    }

    public String getStatusPatient() {
        return statusPatient;
    }

    public void setStatusPatient(String statusPatient) {
        this.statusPatient = statusPatient;
    }
}
