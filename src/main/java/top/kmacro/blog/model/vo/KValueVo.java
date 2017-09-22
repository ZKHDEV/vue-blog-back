package top.kmacro.blog.model.vo;

/**
 * Created by Zhangkh on 2017-09-14.
 */
public class KValueVo {
    private Object val;
    private Object txt;

    public KValueVo() {
    }

    public KValueVo(Object val, Object txt) {
        this.val = val;
        this.txt = txt;
    }

    public Object getVal() {

        return val;
    }

    public void setVal(Object val) {
        this.val = val;
    }

    public Object getTxt() {
        return txt;
    }

    public void setTxt(Object txt) {
        this.txt = txt;
    }
}
