package bean;

import java.util.List;

/**
 * Created by zws on 2017/8/3.
 */

public class SalemanBean {
    /**
     * jsonrpc : 2.0
     * id : null
     * result : [{"partner_id":1,"name":"Administrator"},{"partner_id":215,"name":"Jason Lu"},{"partner_id":193,"name":"乔丹"},{"partner_id":210,"name":"何婷"},{"partner_id":119,"name":"倪建"},{"partner_id":189,"name":"岳红"},{"partner_id":190,"name":"张秀"},{"partner_id":194,"name":"张笛"},{"partner_id":170,"name":"李影"},{"partner_id":178,"name":"李江"},{"partner_id":182,"name":"李状"},{"partner_id":116,"name":"李靖"},{"partner_id":205,"name":"熊伟"},{"partner_id":173,"name":"薛飞"},{"partner_id":171,"name":"袁园"},{"partner_id":208,"name":"辛瑶"},{"partner_id":106,"name":"郭红"},{"partner_id":216,"name":"马婷"},{"partner_id":145,"name":"陶臻Allen"},{"partner_id":95,"name":"陶燕Cathy"},{"partner_id":196,"name":"曹媛Ceary"},{"partner_id":101,"name":"陈月Daisy"},{"partner_id":150,"name":"孟娜Dora"},{"partner_id":157,"name":"陈健Eli"},{"partner_id":104,"name":"陈月Emily"},{"partner_id":112,"name":"张成Jeff"},{"partner_id":114,"name":"徐亚Jerry"},{"partner_id":143,"name":"朱燕July"},{"partner_id":131,"name":"朱聪Kelly"},{"partner_id":130,"name":"梁宇Leo"},{"partner_id":201,"name":"钱露Lucy"},{"partner_id":124,"name":"杨进Milo"},{"partner_id":103,"name":"吴勇Simon"},{"partner_id":100,"name":"唐诗Taylor"},{"partner_id":159,"name":"郑旺Tony"},{"partner_id":92,"name":"魏林William"},{"partner_id":84,"name":"徐伟Xuwei"},{"partner_id":82,"name":"颜靖Yan"},{"partner_id":211,"name":"李杰jay"},{"partner_id":179,"name":"洪丽lily"},{"partner_id":212,"name":"仲丹丹"},{"partner_id":97,"name":"仲传丽"},{"partner_id":169,"name":"倪春梅"},{"partner_id":185,"name":"冯秀华"},{"partner_id":77,"name":"刘振华"},{"partner_id":172,"name":"包欢欢"},{"partner_id":152,"name":"吕子良"},{"partner_id":191,"name":"吴亚西"},{"partner_id":142,"name":"吴晶卫"},{"partner_id":118,"name":"周利梅"},{"partner_id":105,"name":"孙双玲"},{"partner_id":128,"name":"徐美琴"},{"partner_id":186,"name":"李海飞"},{"partner_id":126,"name":"熊孟菊"},{"partner_id":168,"name":"王东丽"},{"partner_id":183,"name":"管小会"},{"partner_id":188,"name":"赵东洋"},{"partner_id":214,"name":"赵耀耀"},{"partner_id":174,"name":"邓秀金"},{"partner_id":209,"name":"邵竹来"},{"partner_id":192,"name":"郝晨宇"},{"partner_id":108,"name":"郭华红"},{"partner_id":88,"name":"陈小娟"},{"partner_id":161,"name":"龙堂庆"},{"partner_id":141,"name":"徐文娟Ada"},{"partner_id":73,"name":"张成玉Alex"},{"partner_id":154,"name":"朱珍晶Alisa"},{"partner_id":146,"name":"李宜峰Allen"},{"partner_id":83,"name":"郭华根Andy"},{"partner_id":85,"name":"田巧慧Anna"},{"partner_id":87,"name":"环贝贝Bebe"},{"partner_id":89,"name":"翟杰鹏Beck"},{"partner_id":91,"name":"顾月珍Candy"},{"partner_id":199,"name":"朱云云Carrie"},{"partner_id":147,"name":"卢延玲Charlene"},{"partner_id":164,"name":"寇慧瑾Coco"},{"partner_id":151,"name":"王迎宾Dabin"},{"partner_id":134,"name":"唐博雯Eleven"},{"partner_id":74,"name":"季海燕Ella"},{"partner_id":160,"name":"黄举星Fox"},{"partner_id":78,"name":"王晓燕Grace"},{"partner_id":197,"name":"苑淑鑫Iris.yan"},{"partner_id":203,"name":"顾豪杰Jim"},{"partner_id":144,"name":"梁振元Judy"},{"partner_id":140,"name":"周宛菊Julie"},{"partner_id":129,"name":"孙吉胜Kevin"},{"partner_id":80,"name":"刘倩倩Kiki"},{"partner_id":166,"name":"梁馥薇Lavende"},{"partner_id":120,"name":"刘莹莹Linda"},{"partner_id":153,"name":"陈丽琴Liz"},{"partner_id":137,"name":"王廷廷Mark"},{"partner_id":127,"name":"顾昀晖Mark"},{"partner_id":122,"name":"刘燕含Nancy"},{"partner_id":202,"name":"薛家刚Nick"},{"partner_id":121,"name":"王晶晶Nicole"},{"partner_id":149,"name":"秦建刚Oscar"},{"partner_id":117,"name":"魏枫林Peggy"},{"partner_id":115,"name":"姚琰兰Phyllis"},{"partner_id":200,"name":"王树江River"},{"partner_id":113,"name":"刘国凯Sam"},{"partner_id":111,"name":"姚晓来Sammi"},{"partner_id":109,"name":"沈晶晶Sara"},{"partner_id":107,"name":"孙彩彩Serena"},{"partner_id":139,"name":"邹曼林Summer"},{"partner_id":102,"name":"高懿晴Sunny"},{"partner_id":204,"name":"张婷君Teresa"},{"partner_id":135,"name":"周尚云Tifa"},{"partner_id":96,"name":"武敏琪Vikki"},{"partner_id":90,"name":"韩莹莹Winnie"},{"partner_id":81,"name":"陆金建Yuki"},{"partner_id":176,"name":"周小月amy"},{"partner_id":198,"name":"郭春袭chunxi"},{"partner_id":180,"name":"陈中秋duke"},{"partner_id":195,"name":"郑成王frank"},{"partner_id":184,"name":"崔强坤joe"},{"partner_id":207,"name":"刘艳菊julia"},{"partner_id":175,"name":"周曼曼paris"},{"partner_id":213,"name":"王亚飞peter"},{"partner_id":177,"name":"王晓婷sophia"},{"partner_id":162,"name":"汪海洋young"}]
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
         * partner_id : 1
         * name : Administrator
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
