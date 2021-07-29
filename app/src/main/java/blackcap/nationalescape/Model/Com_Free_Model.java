package blackcap.nationalescape.Model;

import android.app.Activity;

public class Com_Free_Model {
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

    public Com_Free_Model(android.app.Activity activity, String id, String user_pk, String nickname, String grade, String title, String category, String commentCount, String isLike, String diff, String createdAt){
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
