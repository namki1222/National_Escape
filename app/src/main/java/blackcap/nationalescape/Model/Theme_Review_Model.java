package blackcap.nationalescape.Model;

import blackcap.nationalescape.Activity.tab3.Theme_Focus;

public class Theme_Review_Model {
    private android.app.Activity Activity;
    private String Nickname;
    private String Grade;
    private String Content;
    private String Date;
    private String User_Pk;
    private String Review_User_Pk;
    private String Theme_Pk;
    private String Level;
    private String Status;
    private String Time;
    private String Hint;
    private String Experience;
    private String Line;
    private String User_Time;
    private String Theme_Time;
    private String Recommend_Flag;
    private String Recommend_Count;
    private String Grade_Flag;
    private String Bol_Owner;
    private String Owner_Name;
    private String Owner_Date;
    private String Owner_Memo;
    private String OrderBy;
    public Theme_Review_Model(android.app.Activity activity, String user_pk, String review_user_pk, String nickname, String grade, String content, String date, String theme_pk, String level, String status, String time, String hint, String experience, String line, String user_time, String theme_time, String recommend_Flag, String recommend_Count, String grade_Flag, String bol_Owner, String owner_Name, String owner_Date, String owner_Memo, String orderBy){
        this.Activity = activity;
        this.Nickname = nickname;
        this.Grade = grade;
        this.Content = content;
        this.Date = date;
        this.User_Pk = user_pk;
        this.Review_User_Pk = review_user_pk;
        this.Theme_Pk = theme_pk;
        this.Level = level;
        this.Status = status;
        this.Time = time;
        this.Hint = hint;
        this.Experience = experience;
        this.Line = line;
        this.User_Time = user_time;
        this.Theme_Time = theme_time;
        this.Recommend_Flag = recommend_Flag;
        this.Recommend_Count = recommend_Count;
        this.Grade_Flag = grade_Flag;
        this.Bol_Owner = bol_Owner;
        this.Owner_Name = owner_Name;
        this.Owner_Date = owner_Date;
        this.Owner_Memo = owner_Memo;
        this.OrderBy = orderBy;
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
    public String getTheme_Pk(){
        return Theme_Pk;
    }
    public String getLevel(){
        return Level;
    }
    public String getStatus(){
        return Status;
    }
    public String getTime(){
        return Time;
    }
    public String getHint(){
        return Hint;
    }
    public String getExperience(){
        return Experience;
    }
    public String getLine(){
        return Line;
    }
    public String getUser_Time(){
        return User_Time;
    }
    public String getTheme_Time(){
        return Theme_Time;
    }
    public String getRecommend_Flag(){
        return Recommend_Flag;
    }
    public String getRecommend_Count(){
        return Recommend_Count;
    }
    public String getGrade_Flag(){
        return Grade_Flag;
    }
    public String getBol_Owner(){
        return Bol_Owner;
    }
    public String getOwner_Name(){
        return Owner_Name;
    }
    public String getOwner_Memo(){
        return Owner_Memo;
    }
    public String getOwner_Date(){
        return Owner_Date;
    }
    public String getOrderBy(){
        return OrderBy;
    }
}
