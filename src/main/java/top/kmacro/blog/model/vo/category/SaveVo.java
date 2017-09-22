package top.kmacro.blog.model.vo.category;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by Zhangkh on 2017-09-20.
 */
public class SaveVo {
    private String id;
    @NotEmpty(message = "-10:分类名不能为空")
    private String label;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
