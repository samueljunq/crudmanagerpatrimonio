package model;

public class Asset {
	private int id;
	private String name, category, location, acquisitionValue;
	private Company company;

	public Asset() {
		this(0);
	}

	public Asset(int id) {
		this.id = id;
		setName("");
		setCategory("");
		setLocation("");
		setAcquisitionValue("");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	

	public String getAcquisitionValue() {
		return acquisitionValue;
	}

	public void setAcquisitionValue(String acquisitionValue) {
		this.acquisitionValue = acquisitionValue;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
}
