package blackcap.nationalescape.Model;

public class Manager_Img_Model {
    private int Sever_Index;
    private String Category;
    private String Path;
    public Manager_Img_Model(int sever_Index, String category, String path){
        this.Sever_Index = sever_Index;
        this.Category = category;
        this.Path = path;

    }
    public int getSever_Index(){return Sever_Index;}
    public String getCategory() {
        return Category;
    }
    public String getPath(){return Path;}
}
