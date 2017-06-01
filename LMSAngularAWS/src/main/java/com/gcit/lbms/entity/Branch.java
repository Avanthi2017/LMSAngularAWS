package com.gcit.lbms.entity;

import java.io.Serializable;
import java.util.List;

import com.gcit.lbms.exception.IllegalNameException;

public class Branch implements Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = -1296611266689497146L;
private String branchName;
private String branchAddress;
private int branchId;
private List<BookCopies>bookCopies;
private List<Borrower>borrowers;
/**
 * @return the branchName
 */
public String getBranchName() {
	return branchName;
}
/**
 * @return the borrowers
 */
public List<Borrower> getBorrowers() {
	return borrowers;
}
/**
 * @param borrowers the borrowers to set
 */
public void setBorrowers(List<Borrower> borrowers) {
	this.borrowers = borrowers;
}
/**
 * @param branchName the branchName to set
 */
public void setBranchName(String branchName) throws IllegalNameException {
	if (branchName!=null&&branchName.length() > 46) {
		throw new IllegalNameException("Name Should be less than 45 characters");
	}
	this.branchName = branchName;
}
/**
 * @return the branchAddress
 */
public String getBranchAddress() {
	return branchAddress;
}
/**
 * @param branchAddress the branchAddress to set
 */
public void setBranchAddress(String branchAddress) {
	this.branchAddress = branchAddress;
}
/**
 * @return the branchId
 */
public int getBranchId() {
	return branchId;
}
/**
 * @param branchId the branchId to set
 */
public void setBranchId(int branchId) {
	this.branchId = branchId;
}
public List<BookCopies> getBookCopies() {
	return bookCopies;
}
public void setBookCopies(List<BookCopies> bookCopies) {
	this.bookCopies = bookCopies;
}


}
