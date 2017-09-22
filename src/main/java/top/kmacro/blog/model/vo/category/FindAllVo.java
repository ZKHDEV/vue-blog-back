package top.kmacro.blog.model.vo.category;

/**
 * Created by Zhangkh on 2017-09-11.
 */
public class FindAllVo {
    private String id;
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

    public FindAllVo(String id, String label) {
        this.id = id;
        this.label = label;
    }
}
