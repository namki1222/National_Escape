package blackcap.nationalescape.Model;

public class Theme_Model {
    private android.app.Activity Activity;
    private String Theme_Pk;
    private String Company_Pk;
    private String Img;
    private String Title;
    private String Intro;
    private String Category;
    private String Grade;
    private String Level;
    private String Person;
    private String Tool;
    private String Theme_Activity;
    public Theme_Model(android.app.Activity activity, String theme_Pk, String company_Pk, String img, String title, String intro, String category, String grade, String level, String person, String tool, String theme_activity){
        this.Activity = activity;
        this.Theme_Pk = theme_Pk;
        this.Company_Pk = company_Pk;
        this.Img = img;
        this.Title = title;
        this.Intro = intro;
        this.Category = category;
        this.Grade = grade;
        this.Level = level;
        this.Person = person;
        this.Tool = tool;
        this.Theme_Activity = theme_activity;
    }
    public android.app.Activity getActivity(){return Activity;}
    public String getTheme_Pk(){return Theme_Pk;}
    public String getCompany_Pk(){return Company_Pk;}
    public String getImg() {
        return Img;
    }
    public String getTitle(){
        return Title;
    }
    public String getIntro() {
        return Intro;
    }
    public String getCategory(){return Category;}
    public String getGrade(){
        return Grade;
    }
    public String getLevel(){return Level;}
    public String getPerson(){return Person;}
    public String getTool(){return Tool;}
    public String getTheme_Activity(){return Theme_Activity;}
}
