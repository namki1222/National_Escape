package blackcap.nationalescape.Model;

public class Location_Model {
    private String Title;
    private String Gps_x;
    private String Gps_y;
    public Location_Model(String title, String gps_x, String gps_y){
        this.Title = title;
        this.Gps_x = gps_x;
        this.Gps_y = gps_y;
    }
    public String getTitle() {
        return Title;
    }
    public String getGps_x() {
        return Gps_x;
    }
    public String getGps_y(){return Gps_y;}
}
