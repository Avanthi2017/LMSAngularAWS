package com.gcit.lbms.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gcit.lbms.dao.BookDAO;
import com.gcit.lbms.dao.BookLoansDAO;
import com.gcit.lbms.dao.BorrowerDAO;
import com.gcit.lbms.dao.BranchDAO;
import com.gcit.lbms.entity.BookLoan;
import com.gcit.lbms.entity.Borrower;
import com.gcit.lbms.exception.IllegalNameException;

@RestController
public class BorrowerService {
	@Autowired
	BorrowerDAO bdao;
	@Autowired
	BookLoansDAO bldao;
	@Autowired
	BookDAO bookdao;
	@Autowired
	BranchDAO branchdao;

	@RequestMapping(value = "/initBorrower", method = RequestMethod.GET, produces = "application/json")
	public Borrower initBorrower() {
		return new Borrower();
	}

	@RequestMapping(value = "/searchBorrowers/{borrowerName}", method = RequestMethod.GET, produces = "application/json")
	public List<Borrower> getBorrowersByName(@PathVariable String borrowerName) {

		try {
			return bdao.readAllBorrowersbyName(borrowerName);
		} catch (ClassNotFoundException | SQLException | IllegalNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/viewActiveBorrowers", method = RequestMethod.GET, produces = "application/json")
	public List<Borrower> viewActiveBorrowers() {
		try {
			List<Borrower> borrowers = bdao.readAllBorrowers();
			for (int i = 0; i < borrowers.size(); i++) {
				List<BookLoan> bookLoans = bldao.readActiveBookLoansbyCardNo(borrowers.get(i).getCardNo());
				if (bookLoans == null || bookLoans.isEmpty()) {
					borrowers.remove(i);
				} else {
					for (int j = 0; j < bookLoans.size(); j++) {
						BookLoan bookLoan = new BookLoan();
						bookLoan.setBook(bookdao.readBookById(bookLoans.get(j).getBook().getBookId()));
						bookLoan.setBranch(branchdao.readBranchById(bookLoans.get(j).getBranch().getBranchId()));
						bookLoan.setBorrower(bdao.readBorrowerByCardNo(bookLoans.get(j).getBorrower().getCardNo()));
						bookLoan.setDateIn(bookLoans.get(j).getDateIn());
						bookLoan.setDateOut(bookLoans.get(j).getDateOut());
						bookLoan.setDueDate(bookLoans.get(j).getDueDate());
						bookLoans.set(j, bookLoan);
					}
					borrowers.get(i).setBookloans(bookLoans);
				}
				
			}
			return borrowers;
		} catch (ClassNotFoundException | SQLException | IllegalNameException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/viewBorrowers", method = RequestMethod.GET, produces = "application/json")
	public List<Borrower> viewBorrowers() {
		try {
			List<Borrower> borrowers = bdao.readAllBorrowers();
			for (int i = 0; i < borrowers.size(); i++) {
				List<BookLoan> bookLoans = bldao.readLoansByCardNo(borrowers.get(i).getCardNo());
				for (int j = 0; j < bookLoans.size(); j++) {
					BookLoan bookLoan = new BookLoan();
					bookLoan.setBook(bookdao.readBookById(bookLoans.get(j).getBook().getBookId()));
					bookLoan.setBranch(branchdao.readBranchById(bookLoans.get(j).getBranch().getBranchId()));
					bookLoan.setBorrower(bdao.readBorrowerByCardNo(bookLoans.get(j).getBorrower().getCardNo()));
					bookLoan.setDateIn(bookLoans.get(j).getDateIn());
					bookLoan.setDateOut(bookLoans.get(j).getDateOut());
					bookLoan.setDueDate(bookLoans.get(j).getDueDate());
					bookLoans.set(j, bookLoan);
				}
				borrowers.get(i).setBookloans(bookLoans);
			}
			return borrowers;
		} catch (ClassNotFoundException | SQLException | IllegalNameException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/viewBorrowerByCardNo/{cardNo}", method = RequestMethod.GET, produces = "application/json")
	public Borrower readBorrowerByCardNo(@PathVariable Integer cardNo) {
		try {
			Borrower borrower = bdao.readBorrowerByCardNo(cardNo);
			List<BookLoan> bookLoans = bldao.readActiveBookLoansbyCardNo(cardNo);
			if (bookLoans.size() <= 0) {
				return borrower;
			}
			for (int i = 0; i < bookLoans.size(); i++) {
				BookLoan bookLoan = new BookLoan();
				bookLoan.setBook(bookdao.readBookById(bookLoans.get(i).getBook().getBookId()));
				bookLoan.setBranch(branchdao.readBranchById(bookLoans.get(i).getBranch().getBranchId()));
				bookLoan.setBorrower(bdao.readBorrowerByCardNo(cardNo));
				bookLoan.setDateIn(bookLoans.get(i).getDateIn());
				bookLoan.setDateOut(bookLoans.get(i).getDateOut());
				bookLoan.setDueDate(bookLoans.get(i).getDueDate());
				bookLoans.set(i, bookLoan);
			}
			borrower.setBookloans(bookLoans);
			return borrower;

		} catch (ClassNotFoundException | SQLException | IllegalNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	@Transactional
	@RequestMapping(value = "/addBorrower", method = RequestMethod.POST, consumes = "application/json")
	public void addBorrower(@RequestBody Borrower borrower) {
		try {
			bdao.addBorrower(borrower);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Transactional
	@RequestMapping(value = "/updateBorrower", method = RequestMethod.POST, consumes = "application/json")
	public void updateBorrower(@RequestBody Borrower borrower) {
		try {
			bdao.updateBorrower(borrower);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Transactional
	@RequestMapping(value = "/deleteBorrower", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public void deleteBorrower(@RequestBody Borrower borrower) {
		try {
			bdao.deleteBorrower(borrower);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
