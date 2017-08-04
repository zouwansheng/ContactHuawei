package contactui;

import java.io.Serializable;
import java.util.List;

/**
 * 列表bean类
 * */
public class SortModel implements Serializable{
	
	public String name;
	public String sortLetters;//首字母
	public List<String> mobilePhone;
	public String organization;
	public String email_v2;
	public List<String> postal_address_v2;
	public boolean isChecked;
	public String website;
	public int raw_contact_id;
	private String linkType;

	public String getLinkType() {
		return linkType;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}

	public int getRaw_contact_id() {
		return raw_contact_id;
	}

	public void setRaw_contact_id(int raw_contact_id) {
		this.raw_contact_id = raw_contact_id;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getEmail_v2() {
		return email_v2;
	}

	public void setEmail_v2(String email_v2) {
		this.email_v2 = email_v2;
	}

	public List<String> getPostal_address_v2() {
		return postal_address_v2;
	}

	public void setPostal_address_v2(List<String> postal_address_v2) {
		this.postal_address_v2 = postal_address_v2;
	}

	public boolean getChecked() {
		return isChecked;
	}

	public void setChecked(boolean checked) {
		isChecked = checked;
	}

	public List<String> getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(List<String> mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSortLetters() {
		return sortLetters;
	}
	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}


	/**
	 * public String name;
	 public String sortLetters;//首字母
	 public List<String> mobilePhone;
	 public String organization;
	 public String email_v2;
	 public String postal_address_v2;
	 public boolean isChecked;
	 public String website;
	 * */
	@Override
	public String toString() {
		return "name = "+name+" mobilePhone = "+mobilePhone.get(0)+" organization = "+organization
				+" email_v2 = "+email_v2+" postal_address_v2 = "+postal_address_v2+" website = "+website;
	}
}
