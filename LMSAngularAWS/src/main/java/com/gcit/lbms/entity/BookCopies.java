package com.gcit.lbms.entity;

import java.io.Serializable;

public class BookCopies implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8698462912587446557L;
	 private int noOfCopies;
	 private Book book;
	 private Branch branch;
	
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public Branch getBranch() {
		return branch;
	}
	public void setBranch(Branch branch) {
		this.branch = branch;
	}
	public int getNoOfCopies() {
		return noOfCopies;
	}
	/**
	 * @param noOfCopies the noOfCopies to set
	 */
	public void setNoOfCopies(int noOfCopies) {
		this.noOfCopies = noOfCopies;
	}
	@Override
	public String toString() {
		return "BookCopies [noOfCopies=" + noOfCopies + ", book=" + book + ", branch=" + branch + "]";
	}
	

	 
}