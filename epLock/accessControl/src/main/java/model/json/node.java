package model.json;

import java.util.List;

/**
 * Created by admin on 2017/4/21.
 */
public class node {

    /**
     * sign : 1
     * data : [{"title":"one","isflag":true,"text":"dfs"},{"title":"two","isflag":true,"text":"dfs"},{"title":"one","isflag":true,"text":"dfs"}]
     */

    private String sign;
    private List<theNode> data;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public List<theNode> getData() {
        return data;
    }

    public void setData(List<theNode> data) {
        this.data = data;
    }

    public static class theNode {
        /**
         * title : one
         * isflag : true
         * text : dfs
         */

        private String title;
        private boolean isflag;
        private String text;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean isIsflag() {
            return isflag;
        }

        public void setIsflag(boolean isflag) {
            this.isflag = isflag;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
