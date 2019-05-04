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
    public Review_Model(android.app.Activity activity, String user_pk, String review_user_pk, String nickname, String grade, String content, String date, String company_pk){
        this.Activity = activity;
        this.Nickname = nickname;
        this.Grade = grade;
        this.Content = content;
        this.Date = date;
        this.User_Pk = user_pk;
        this.Review_User_Pk = review_user_pk;
        this.Company_Pk = company_pk;
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
}

