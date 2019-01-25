package akkaudom.oranat.th.ac.su.reg.homecarese.Detail;

import android.util.JsonReader;

import org.json.JSONObject;

public class MedicineDetail {
    private String nameMed;
    private  String imageUrl;
    private String timeMed;
    private JSONObject range;
    private String amount;
    private String status;


    public String getNameMed() { return nameMed; }

    public void setNameMed(String nameMed) {
        this.nameMed = nameMed;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTimeMed() {
        return timeMed;
    }

    public void setTimeMed(String timeMed) { this.timeMed = timeMed; }

    public JSONObject getRange() {
        return range;
    }

    public void setRange(JSONObject range) {
        this.range = range;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
