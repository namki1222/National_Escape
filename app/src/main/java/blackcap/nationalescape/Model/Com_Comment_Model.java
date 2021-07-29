package blackcap.nationalescape.Model;

import android.app.Activity;

public class Com_Comment_Model {
    private android.app.Activity Activity;
    private String Id;
    private String BoardId;
    private String User_pk;
    private String Nickname;
    private String Grade;
    private String Content;
    private String Depth;
    private String CreatedAt;
    public Com_Comment_Model(android.app.Activity activity, String id, String boardId, String user_pk,String nickname, String grade,String content, String depth, String createdAt){
        this.Activity = activity;
        this.Id = id;
        this.BoardId = boardId;
        this.User_pk = user_pk;
        this.Nickname = nickname;
        this.Grade = grade;
        this.Content = content;
        this.Depth = depth;
        this.CreatedAt = createdAt;
    }

    public String getUser_pk() {
        return User_pk;
    }

    public String getGrade() {
        return Grade;
    }

    public String getNickname() {
        return Nickname;
    }

    public String getCreatedAt() {
        return CreatedAt;
    }

    public android.app.Activity getActivity() {
        return Activity;
    }

    public String getId() {
        return Id;
    }

    public String getContent() {
        return Content;
    }

    public String getDepth() {
        return Depth;
    }

    public String getBoardId() {
        return BoardId;
    }
}
