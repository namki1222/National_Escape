package blackcap.nationalescape.Model;

public class Review_Model {
    private android.app.Activity Activity;
    private String Nickname;
    private String Grade;
    private String Content;
    private String Date;
    private String User_Pk;
    private String Review_User_Pk;
    private String Company_Pk;
    private String Bol_Owner;
    private String Owner_Date;
    private String Owner_Memo;
    private String Title;

    public Review_Model(android.app.Activity activity, String user_pk, String review_user_pk, String nickname, String grade, String content, String date, String company_pk, String bol_Owner, String title, String owner_Date, String owner_Memo){
        this.Activity = activity;
        this.Nickname = nickname;
        this.Grade = grade;
        this.Content = content;
        this.Date = date;
        this.User_Pk = user_pk;
        this.Review_User_Pk = review_user_pk;
        this.Company_Pk = company_pk;
        this.Bol_Owner = bol_Owner;
        this.Owner_Date = owner_Date;
        this.Owner_Memo = owner_Memo;
        this.Title = title;
    }
    public android.app.Activity getActivity(){return Activity;}
    public String getNickname(){return Nickname;}
    public String getGrade() {
        return Grade;
    }
    public String getContent() {
        return Content;
    }
    public String getDate() {
        return Date;
    }
    public String getUser_Pk(){
        return User_Pk;
    }
    public String getReview_User_Pk(){
        return Review_User_Pk;
    }
    public String getCompany_Pk(){
        return Company_Pk;
    }
    public String getBol_Owner(){
        return Bol_Owner;
    }
    public String getOwner_Date(){
        return Owner_Date;
    }
    public String getOwner_Memo(){
        return Owner_Memo;
    }
    public String getTitle(){
        return Title;
    }
}

