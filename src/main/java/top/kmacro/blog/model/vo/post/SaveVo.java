package top.kmacro.blog.model.vo.post;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by Zhangkh on 2017-09-02.
 */
public class SaveVo {
    private String id;
    @NotEmpty(message = "-10:标题不能为空")
    @Length(max = 32, message = "-11:标题长度须小于32字节")
    private String title;
    @NotEmpty(message = "-20:正文不能为空")
    private String content;
    private String cover;
    @NotEmpty(message = "-30:摘要不能为空")
    @Length(max = 200, message = "-31:摘要长度须小于200字节")
    private String summary;
    private String[] tagList;
    private Byte type;
    private String[] cateIds;
    private String verDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String[] getTagList() {
        return tagList;
    }

    public void setTagList(String[] tagList) {
        this.tagList = tagList;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String[] getCateIds() {
        return cateIds;
    }

    public void setCateIds(String[] cateIds) {
        this.cateIds = cateIds;
    }

    public String getVerDate() {
        return verDate;
    }

    public void setVerDate(String verDate) {
        this.verDate = verDate;
    }
}
