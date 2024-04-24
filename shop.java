package com.jsp.Model;
 
public class shop {

	 private String shopname;
	 private String address;
	 private int id ; 
	 private String gst;
	 private String owmername;
	 private long contact; //Make it non static to make multiple copies and used outside the class .
	
    
    public String getshopname() {
		return shopname;
	}
	public void setshopname(String name) {
		this.shopname = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGst() {
		return gst;
	}
	public void setGst(String gst) {
		this.gst = gst;
	}
	public String getOwmenamer() {
		return owmername;
	}
	public void setOwmername(String owmer) {
		this.owmername = owmer;
	}
	public long getContact() {
		return contact;
	}
	public void setContact(long contact) {
		this.contact = contact;
	}
	@Override
	public String toString() {
		return "shop [shopname=" + shopname + ", address=" + address + ", id=" + id + ", gst=" + gst + ", owmername="
				+ owmername + ", contact=" + contact + ", getshopname()=" + getshopname() + ", getAddress()="
				+ getAddress() + ", getId()=" + getId() + ", getGst()=" + getGst() + ", getOwmenamer()="
				+ getOwmenamer() + ", getContact()=" + getContact() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}

	
    


	
}
