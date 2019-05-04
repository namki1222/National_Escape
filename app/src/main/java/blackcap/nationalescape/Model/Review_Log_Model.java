package blackcap.nationalescape.Model;

public class Review_Log_Model {
    private android.app.Activity Activity;
    private String Company_Name;
    private String Theme_Name;
    private String Grade;
    private String Content;
    private String Level;
    private String Status;
    private String Time;
    private String Hint;
    public Review_Log_Model(android.app.Activity activity, String Company_Name, String Theme_Name, String grade, String content, String level, String status, String time, String hint){
        this.Company_Name = Company_Name;
        this.Theme_Name = Theme_Name;
        this.Activity = activity;
        this.Grade = grade;
        this.Content = content;
        this.Level = level;
        this.Status = status;
        this.Time = time;
        this.Hint = hint;
    }
    public android.app.Activity getActivity(){return Activity;}
    public String getCompany_Name(){
        return Company_Name;
    }
    public String getTheme_Name(){
        return Theme_Name;
    }
    public String getGrade() {
        return Grade;
    }
    public String getContent() {
        return Content;
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
}
