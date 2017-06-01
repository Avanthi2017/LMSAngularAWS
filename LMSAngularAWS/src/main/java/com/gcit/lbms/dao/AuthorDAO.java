package com.gcit.lbms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lbms.entity.Author;
import com.gcit.lbms.exception.IllegalNameException;

public class AuthorDAO extends BaseDAO implements ResultSetExtractor<List<Author>> {

	public void addAuthor(Author author) throws SQLException, ClassNotFoundException {
		template.update("insert into tbl_author (authorName) values (?)", new Object[] { author.getAuthorName() });

	}

	public void updateAuthor(Author author) throws SQLException, ClassNotFoundException {
		template.update("update tbl_author set authorName = ? where authorId = ?",
				new Object[] { author.getAuthorName(), author.getAuthorId() });
	}

	public void deleteAuthor(Author author) throws SQLException, ClassNotFoundException {
		template.update("delete from tbl_author where authorId = ?", new Object[] { author.getAuthorId() });
	}

	public List<Author> readAllAuthors(Integer pageNo)
			throws ClassNotFoundException, SQLException, IllegalNameException {
		setPageNo(pageNo);
		return template.query("select * from tbl_author", this);
	}

	public List<Author> readAllAuthors() throws ClassNotFoundException, SQLException, IllegalNameException {
		return template.query("select * from tbl_author", this);
	}
	// public Integer getAuthorsCount() throws ClassNotFoundException,
	// SQLException{
	// return template.query("select count(*) as COUNT from tbl_author",this);
	// }

	public List<Author> readAllAuthorsByName(String authorName)
			throws ClassNotFoundException, SQLException, IllegalNameException {
		return template.query("select * from tbl_author where authorName like ?",
				new Object[] { "%" + authorName + "%" }, this);
	}

	public List<Author> readBookAuthorsbybookId(Integer bookId)
			throws ClassNotFoundException, SQLException, IllegalNameException {
		return template.query(
				"select * from tbl_author where authorId IN (select authorId from tbl_book_authors where bookId=?)",
				new Object[] { bookId }, this);

	}

	public Author readAuthorById(Integer authorId) throws ClassNotFoundException, SQLException, IllegalNameException {
		List<Author> authors = template.query("select * from tbl_author where authorId = ?", new Object[] { authorId },
				this);
		if (authors != null && !authors.isEmpty()) {
			return authors.get(0);
		}
		return null;
	}

	public List<Author> extractData(ResultSet rs) throws SQLException {
		List<Author> authors = new ArrayList<>();
		while (rs.next()) {
			Author a = new Author();
			a.setAuthorId(rs.getInt("authorId"));
			try {
				a.setAuthorName(rs.getString("authorName"));
			} catch (IllegalNameException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			authors.add(a);
		}
		return authors;
	}

}
