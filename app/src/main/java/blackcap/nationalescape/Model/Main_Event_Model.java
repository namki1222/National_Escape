package blackcap.nationalescape.Model;

public class Main_Event_Model {
    private android.app.Activity Activity;
    private String Pk;
    private String Img;
    private String Category;
    private String Contents;
    public Main_Event_Model(android.app.Activity activity, String pk, String img, String category, String contents){
        this.Activity = activity;
        this.Pk = pk;
        this.Img = img;
        this.Category = category;
        this.Contents = contents;
    }
    public android.app.Activity getActivity(){return Activity;}
    public String getPk(){return Pk;}
    public String getImg(){return Img;}
    public String getCategory() {
        return Category;
    }
    public String getContents() {
        return Contents;
    }
}
