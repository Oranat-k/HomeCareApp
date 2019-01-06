package akkaudom.oranat.th.ac.su.reg.homecarese;

public class PlannerDetail {

    private String title;
    private boolean status;

    public PlannerDetail(String title, boolean status) {
        this.title = title;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
