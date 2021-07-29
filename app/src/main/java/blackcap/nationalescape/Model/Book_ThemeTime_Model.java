package blackcap.nationalescape.Model;

import android.app.Activity;

public class Book_ThemeTime_Model {
    private android.app.Activity Activity;
    private String Time;
    private String Flag;
    private String Select;
    public Book_ThemeTime_Model(android.app.Activity activity, String time, String flag, String select){
        this.Activity = activity;
        this.Time = time;
        this.Flag = flag;
        this.Select = select;

    }

    public String getTime() {
        return Time;
    }

    public void setActivity(android.app.Activity activity) {
        Activity = activity;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getFlag() {
        return Flag;
    }

    public void setFlag(String flag) {
        Flag = flag;
    }

    public String getSelect() {
        return Select;
    }

    public void setSelect(String select) {
        Select = select;
    }
}
