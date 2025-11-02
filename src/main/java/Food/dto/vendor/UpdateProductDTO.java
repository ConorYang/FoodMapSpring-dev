package Food.dto.vendor;

import java.time.LocalDate;

public class UpdateProductDTO {
	private Integer stock;
	private LocalDate endDate;
	
	public UpdateProductDTO() {}
	public UpdateProductDTO(Integer stock, LocalDate endDate) {
		this.stock = stock;
		this.endDate = endDate;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

}
