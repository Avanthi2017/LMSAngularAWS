package com.gcit.lbms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lbms.entity.Genre;
import com.gcit.lbms.exception.IllegalNameException;

public  class GenreDAO extends BaseDAO implements ResultSetExtractor<List<Genre>> {

	public void addGenre(Genre genre) throws ClassNotFoundException, SQLException{
		template.update("insert into tbl_genre  (genre_name) values (?)", new Object[]{genre.getGenreName()});
	}
	
	public void updateGenre(Genre genre) throws ClassNotFoundException, SQLException{
		template.update("update tbl_genre set genre_name = ? where genre_id=?", new Object[]{genre.getGenreName(),genre.getGenreId()});
	}
	
	public void deleteGenre(Genre genre) throws ClassNotFoundException, SQLException{
		template.update("delete from tbl_genre where genre_id= ? ", new Object[]{genre.getGenreId()});
	}
	
	public Genre readGenreById(Integer genreId) throws ClassNotFoundException, SQLException, IllegalNameException{
		List<Genre> genre = template.query("select * from tbl_genre where genre_id = ?", new Object[]{genreId},this);
		if(genre!=null && !genre.isEmpty()){
			return genre.get(0);
		}
		return null;
	}
	
	public List<Genre> readBooksByGenreName(String genre_name) throws ClassNotFoundException, SQLException, IllegalNameException{
		return template.query("select * from tbl_genre where genre_name like ?", new Object[]{"%"+genre_name+"%"},this);
	}
	public List<Genre> readAllGenres(Integer pageNo) throws ClassNotFoundException, SQLException, IllegalNameException{
		setPageNo(pageNo);
		return template.query("select * from tbl_genre", this);
	}
	public List<Genre> readAllGenres() throws ClassNotFoundException, SQLException, IllegalNameException{
		return template.query("select * from tbl_genre", this);
	}
	public List<Genre> readBookGenrebybookId(Integer bookId)throws ClassNotFoundException, SQLException, IllegalNameException{
		return template.query("select * from tbl_genre where genre_id IN (select genre_id from tbl_book_genres where bookId=?)",new Object[]{bookId},this);
			
	}
//	public Integer getGenreCount() throws ClassNotFoundException, SQLException{
//		return getCount("select count(*) as COUNT from tbl_genre");
//	}

	

	@Override
	public List<Genre> extractData(ResultSet rs) throws SQLException {
		List<Genre> genres = new ArrayList<>();
		while(rs.next()){
			Genre g = new Genre();
			try {
				g.setGenre_name(rs.getString("genre_name"));
			} catch (IllegalNameException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			g.setGenreId(rs.getInt("genre_id"));
			genres.add(g);
		}
		return genres;
	}

}
