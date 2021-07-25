package app.core.entities;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Coupon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
//	private int companyId;
	@Enumerated(EnumType.STRING)
	private Category category;
	private String title;
	private String description;
	private LocalDate startDate;
	private LocalDate endDate;
	private String stringEndDate;
	private String stringStartDate;
	private int amount;
	private double price;
	private String imageName;
	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "company_id")
	private Company company;
	@ManyToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH, }, fetch = FetchType.LAZY)
	@JoinTable(name = "customers_vs_coupons", joinColumns = @JoinColumn(name = "coupon_id"), inverseJoinColumns = @JoinColumn(name = "customer_id"))
	@JsonIgnore
	private List<Customer> customers;

	public Coupon() {
	}

	public Coupon(Category category, String title, String description, LocalDate startDate, LocalDate endDate,
			int amount, double price, Company company) {
		this.category = category;
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.price = price;
		this.company = company;
	}

	
	public Coupon(int id, Category category, String title, String description, LocalDate startDate, LocalDate endDate,
			int amount, double price, Company company) {
		this.id = id;
		this.category = category;
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.price = price;
		this.company = company;
	}

	public Coupon(Category category, String title, String description, LocalDate startDate, LocalDate endDate,
			int amount, double price, String image, Company company) {
		this.category = category;
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.price = price;
		this.imageName = image;
		this.company = company;
	}
	
	public void convertDatesFromStringToLocalDate() {
		setStartDate(LocalDate.parse(stringStartDate));
		setEndDate(LocalDate.parse(stringEndDate));
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStringEndDate() {
		return stringEndDate;
	}
	
	public void setStringEndDate(String stringEndDate) {
		this.stringEndDate = stringEndDate;
	}
	
	public String getStringStartDate() {
		return stringStartDate;
	}
	
	public void setStringStartDate(String stringStartDate) {
		this.stringStartDate = stringStartDate;
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

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setImageName(String image) {
		this.imageName = image;
	}

	public void setCompanyID(int companyID) {
		this.company.setId(companyID);
	}

	public int getCompanyID() {
		return company.getId();
	}

	public int getId() {
		return id;
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

	public LocalDate getStartDate() {
		return startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public double getPrice() {
		return price;
	}

	public String getImageName() {
		return imageName;
	}

	@Override
	public String toString() {
		return "Coupon [id=" + id + ", category=" + category + ", title=" + title + ", description=" + description
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", amount=" + amount + ", price=" + price
				+ ", imageName=" + imageName + "]";
	}

}
