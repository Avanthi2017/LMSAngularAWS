package com.gcit.lbms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lbms.exception.IllegalNameException;

public class BookAuthorDAO extends  BaseDAO implements ResultSetExtractor<List<Integer>>{
	
	public void addBookAuthor(Integer bookId, Integer authorId ) throws ClassNotFoundException, SQLException{
		template.update("insert into tbl_book_authors values (? , ?)", new Object[]{bookId, authorId});
	}
	public void deleteBookAuthor(Integer authorId, Integer bookId) throws ClassNotFoundException, SQLException{
		template.update("delete from tbl_book_authors where authorId=? and bookId=? ", new Object[]{authorId,bookId});
	}
	public void deleteBookAuthorsByBookId(Integer bookId) throws ClassNotFoundException, SQLException{
		template.update("delete from tbl_book_authors where  bookId=? ", new Object[]{bookId});
	}
	public List<Integer> readbyAuthorId(Integer authorId)throws ClassNotFoundException, SQLException, IllegalNameException{
		return template.query("select bookId from tbl_book_authors where authorId=?",new Object[]{authorId},this);
		
	}
	public List<Integer> readBookAuthorsbybookId(Integer bookId)throws ClassNotFoundException, SQLException, IllegalNameException{
	return template.query("select authorId from tbl_book_authors where bookId=?",new Object[]{bookId},this);
		
	
	}
	


	@Override
	public List<Integer> extractData(ResultSet rs) throws SQLException {
		List<Integer>id= new ArrayList<>();
		int numberOfColumns = rs.getMetaData().getColumnCount();
		String name = null;
		// get the column names; column indexes start from 1
		for (int i = 1; i < numberOfColumns + 1; i++) {
		    String columnName = rs.getMetaData().getColumnName(i);
		    // Get the name of the column's table name
		    if ("bookId".equals(columnName)) {
		        name="bookId";
		    }
		    else if("authorId".equals(columnName)){
		    	name="authorId";
		    }
		}
		if(name!=null){
		while(rs.next()){
			id.add(rs.getInt(name));
		}
		}
		// TODO Auto-generated method stub
		return id;
	}
}
