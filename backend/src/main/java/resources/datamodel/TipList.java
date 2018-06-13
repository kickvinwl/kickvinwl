package resources.datamodel;

public class TipList {
    private String token;

    private Tip[] tips;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Tip[] getTips() {
        return tips;
    }

    public void setTips(Tip[] tips) {
        this.tips = tips;
    }
}
