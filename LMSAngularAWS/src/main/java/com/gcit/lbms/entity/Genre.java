package com.gcit.lbms.entity;

import java.io.Serializable;
import java.util.List;

import com.gcit.lbms.exception.IllegalNameException;

public class Genre implements Serializable{
	private static final long serialVersionUID = 7982686206406225512L;
	
	private Integer genreId;
	private String genreName;
	private List<Book> books;
	/**
	 * @return the genreId
	 */
	public Integer getGenreId() {
		return genreId;
	}
	/**
	 * @param genreId the genreId to set
	 */
	public void setGenreId(Integer genreId) {
		this.genreId = genreId;
	}
	/**
	 * @return the genreName
	 */
	public String getGenreName() {
		return genreName;
	}
	/**
	 * @param genreName the genreName to set
	 */
	public void setGenre_name(String genre_name) throws IllegalNameException {
		if (genre_name!=null&&genre_name.length() > 45) {
			throw new IllegalNameException("Name Should be less than 45 characters");

		}

		this.genreName = genre_name;
	}
	/**
	 * @return the books
	 */
	public List<Book> getBooks() {
		return books;
	}
	/**
	 * @param books the books to set
	 */
	public void setBooks(List<Book> books) {
		this.books = books;
	}
}
