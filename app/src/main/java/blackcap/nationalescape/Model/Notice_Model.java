package blackcap.nationalescape.Model;

public class Notice_Model {
    private android.app.Activity Activity;
    private String Notice_Pk;
    private String Title;
    private String Contents;
    private String Date;
    public Notice_Model(android.app.Activity activity, String notice_Pk, String title, String contents, String date){
        this.Activity = activity;
        this.Notice_Pk = notice_Pk;
        this.Title = title;
        this.Contents = contents;
        this.Date = date;
    }
    public android.app.Activity getActivity(){return Activity;}
    public String getNotice_Pk(){return Notice_Pk;}
    public String getTitle() {
        return Title;
    }
    public String getContents() {
        return Contents;
    }
    public String getDate(){return Date;}
}

