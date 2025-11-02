package Food.dto.member;

public class FavoriteDTO {
	private Integer favoriteID;
	private String vendorName;
	private String logoUrl;
	private String styleName;
	private Integer vendorId;

	public FavoriteDTO(Integer favoriteID, String vendorName, String logoUrl, String styleName, Integer vendorId) {
		this.favoriteID = favoriteID;
		this.vendorName = vendorName;
		this.logoUrl = logoUrl;
		this.styleName = styleName;
		this.vendorId = vendorId;
	}

	// Getter & Setter
	public Integer getFavoriteID() {
		return favoriteID;
	}

	public String getVendorName() {
		return vendorName;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public String getStyleName() {
		return styleName;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setFavoriteID(Integer favoriteID) {
		this.favoriteID = favoriteID;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public void setStyleName(String styleName) {
		this.styleName = styleName;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
}
