package top.kmacro.blog.model.vo.post;

public class PhoneSearchVo {
    private String phone;
    private Integer pageSize;
    private Integer pageNum;

    public PhoneSearchVo() {
        pageSize = 6;
        pageNum = 1;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }
}
