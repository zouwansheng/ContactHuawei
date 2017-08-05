package bean;

import java.util.List;

/**
 * Created by zws on 2017/8/5.
 */

public class CommitAllMessageBean {
    /**
     * jsonrpc : 2.0
     * id : null
     * error : {"message":"Odoo Server Error","code":200,"data":{"debug":"Traceback (most recent call last):\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 638, in _handle_exception\n    return super(JsonRequest, self)._handle_exception(exception)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 675, in dispatch\n    result = self._call_function(**self.params)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 331, in _call_function\n    return checked_call(self.db, *args, **kwargs)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/service/model.py\", line 119, in wrapper\n    return f(dbname, *args, **kwargs)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 324, in checked_call\n    result = self.endpoint(*a, **kw)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 933, in __call__\n    return self.method(*args, **kw)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 504, in response_wrap\n    response = f(*args, **kw)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/linklovingaddons/linkloving_oa_api/controllers/name_card_controller.py\", line 92, in add_partners\n    NameCardController.add_one_partner(partner)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/linklovingaddons/linkloving_oa_api/controllers/name_card_controller.py\", line 162, in add_one_partner\n    'product_series_ids': product_series or [],\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/linklovingaddons/linkloving_crm/models/res_partner.py\", line 112, in write\n    raise UserError(u'此' + item_type + u'已绑定公司，请确认')\nUserError: (u'\\u6b64\\u516c\\u53f8\\u540d\\u79f0\\u5df2\\u7ed1\\u5b9a\\u516c\\u53f8\\uff0c\\u8bf7\\u786e\\u8ba4', None)\n","exception_type":"user_error","message":"此公司名称已绑定公司，请确认\nNone","name":"odoo.exceptions.UserError","arguments":["此公司名称已绑定公司，请确认",null]}}
     */

    private String jsonrpc;
    private Object id;
    private ErrorBean error;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    private boolean result;

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

    public ErrorBean getError() {
        return error;
    }

    public void setError(ErrorBean error) {
        this.error = error;
    }

    public static class ErrorBean {
        /**
         * message : Odoo Server Error
         * code : 200
         * data : {"debug":"Traceback (most recent call last):\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 638, in _handle_exception\n    return super(JsonRequest, self)._handle_exception(exception)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 675, in dispatch\n    result = self._call_function(**self.params)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 331, in _call_function\n    return checked_call(self.db, *args, **kwargs)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/service/model.py\", line 119, in wrapper\n    return f(dbname, *args, **kwargs)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 324, in checked_call\n    result = self.endpoint(*a, **kw)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 933, in __call__\n    return self.method(*args, **kw)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py\", line 504, in response_wrap\n    response = f(*args, **kw)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/linklovingaddons/linkloving_oa_api/controllers/name_card_controller.py\", line 92, in add_partners\n    NameCardController.add_one_partner(partner)\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/linklovingaddons/linkloving_oa_api/controllers/name_card_controller.py\", line 162, in add_one_partner\n    'product_series_ids': product_series or [],\n  File \"/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/linklovingaddons/linkloving_crm/models/res_partner.py\", line 112, in write\n    raise UserError(u'此' + item_type + u'已绑定公司，请确认')\nUserError: (u'\\u6b64\\u516c\\u53f8\\u540d\\u79f0\\u5df2\\u7ed1\\u5b9a\\u516c\\u53f8\\uff0c\\u8bf7\\u786e\\u8ba4', None)\n","exception_type":"user_error","message":"此公司名称已绑定公司，请确认\nNone","name":"odoo.exceptions.UserError","arguments":["此公司名称已绑定公司，请确认",null]}
         */

        private String message;
        private int code;
        private DataBean data;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * debug : Traceback (most recent call last):
             File "/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py", line 638, in _handle_exception
             return super(JsonRequest, self)._handle_exception(exception)
             File "/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py", line 675, in dispatch
             result = self._call_function(**self.params)
             File "/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py", line 331, in _call_function
             return checked_call(self.db, *args, **kwargs)
             File "/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/service/model.py", line 119, in wrapper
             return f(dbname, *args, **kwargs)
             File "/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py", line 324, in checked_call
             result = self.endpoint(*a, **kw)
             File "/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py", line 933, in __call__
             return self.method(*args, **kw)
             File "/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/odoo/http.py", line 504, in response_wrap
             response = f(*args, **kw)
             File "/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/linklovingaddons/linkloving_oa_api/controllers/name_card_controller.py", line 92, in add_partners
             NameCardController.add_one_partner(partner)
             File "/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/linklovingaddons/linkloving_oa_api/controllers/name_card_controller.py", line 162, in add_one_partner
             'product_series_ids': product_series or [],
             File "/Users/charynbryant/Desktop/CharlieWorkSpace/Python_Workspace/odoo-10.0/linklovingaddons/linkloving_crm/models/res_partner.py", line 112, in write
             raise UserError(u'此' + item_type + u'已绑定公司，请确认')
             UserError: (u'\u6b64\u516c\u53f8\u540d\u79f0\u5df2\u7ed1\u5b9a\u516c\u53f8\uff0c\u8bf7\u786e\u8ba4', None)

             * exception_type : user_error
             * message : 此公司名称已绑定公司，请确认
             None
             * name : odoo.exceptions.UserError
             * arguments : ["此公司名称已绑定公司，请确认",null]
             */

            private String debug;
            private String exception_type;
            private String message;
            private String name;
            private List<String> arguments;

            public String getDebug() {
                return debug;
            }

            public void setDebug(String debug) {
                this.debug = debug;
            }

            public String getException_type() {
                return exception_type;
            }

            public void setException_type(String exception_type) {
                this.exception_type = exception_type;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<String> getArguments() {
                return arguments;
            }

            public void setArguments(List<String> arguments) {
                this.arguments = arguments;
            }
        }
    }
}
