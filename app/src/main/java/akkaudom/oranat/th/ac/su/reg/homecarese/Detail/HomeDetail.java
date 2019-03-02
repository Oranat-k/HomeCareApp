package akkaudom.oranat.th.ac.su.reg.homecarese.Detail;

public class HomeDetail {
    public String Topic;
    public String ImgUrl;

    public HomeDetail(String topic, String imgUrl) {
        Topic = topic;
        ImgUrl = imgUrl;
    }

    public String getTopic() {
        return Topic;
    }

    public void setTopic(String topic) {
        Topic = topic;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }
}
