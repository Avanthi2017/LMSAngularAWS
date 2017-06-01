package com.gcit.lbms.service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gcit.lbms.dao.BookLoansDAO;
import com.gcit.lbms.entity.Book;
import com.gcit.lbms.entity.BookLoan;
import com.gcit.lbms.entity.Borrower;
import com.gcit.lbms.entity.Branch;
import com.gcit.lbms.exception.IllegalNameException;

@RestController
public class BookLoanService {
	@Autowired
	BookLoansDAO bldao;

	@RequestMapping(value = "/initBookLoan", method = RequestMethod.GET, produces = "application/json")
	public BookLoan initBookLoan() {
		return new BookLoan();
	}

	@RequestMapping(value = "/viewBookLoans", method = RequestMethod.GET, produces = "application/json")
	public List<BookLoan> readAllBookLoans() {
		try {
			return bldao.readAllBookLoans();

		} catch (ClassNotFoundException | SQLException | IllegalNameException e) {
			e.printStackTrace();
		}
		return null;

	}

	@RequestMapping(value = "/readbookloansbyCardNo", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public List<BookLoan> readbookloansbyCardNo(int cardNo) {
		try {
			return bldao.readLoansByCardNo(cardNo);
		} catch (ClassNotFoundException | SQLException | IllegalNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/readActiveBookLoansbyCardNo/{cardNo}", method = RequestMethod.GET, produces = "application/json")
	public List<BookLoan> readActiveBookLoansbyCardNo(@PathVariable Integer cardNo) {
		try {
			return bldao.readActiveBookLoansbyCardNo(cardNo);
		} catch (ClassNotFoundException | SQLException | IllegalNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/readActiveBookLoansbyBookId", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public List<BookLoan> readActiveBookLoansbyBookId(int bookId) {
		try {
			return bldao.readActiveBookLoansbyBookId(bookId);
		} catch (ClassNotFoundException | SQLException | IllegalNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/readBookLoansbyCardNoAndBranchId", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public List<BookLoan> readBookLoansbyCardNoAndBranchId(int cardNo, int branchId) {
		try {
			return bldao.readLoansByCardNoAndBranchId(cardNo, branchId);
		} catch (ClassNotFoundException | SQLException | IllegalNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/readBookLoansbyBookIdAndBranchId", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public List<BookLoan> readBookLoansbyBookIdAndBranchId(int bookId, int branchId) {
		try {
			return bldao.readBookLoansbyBookIdAndBranchId(bookId, branchId);
		} catch (ClassNotFoundException | SQLException | IllegalNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/readBookLoansbyIds", method = RequestMethod.POST,consumes = "application/json", produces = "application/json")
	public BookLoan readBookLoansbyIds(@RequestBody BookLoan boookLoan) {
		try {
			return bldao.readBookLoanByIds(boookLoan);
		} catch (ClassNotFoundException | SQLException | IllegalNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Transactional
	@RequestMapping(value = "/addBookLoan", method = RequestMethod.POST, consumes = "application/json")
	public void addBookLoan(@RequestBody BookLoan bookloan) {
		try {
			
			BookLoan bl=bldao.readBookLoanByIds(bookloan);
			if(bl!=null){
				return;
			}
			DateTimeFormatter ft = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
			LocalDateTime date = LocalDateTime.now();
			bookloan.setDateOut(date.format(ft));
			bookloan.setDueDate(date.plusDays(7).format(ft));

			bldao.bookCheckOut(bookloan);
		} catch (ClassNotFoundException | SQLException | IllegalNameException e) {
			e.printStackTrace();

		}

	}

	@Transactional
	@RequestMapping(value = "/updateBookLoan", method = RequestMethod.POST, consumes = "application/json")
	public void updateBookLoan(@RequestBody BookLoan bookloan) {
		DateTimeFormatter ft = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
		LocalDateTime date = LocalDateTime.now();
		// bookloan.get
		bookloan.setDateIn(date.format(ft));
		try {
			bldao.bookCheckIn(bookloan);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Transactional
	@RequestMapping(value = "/overWriteDueDate/{bookId}/{branchId}/{cardNo}/{noOfdays}", method = RequestMethod.GET )
	public void overWriteDueDate(@PathVariable Integer bookId,@PathVariable Integer branchId,
			@PathVariable Integer cardNo,@PathVariable Integer noOfdays) {
		try {
			BookLoan bookloan=new BookLoan();
			Book book= new Book();
			Branch branch= new Branch();
			Borrower borrower= new Borrower();
			branch.setBranchId(branchId);
			borrower.setCardNo(cardNo);
			book.setBookId(bookId);
			bookloan.setBook(book);
			bookloan.setBranch(branch);
			bookloan.setBorrower(borrower);
		  bookloan= bldao.readBookLoanByIds(bookloan);
			String dueDate = null ;
			 dueDate = bookloan.getDueDate();
			 int stringSize = dueDate.length();
			 String newDueDate = dueDate.substring(0,stringSize-2 );
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime dateTime = LocalDateTime.parse(newDueDate, formatter);
						
			dateTime = dateTime.plusDays(noOfdays);
			System.out.println("Date time"+dateTime);

			bookloan.setDueDate(dateTime.format(formatter));

			bldao.overrideDueDate(bookloan);
			
		} catch (ClassNotFoundException | SQLException | IllegalNameException e) {
			e.printStackTrace();
		}
	}

}
