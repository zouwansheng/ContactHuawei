package bean;

import java.util.List;

/**
 * Created by zws on 2017/8/3.
 */

public class TeamBean {
    /**
     * jsonrpc : 2.0
     * id : null
     * result : [{"team_id":7,"name":"其他"},{"team_id":3,"name":"内销团队"},{"team_id":5,"name":"外销团队"},{"team_id":2,"name":"电商团队"},{"team_id":6,"name":"跨境电商"},{"team_id":4,"name":"阿里团队"}]
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
         * team_id : 7
         * name : 其他
         */

        private int team_id;
        private String name;

        public int getTeam_id() {
            return team_id;
        }

        public void setTeam_id(int team_id) {
            this.team_id = team_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
