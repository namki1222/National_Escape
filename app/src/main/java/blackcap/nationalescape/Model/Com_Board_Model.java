package blackcap.nationalescape.Model;

public class Com_Board_Model {
    private android.app.Activity Activity;
    private String Id;
    private String User_pk;
    private String Nickname;
    private String Grade;
    private String Title;
    private String Category;
    private String CommentCount;
    private String IsLike;
    private String Diff;
    private String CreatedAt;
    private String Thumbnail;
    private String Company_name;
    private String Theme_name;
    private String LikeCount;
    public Com_Board_Model(android.app.Activity activity, String id, String user_pk, String nickname, String grade, String title, String category, String commentCount, String isLike, String diff, String createdAt, String thumbnail, String company_name, String theme_name, String likeCount){
        this.Activity = activity;
        this.Id = id;
        this.User_pk = user_pk;
        this.Nickname = nickname;
        this.Grade = grade;
        this.Title = title;
        this.Category = category;
        this.CommentCount = commentCount;
        this.IsLike = isLike;
        this.Diff = diff;
        this.CreatedAt = createdAt;
        this.Thumbnail = thumbnail;
        this.Company_name = company_name;
        this.Theme_name = theme_name;
        this.LikeCount = likeCount;
    }

    public String getLikeCount() {
        return LikeCount;
    }

    public String getCompany_name() {
        return Company_name;
    }

    public String getTheme_name() {
        return Theme_name;
    }

    public String getThumbnail() {
        return Thumbnail;
    }

    public String getTitle() {
        return Title;
    }

    public String getId() {
        return Id;
    }

    public android.app.Activity getActivity() {
        return Activity;
    }

    public String getDiff() {
        return Diff;
    }

    public String getCreatedAt() {
        return CreatedAt;
    }

    public String getCategory() {
        return Category;
    }

    public String getNickname() {
        return Nickname;
    }

    public String getCommentCount() {
        return CommentCount;
    }

    public String getGrade() {
        return Grade;
    }

    public String getIsLike() {
        return IsLike;
    }

    public String getUser_pk() {
        return User_pk;
    }
}
