package top.kmacro.blog.model.vo.comment;

import org.hibernate.validator.constraints.NotEmpty;

public class SaveVo {
    @NotEmpty(message = "-10:评论内容不能为空")
    private String content;
    private String tarUserId;
    private String postId;
    private String parCommentId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTarUserId() {
        return tarUserId;
    }

    public void setTarUserId(String tarUserId) {
        this.tarUserId = tarUserId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getParCommentId() {
        return parCommentId;
    }

    public void setParCommentId(String parCommentId) {
        this.parCommentId = parCommentId;
    }
}
