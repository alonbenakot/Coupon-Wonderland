package app.core.entities;


import org.springframework.web.multipart.MultipartFile;


public class CouponImage {

	private int id;
	private Category category;
	private String title;
	private String description;
	private String startDate;
	private String endDate;
	private int amount;
	private double price;
	private MultipartFile image;





	@Override
	public String toString() {
		return "CouponImage [id=" + id + ", category=" + category + ", title=" + title + ", description=" + description
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", amount=" + amount + ", price=" + price
				+ ", image=" + image+ "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}




	public CouponImage() {
	}


	public CouponImage(Category category, String title, String description, String startDate, String endDate,
			int amount, double price, MultipartFile image) {
		this.category = category;
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.price = price;
		this.image = image;
	}


	public void setCategory(Category category) {
		this.category = category;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


	public void setAmount(int amount) {
		this.amount = amount;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public void setImage(MultipartFile image) {
		this.image = image;
	}


	public Category getCategory() {
		return category;
	}


	public String getTitle() {
		return title;
	}


	public String getDescription() {
		return description;
	}


	public String getStartDate() {
		return startDate;
	}


	public String getEndDate() {
		return endDate;
	}


	public int getAmount() {
		return amount;
	}


	public double getPrice() {
		return price;
	}


	public MultipartFile getImage() {
		return image;
	}

}
