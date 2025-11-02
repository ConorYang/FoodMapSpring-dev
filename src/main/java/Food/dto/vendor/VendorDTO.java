package Food.dto.vendor;

import java.io.Serializable;

public class VendorDTO implements Serializable {

	private Integer vendorId;
	private String vendorName;
	private Integer taxID;
	private String ownerName;
	private String contactName;
	private String contactTitle;
	private String contactTel;
	private String contactEmail;
	private String address;
	private String mapApi;
	private Integer vdCategoryID;
	private String logoURL;
	private String vdStatus;
	private Long accountId;

	public VendorDTO(Integer vendorId,
			String vendorName,
			String contactName,
			String contactTel,
			String address,
			String logoURL,
			String vdStatus) {
		this.vendorId = vendorId;
		this.vendorName = vendorName;
		this.contactName = contactName;
		this.contactTel = contactTel;
		this.address = address;
		this.logoURL = logoURL;
		this.vdStatus = vdStatus;
	}

	// JPQL constructor
	public VendorDTO(Integer vendorId, String vendorName, Integer taxID, String ownerName,
			String contactName, String contactTitle, String contactTel, String contactEmail,
			String address, String mapApi, Integer vdCategoryID, String logoURL, String vdStatus,
			Long accountId) {
		this.vendorId = vendorId;
		this.vendorName = vendorName;
		this.taxID = taxID;
		this.ownerName = ownerName;
		this.contactName = contactName;
		this.contactTitle = contactTitle;
		this.contactTel = contactTel;
		this.contactEmail = contactEmail;
		this.address = address;
		this.mapApi = mapApi;
		this.vdCategoryID = vdCategoryID;
		this.logoURL = logoURL;
		this.vdStatus = vdStatus;
		this.accountId = accountId;
	}

	public VendorDTO() {
	}

	// getter & setter
	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public Integer getTaxID() {
		return taxID;
	}

	public void setTaxID(Integer taxID) {
		this.taxID = taxID;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactTitle() {
		return contactTitle;
	}

	public void setContactTitle(String contactTitle) {
		this.contactTitle = contactTitle;
	}

	public String getContactTel() {
		return contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMapApi() {
		return mapApi;
	}

	public void setMapApi(String mapApi) {
		this.mapApi = mapApi;
	}

	public Integer getVdCategoryID() {
		return vdCategoryID;
	}

	public void setVdCategoryID(Integer vdCategoryID) {
		this.vdCategoryID = vdCategoryID;
	}

	public String getLogoURL() {
		return logoURL;
	}

	public void setLogoURL(String logoURL) {
		this.logoURL = logoURL;
	}

	public String getVdStatus() {
		return vdStatus;
	}

	public void setVdStatus(String vdStatus) {
		this.vdStatus = vdStatus;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
}