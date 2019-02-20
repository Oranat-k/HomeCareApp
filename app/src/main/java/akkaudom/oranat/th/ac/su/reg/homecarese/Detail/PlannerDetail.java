package akkaudom.oranat.th.ac.su.reg.homecarese.Detail;

public class PlannerDetail {

    private String title;
    private String [] subtitle;
    private String imagePath;
    private String mode;
    private String value;
    private String status;
    private String date;
    private String during;


//    private String timeMed;
//    private String range;
//    private String amount;




    public PlannerDetail(String title, String mode, String status) {
        this.title = title;
        this.mode = mode;
        this.status = status;

    }//default mode

    public PlannerDetail(String title, String [] subtitle, String imagePath, String mode, String status) {
        this.title = title;
        this.subtitle = subtitle;
        this.imagePath = imagePath;
        this.mode = mode;
        this.status = status;

    }//Medicine mode


    public PlannerDetail(String title, String[] subtitle, String mode, String status) {
        this.title = title;
        this.subtitle = subtitle;
        this.mode = mode;
        this.status = status;

    }//Note mode

    public PlannerDetail(String title, String[] subtitle, String mode, String status, String date, String during) {
        this.title = title;
        this.subtitle = subtitle;
        this.mode = mode;
        this.status = status;
        this.date = date;
        this.during = during;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDuring() {
        return during;
    }

    public void setDuring(String during) {
        this.during = during;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String[] getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String[] subtitle) {
        this.subtitle = subtitle;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
