package blackcap.nationalescape.Model;

public class Point_Model {
    private android.app.Activity Activity;
    private String Pk;
    private String Category;
    private String Title;
    private String User_Pk;
    private String Point;
    private String Date;
    public Point_Model(android.app.Activity activity, String pk, String category, String title, String point, String date){
        this.Activity = activity;
        this.Pk = pk;
        this.Category = category;
        this.Title = title;
        this.Point = point;
        this.Date = date;
    }
    public android.app.Activity getActivity(){return Activity;}
    public String getPk(){return Pk;}
    public String getCategory(){
        return Category;
    }
    public String getTitle(){return Title;}
    public String getPoint(){
        return Point;
    }
    public String getDate(){
        return Date;
    }
}
