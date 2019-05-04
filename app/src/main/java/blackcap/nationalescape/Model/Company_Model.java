package blackcap.nationalescape.Model;

public class Company_Model {
    private android.app.Activity Activity;
    private String Company_Pk;
    private String Owner_Pk;
    private String Title;
    private String Intro;
    private String Grade_Avg;
    private String Recommend_Count;
    private String Address;
    private String Distance;
    private String Favorite;
    public Company_Model(android.app.Activity activity, String company_pk, String owner_pk, String title, String intro, String grade_avg, String recommend_count, String address, String distance, String favorite){
        this.Activity = activity;
        this.Company_Pk = company_pk;
        this.Owner_Pk = owner_pk;
        this.Title = title;
        this.Intro = intro;
        this.Grade_Avg = grade_avg;
        this.Recommend_Count = recommend_count;
        this.Address = address;
        this.Distance = distance;
        this.Favorite = favorite;
    }
    public android.app.Activity getActivity(){return Activity;}
    public String getCompany_Pk(){return Company_Pk;}
    public String getOwner_Pk(){return Owner_Pk;}
    public String getTitle() {
        return Title;
    }
    public String getIntro() {
        return Intro;
    }
    public String getGrade_Avg(){return Grade_Avg;}
    public String getRecommend_Count(){
        return Recommend_Count;
    }
    public String getAddress(){return Address;}
    public String getDistance(){return Distance;}
    public String getFavorite(){return Favorite;}
}
