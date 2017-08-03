package bean;

import java.util.List;

/**
 * Created by zws on 2017/8/3.
 */

public class ComponyQueryBean {
    /**
     * jsonrpc : 2.0
     * id : null
     * result : [{"partner_id":2916,"name":"连爱-大唐微电子技术有限公司"}]
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
         * partner_id : 2916
         * name : 连爱-大唐微电子技术有限公司
         */

        private int partner_id;
        private String name;

        public int getPartner_id() {
            return partner_id;
        }

        public void setPartner_id(int partner_id) {
            this.partner_id = partner_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
