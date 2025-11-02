package Food.entity.vendor;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "VD_Styles")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "details" })
public class VD_Styles {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "VD_StyleID")
	private Integer styleId;

	@Column(name = "StyleName")
	private String styleName;

	@Column(name = "Description")
	private String description;

	@OneToMany(mappedBy = "style", fetch = FetchType.LAZY)
	@JsonIgnoreProperties("style")
	private List<Details> details;

	public Integer getStyleId() {
		return styleId;
	}

	public void setStyleId(Integer styleId) {
		this.styleId = styleId;
	}

	public String getStyleName() {
		return styleName;
	}

	public void setStyleName(String styleName) {
		this.styleName = styleName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Details> getDetails() {
		return details;
	}

	public void setDetails(List<Details> details) {
		this.details = details;
	}
}