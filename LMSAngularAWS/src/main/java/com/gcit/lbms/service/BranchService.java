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

import com.gcit.lbms.dao.BookCopiesDAO;
import com.gcit.lbms.dao.BookDAO;
import com.gcit.lbms.dao.BookLoansDAO;
import com.gcit.lbms.dao.BorrowerDAO;
import com.gcit.lbms.dao.BranchDAO;
import com.gcit.lbms.entity.Book;
import com.gcit.lbms.entity.BookCopies;
import com.gcit.lbms.entity.BookLoan;
import com.gcit.lbms.entity.Borrower;
import com.gcit.lbms.entity.Branch;
import com.gcit.lbms.exception.IllegalNameException;

@RestController
public class BranchService {

	@Autowired
	BranchDAO bdao;
	@Autowired
	BorrowerDAO brdao;
	@Autowired
	BookDAO bookdao;
	@Autowired
	BookCopiesDAO bcdao;
	@Autowired
	BorrowerDAO bodao;
	@Autowired
	BookLoansDAO bldao;

	@RequestMapping(value = "/initBranch", method = RequestMethod.GET, produces = "application/json")
	public Branch initBranch() {
		return new Branch();
	}

	@RequestMapping(value = "/searchBranchs/{branchName}", method = RequestMethod.GET, produces = "application/json")
	public List<Branch> getGenreByName(@PathVariable String branchName) {

		try {
			return bdao.readAllBranchesByName(branchName);
		} catch (ClassNotFoundException | SQLException | IllegalNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/viewBracnhs", method = RequestMethod.GET, produces = "application/json")
	public List<Branch> readAllBranches() {
		List<BookCopies>bookCopies= new ArrayList<>();
		BookCopies bc= new BookCopies();
		bookCopies.add(bc);
		try {
			List<Branch> branchs= bdao.readAllBranchs();
			for(int i=0;i<branchs.size();i++){
				List<Borrower>borrowers=brdao.readBorrowerByBranchId(branchs.get(i).getBranchId());
				branchs.get(i).setBorrowers(borrowers);
				branchs.get(i).setBookCopies(bookCopies);
			}
			return branchs;

		} catch (ClassNotFoundException | SQLException | IllegalNameException e) {
			e.printStackTrace();
		}
		return null;

	}

	@RequestMapping(value = "/viewBracnhsbyBookId/{bookId}", method = RequestMethod.GET, produces = "application/json")
	public List<Branch> readAllBranches(@PathVariable Integer bookId) {
		try {
			List<Branch> branches = bdao.readAllBranchs();
			for (int i=0;i<branches.size();i++) {
				List<BookCopies> copies = new ArrayList<>();
				BookCopies bc = bcdao.readBookCopiesByBranchIdAndBookId(branches.get(i).getBranchId(), bookId);
				if (bc == null) {
					bc = new BookCopies();
					bc.setNoOfCopies(0);
					Book book= new Book();
					book.setBookId(bookId);
					bc.setBook(book);
					Branch branch= new Branch();
					branch.setBranchId(branches.get(i).getBranchId());
					bc.setBranch(branch);
				} // TODO:
				copies.add(bc);
				branches.get(i).setBookCopies(copies);
				
			}

			return branches;
		} catch (ClassNotFoundException | SQLException | IllegalNameException e) {
			e.printStackTrace();
		}
		return null;

	}

	@RequestMapping(value = "/viewBranchById/{branchId}", method = RequestMethod.GET, produces = "application/json")
	public Branch readBranchById(@PathVariable Integer branchId) {
		try {
			Branch branch = bdao.readBranchById(branchId);
			if (branch == null) {
				return null;
			}
			List<BookCopies> bookCopies = bcdao.readAllBookCopiesByBranchId(branchId);
			for (int i = 0; i < bookCopies.size(); i++) {
				BookCopies bookCopies2 = new BookCopies();
				bookCopies2.setNoOfCopies(bookCopies.get(i).getNoOfCopies());
				bookCopies2.setBook(bookdao.readBookById(bookCopies.get(i).getBook().getBookId()));
				bookCopies2.setBranch(bdao.readBranchById(branchId));

				bookCopies.set(i, bookCopies2);
			}
			branch.setBookCopies(bookCopies);
			return branch;

		} catch (ClassNotFoundException | SQLException | IllegalNameException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/addBranch", method = RequestMethod.POST, produces = "application/json")
	public void addBranch(@RequestBody Branch branch) {

		try {
			bdao.addBranch(branch);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@Transactional
	@RequestMapping(value = "/updateBranch", method = RequestMethod.POST, consumes = "application/json")
	public void updateBranch(@RequestBody Branch branch) {

		try {
			bdao.updateBranch(branch);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Transactional
	@RequestMapping(value = "/updateBranchLibrarian", method = RequestMethod.POST, consumes = "application/json")
	public void updateBranchLibrarian(@RequestBody Branch branch) {

		try {
			bdao.updateBranch(branch);
			List<BookCopies> bookCopies = branch.getBookCopies();
			for (BookCopies bc : bookCopies) {
				bcdao.updateBookCopies(bc);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/deleteBranch", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public void deleteBranch(@RequestBody Branch branch) {

		try {
			bdao.deleteBranch(branch);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
