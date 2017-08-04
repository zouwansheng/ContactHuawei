package bean;

import java.util.List;

/**
 * Created by zws on 2017/8/4.
 */

public class LikeProductBean {

    /**
     * jsonrpc : 2.0
     * id : null
     * result : [{"series_id":1,"name":"zzzz"}]
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
         * series_id : 1
         * name : zzzz
         */

        private int series_id;
        private String name;

        public int getSeries_id() {
            return series_id;
        }

        public void setSeries_id(int series_id) {
            this.series_id = series_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
