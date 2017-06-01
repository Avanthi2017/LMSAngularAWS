package com.gcit.lbms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lbms.entity.Book;
import com.gcit.lbms.exception.IllegalNameException;

public class BookDAO extends BaseDAO implements ResultSetExtractor<List<Book>>{
	

	public Integer addBookWithID(Book book) throws ClassNotFoundException, SQLException{
		 template.update("insert into tbl_book (title, pubId) values (? , ?)", new Object[]{book.getTitle(), book.getPubId()});
		 return template.queryForInt("select last_insert_id()");
	}
	
	public void updateBook(Book book) throws ClassNotFoundException, SQLException{
		template.update("update tbl_book set title = ?, pubId = ? where bookId = ?", new Object[]{book.getTitle(),book.getPubId(), book.getBookId()});
	}
	public Book readBookById(Integer bookId) throws ClassNotFoundException, SQLException, IllegalNameException{
		List<Book> books = template.query("select * from tbl_book where bookId = ?", new Object[]{bookId},this);
		if(books!=null && !books.isEmpty()){
			return books.get(0);
		}
		return null;
	}
	public List<Book> readAllBooks() throws ClassNotFoundException, SQLException, IllegalNameException{
		return template.query("select * from tbl_book", this);
	}
//	public Integer getBooksCount() throws ClassNotFoundException, SQLException{
//		return getCount("select count(*) as COUNT from tbl_book");
//	}
	public List<Book> readAllBooksByName(String title) throws ClassNotFoundException, SQLException, IllegalNameException{
		return template.query("select * from tbl_book where title like ?", new Object[]{title+"%"},this);
	}
	public List<Book> readAllBooksByPubID(Integer pubId){
		return template.query("select * from tbl_book  where pubId= ?", new Object[]{pubId}, this);
	}
	public void deleteBook(Book book) throws ClassNotFoundException, SQLException{
		template.update("delete from tbl_book where bookId= ? ", new Object[]{book.getBookId()});
	}
	public List<Book> readAllBooksByAuthorID(Integer authorId){
		return template.query("select * from tbl_book where bookId IN (select bookId from tbl_book_authors where authorId = ?)", new Object[]{authorId}, this);
	}
	public void addBookAuthor(Integer bookId, Integer authorId) throws ClassNotFoundException, SQLException{
		template.update("insert into tbl_book_authors values (?, ?)", new Object[] {bookId, authorId});
	}
	public void deleteBookAuthorsByBookId(Integer bookId) throws ClassNotFoundException, SQLException{
		template.update("delete from tbl_book_authors where  bookId=? ", new Object[]{bookId});
	}
	public void deleteBookAuthor(Integer authorId, Integer bookId) throws ClassNotFoundException, SQLException{
		template.update("delete from tbl_book_authors where authorId=? and bookId=? ", new Object[]{authorId,bookId});
	}
	
	public void addBookGenre(Integer genre_id, Integer bookId ) throws ClassNotFoundException, SQLException{
		template.update("insert into tbl_book_genres values (? , ?)", new Object[]{genre_id, bookId});
	}
	public void deleteBookGenre(Integer genre_id, Integer bookId ) throws ClassNotFoundException, SQLException{
		template.update("delete from tbl_book_genres where genre_id= ? and bookId=?", new Object[]{genre_id, bookId});
	}
	public void deleteBookGenreByBookId( Integer bookId ) throws ClassNotFoundException, SQLException{
		template.update("delete from tbl_book_genres where bookId=?", new Object[]{bookId});
	}
	public List<Book> readbygenreId(Integer genre_id)throws ClassNotFoundException, SQLException, IllegalNameException{
		return template.query("select * from tbl_book where bookId IN (select bookId from tbl_book_genres where genre_id=?)",new Object[]{genre_id},this);
		
	}
	
	
	@Override
	public List<Book> extractData(ResultSet rs) throws SQLException {
		List<Book> books = new ArrayList<>();
		while(rs.next()){
			Book b = new Book();
			if(rs.getString("title")!=null){
			try {
				b.setTitle(rs.getString("title"));
				b.setPubId(rs.getInt("pubId"));
			} catch (IllegalNameException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
			b.setBookId(rs.getInt("bookId"));
			books.add(b);
		}
		return books;
	}

}
