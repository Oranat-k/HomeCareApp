package akkaudom.oranat.th.ac.su.reg.homecarese.Detail;

public class PatientDetail {
    public String Id;
    public String Name;
    public String ImgUrl;

    public PatientDetail(String id, String name, String imgUrl) {
        Id = id;
        Name = name;
        ImgUrl = imgUrl;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }
}
