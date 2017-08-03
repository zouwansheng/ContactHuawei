package bean;

import java.util.List;

/**
 * Created by zws on 2017/8/3.
 */

public class CountryBean {

    /**
     * jsonrpc : 2.0
     * id : null
     * result : [{"country_id":3,"name":"Afghanistan, Islamic State of"},
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
         * country_id : 3
         * name : Afghanistan, Islamic State of
         */

        private int country_id;
        private String name;

        public int getCountry_id() {
            return country_id;
        }

        public void setCountry_id(int country_id) {
            this.country_id = country_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}

