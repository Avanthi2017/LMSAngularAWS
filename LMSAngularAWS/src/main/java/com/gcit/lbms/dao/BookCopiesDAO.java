package com.gcit.lbms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lbms.entity.Book;
import com.gcit.lbms.entity.BookCopies;
import com.gcit.lbms.entity.Branch;
import com.gcit.lbms.exception.IllegalNameException;

public class BookCopiesDAO extends BaseDAO implements ResultSetExtractor<List<BookCopies>> {

	public void addBookCopies(BookCopies bookcopies) throws SQLException, ClassNotFoundException {
		template.update("insert into tbl_book_copies (bookId,branchId,noOfCopies)values(?,?,? )", new Object[] {
				bookcopies.getBook().getBookId(), bookcopies.getBranch().getBranchId(), bookcopies.getNoOfCopies() });
	}

	public void updateBookCopies(BookCopies bookcopies) throws SQLException, ClassNotFoundException {
		template.update("update tbl_book_copies set noOfCopies=? where bookId=? and branchId=?", new Object[] {
				bookcopies.getNoOfCopies(), bookcopies.getBook().getBookId(), bookcopies.getBranch().getBranchId() });
	}

	public void deleteBookCopies(BookCopies bookcopies) throws SQLException, ClassNotFoundException {
		template.update("delete from tbl_book_copies where bookId=? and branchId=?",
				new Object[] { bookcopies.getBook().getBookId(), bookcopies.getBranch().getBranchId() });
	}
	public void deleteBookCopiesByBookId(int bookId) throws SQLException, ClassNotFoundException {
		template.update("delete from tbl_book_copies where bookId=?",
				new Object[] { bookId});
	}
	

	public List<BookCopies> readAllBookCopies() throws SQLException, ClassNotFoundException, IllegalNameException {
		return template.query("select * from tbl_book_copies", this);
	}

	public BookCopies readBookCopiesByBranchIdAndBookId(int branchId, int bookId)
			throws SQLException, ClassNotFoundException, IllegalNameException {
		List<BookCopies> bookCopies2 = template.query("select  * from tbl_book_copies where bookId=? and branchId=?",
				new Object[] { bookId, branchId }, this);
		if (bookCopies2 != null && !bookCopies2.isEmpty()) {
			return bookCopies2.get(0);
		}
		return null;
	}

	public List<BookCopies> readAllBookCopiesByBranchId(int branchId)
			throws SQLException, ClassNotFoundException, IllegalNameException {
		return template.query("select * from tbl_book_copies where branchId=?", new Object[] { branchId }, this);
	}

	public List<BookCopies> readAllBookCopiesByBookId(int bookId)
			throws SQLException, ClassNotFoundException, IllegalNameException {
		return template.query("select  * from tbl_book_copies where bookId=?", new Object[] { bookId }, this);
	}

	@Override
	public List<BookCopies> extractData(ResultSet rs) throws SQLException {
		List<BookCopies> bookcopies = new ArrayList<>();
		while (rs.next()) {
			BookCopies bc = new BookCopies();
			Book book = new Book();
			Branch branch = new Branch();
			book.setBookId(rs.getInt("bookId"));
			branch.setBranchId(rs.getInt("branchId"));
			bc.setBook(book);
			bc.setBranch(branch);
			bc.setNoOfCopies(rs.getInt("noOfCopies"));
			bookcopies.add(bc);
		}
		return bookcopies;
	}

}
