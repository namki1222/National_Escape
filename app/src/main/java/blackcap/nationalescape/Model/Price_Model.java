package blackcap.nationalescape.Model;

public class Price_Model {
    private android.app.Activity Activity;
    private String Title;
    private String Price;
    public Price_Model(android.app.Activity activity, String title, String price){
        this.Activity = activity;
        this.Title = title;
        this.Price = price;
    }
    public android.app.Activity getActivity(){return Activity;}
    public String getPrice(){return Price;}
    public String getTitle() {
        return Title;
    }
    public void setPrice(String price){
        this.Price = price;
    }
    public void setTitle(String title){
        this.Title = title;
    }
}
