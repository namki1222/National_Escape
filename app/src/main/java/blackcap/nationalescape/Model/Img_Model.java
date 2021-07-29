package blackcap.nationalescape.Model;

public class Img_Model {
    private android.app.Activity Activity;
    private String Img;
    public Img_Model(android.app.Activity activity, String img){
        this.Activity = activity;
        this.Img = img;

    }
    public android.app.Activity getActivity(){return Activity;}
    public String getImg(){return Img;}
}
