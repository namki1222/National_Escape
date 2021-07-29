package blackcap.nationalescape.Model;

public class Photos_models {
    private android.app.Activity Activity;
    private String Id;
    private String Name;
    public Photos_models (android.app.Activity activity, String id, String name){
        this.Activity = activity;
        this.Id =id;
        this.Name =name;
    }

    public String getName() {
        return Name;
    }

    public android.app.Activity getActivity() {
        return Activity;
    }

    public String getId() {
        return Id;
    }
}
