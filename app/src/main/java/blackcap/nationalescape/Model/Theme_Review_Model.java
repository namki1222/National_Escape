package blackcap.nationalescape.Model;

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
    public Theme_Review_Model(android.app.Activity activity, String user_pk, String review_user_pk, String nickname, String grade, String content, String date, String theme_pk, String level, String status, String time, String hint, String experience, String line){
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
}
