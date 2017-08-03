package bean;

import java.util.List;

/**
 * Created by zws on 2017/8/3.
 */

public class OriginBean {
    /**
     * jsonrpc : 2.0
     * id : null
     * result : [{"src_id":1,"name":"2017年之前的展会"},{"src_id":2,"name":"Alibaba国际站"},{"src_id":3,"name":"2017年香港玩具展"},{"src_id":4,"name":"2017年纽伦堡玩具展"},{"src_id":5,"name":"2017年春交会"},{"src_id":6,"name":"2017年秋交会"},{"src_id":7,"name":"2017年ASD春季展"},{"src_id":8,"name":"2017年香港礼品展"},{"src_id":9,"name":"老客户"},{"src_id":10,"name":"公司官网"},{"src_id":11,"name":"2017香港礼品展"},{"src_id":12,"name":"其他"},{"src_id":13,"name":"自己开发"},{"src_id":14,"name":"内销展会"},{"src_id":15,"name":"客户主动联系"}]
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
         * src_id : 1
         * name : 2017年之前的展会
         */

        private int src_id;
        private String name;

        public int getSrc_id() {
            return src_id;
        }

        public void setSrc_id(int src_id) {
            this.src_id = src_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
