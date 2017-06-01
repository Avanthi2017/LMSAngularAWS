package com.gcit.lbms.entity;

import java.io.Serializable;
import java.util.List;

import com.gcit.lbms.exception.IllegalNameException;

public class Borrower implements Serializable	{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4798436597151753962L;
	private int cardNo;
	private String name;
	private String address;
	private String phone;
	private List<BookLoan> bookloans;
	
	/**
	 * @return the cardNo
	 */
	public int getCardNo() {
		return cardNo;
	}
	/**
	 * @param cardNo the cardNo to set
	 */
	public void setCardNo(int cardNo) {
		this.cardNo = cardNo;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) throws IllegalNameException {
		if(name!=null&&name.length()>45){
			throw new IllegalNameException("Name Should be less than 45 characters");
		}
		this.name = name;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @return the bookloans
	 */
	public List<BookLoan> getBookloans() {
		return bookloans;
	}
	/**
	 * @param bookloans the bookloans to set
	 */
	public void setBookloans(List<BookLoan> bookloans) {
		this.bookloans = bookloans;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((bookloans == null) ? 0 : bookloans.hashCode());
		result = prime * result + cardNo;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Borrower other = (Borrower) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (bookloans == null) {
			if (other.bookloans != null)
				return false;
		} else if (!bookloans.equals(other.bookloans))
			return false;
		if (cardNo != other.cardNo)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		return true;
	}

}
