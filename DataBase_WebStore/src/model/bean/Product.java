package model.bean;

import java.awt.Image;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Product {
	private int ID;
	private String name;
	private int price;
	private String category;
	private Date addedDate;
	private int availableCount;
	private int soldCount;
	private Image image;

	public Product(int iD, String name, int price, String category, Date addedDate, int availableCount, int soldCount) {
		ID = iD;
		this.name = name;
		this.price = price;
		this.category = category;
		this.addedDate = addedDate;
		this.availableCount = availableCount;
		this.soldCount = soldCount;
		try{
			image = ImageIO.read(this.getClass().getResource("/picture/" + this.name +".png"));
		} catch (Exception e){
			try {
				image = ImageIO.read(this.getClass().getResource("/picture/noprev.png"));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Date getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}

	public int getAvailableCount() {
		return availableCount;
	}

	public void setAvailableCount(int availableCount) {
		this.availableCount = availableCount;
	}

	public int getSoldCount() {
		return soldCount;
	}

	public void setSoldCount(int soldCount) {
		this.soldCount = soldCount;
	}
	
	public ImageIcon getImageBySize(int size) {
		return new ImageIcon(image.getScaledInstance(size, size, 0));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ID;
		result = prime * result + ((addedDate == null) ? 0 : addedDate.hashCode());
		result = prime * result + availableCount;
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + price;
		result = prime * result + soldCount;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (ID != other.ID)
			return false;
		if (addedDate == null) {
			if (other.addedDate != null)
				return false;
		} else if (!addedDate.equals(other.addedDate))
			return false;
		if (availableCount != other.availableCount)
			return false;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (price != other.price)
			return false;
		if (soldCount != other.soldCount)
			return false;
		return true;
	}
	
	
}
