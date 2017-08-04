package bean;

import java.util.List;

/**
 * Created by zws on 2017/8/4.
 */

public class TagBean {
    /**
     * jsonrpc : 2.0
     * id : null
     * result : [{"category_id":1,"name":"供应商"},{"category_id":2,"name":"客户"}]
     */

    private String jsonrpc;
    private Object id;
    private List<ResultBean> result;

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * category_id : 1
         * name : 供应商
         */

        private int category_id;
        private String name;

        public int getCategory_id() {
            return category_id;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
