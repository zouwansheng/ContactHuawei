package bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zws on 2017/8/4.
 */

public class CompanyAllModel implements Serializable{
    private String company_name;
    private int company_id;
    private int saleman_id;
    private int saleteam_id;
    private int country_id;
    private int crm_source_id;
    private int source_id;
    private int tag_list;
    private String partner_type;
    private int star_cnt;
    private List<Object> series_ids;
    private List<LinkAndAddress> members;
    private int partner_lv;

    public int getPartner_lv() {
        return partner_lv;
    }

    public void setPartner_lv(int partner_lv) {
        this.partner_lv = partner_lv;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public int getSaleman_id() {
        return saleman_id;
    }

    public void setSaleman_id(int saleman_id) {
        this.saleman_id = saleman_id;
    }

    public int getSaleteam_id() {
        return saleteam_id;
    }

    public void setSaleteam_id(int saleteam_id) {
        this.saleteam_id = saleteam_id;
    }

    public int getCountry_id() {
        return country_id;
    }

    public void setCountry_id(int country_id) {
        this.country_id = country_id;
    }

    public int getCrm_source_id() {
        return crm_source_id;
    }

    public void setCrm_source_id(int crm_source_id) {
        this.crm_source_id = crm_source_id;
    }

    public int getSource_id() {
        return source_id;
    }

    public void setSource_id(int source_id) {
        this.source_id = source_id;
    }

    public int getTag_list() {
        return tag_list;
    }

    public void setTag_list(int tag_list) {
        this.tag_list = tag_list;
    }

    public String getPartner_type() {
        return partner_type;
    }

    public void setPartner_type(String partner_type) {
        this.partner_type = partner_type;
    }


    public int getStar_cnt() {
        return star_cnt;
    }

    public void setStar_cnt(int star_cnt) {
        this.star_cnt = star_cnt;
    }

    public List<Object> getSeries_ids() {
        return series_ids;
    }

    public void setSeries_ids(List<Object> series_ids) {
        this.series_ids = series_ids;
    }

    public List<LinkAndAddress> getMembers() {
        return members;
    }

    public void setMembers(List<LinkAndAddress> members) {
        this.members = members;
    }

    public static class LinkAndAddress implements Serializable{
        private String name;
        private String phone;
        private String email;
        private String street;
        private String type;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "name = "+name+" phone = "+phone;
        }
    }
}
