package bean;

import java.util.List;

/**
 * Created by zws on 2017/8/3.
 */

public class ChannelBean {
    /**
     * jsonrpc : 2.0
     * id : null
     * result : [{"source_id":1,"name":"外贸公司"},{"source_id":2,"name":"进口批发商"},{"source_id":3,"name":"邮购"},{"source_id":4,"name":"在线销售"},{"source_id":5,"name":"连锁零售商"},{"source_id":6,"name":"品牌商"},{"source_id":7,"name":"出版商"},{"source_id":8,"name":"其他"}]
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
         * source_id : 1
         * name : 外贸公司
         */

        private int source_id;
        private String name;

        public int getSource_id() {
            return source_id;
        }

        public void setSource_id(int source_id) {
            this.source_id = source_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
