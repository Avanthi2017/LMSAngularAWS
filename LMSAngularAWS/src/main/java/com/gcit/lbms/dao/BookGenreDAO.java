package com.gcit.lbms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lbms.exception.IllegalNameException;

//void add(book,genre)
public class BookGenreDAO extends BaseDAO implements ResultSetExtractor<List<Integer>>{

	public void addBookGenre(Integer genre_id, Integer bookId ) throws ClassNotFoundException, SQLException{
		template.update("insert into tbl_book_genres values (? , ?)", new Object[]{genre_id, bookId});
	}
	public void deleteBookGenre(Integer genre_id, Integer bookId ) throws ClassNotFoundException, SQLException{
		template.update("delete from tbl_book_genres where genre_id= ? and bookId=?", new Object[]{genre_id, bookId});
	}
	public void deleteBookGenreByBookId( Integer bookId ) throws ClassNotFoundException, SQLException{
		template.update("delete from tbl_book_genres where bookId=?", new Object[]{bookId});
	}
	public List<Integer> readbygenreId(Integer genre_id)throws ClassNotFoundException, SQLException, IllegalNameException{
		return template.query("select * from tbl_book where bookId IN (select bookId from tbl_book_genres where genre_id=?",new Object[]{genre_id},this);
		
	}
	public List<Integer> readBookGenrebybookId(Integer bookId)throws ClassNotFoundException, SQLException, IllegalNameException{
	return template.query("select genre_id from tbl_book_genres where bookId=?",new Object[]{bookId},this);
		
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
		    else if("genre_id".equals(columnName)){
		    	name="genre_id";
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
