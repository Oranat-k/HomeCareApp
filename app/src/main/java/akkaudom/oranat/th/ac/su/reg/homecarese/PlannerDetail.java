package akkaudom.oranat.th.ac.su.reg.homecarese;

public class PlannerDetail {

    private String title;
    private String status;

    public PlannerDetail(String title, String status) {
        this.title = title;
        this.status = status;
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
}
