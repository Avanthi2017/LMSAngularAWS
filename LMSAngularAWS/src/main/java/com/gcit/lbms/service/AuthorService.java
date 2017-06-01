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

import com.gcit.lbms.dao.AuthorDAO;
import com.gcit.lbms.dao.BookDAO;
import com.gcit.lbms.entity.Author;
import com.gcit.lbms.exception.IllegalNameException;

@RestController
public class AuthorService {
	@Autowired
	AuthorDAO adao;
	@Autowired
	BookDAO bdao;
	@RequestMapping(value = "/initAuthor", method = RequestMethod.GET, produces="application/json")
	public Author initAuthor() {
		return new Author();
	}
	@RequestMapping(value = "/viewAuthorById/{authorId}", method = RequestMethod.GET,produces="application/json")
	public Author readAuthorById(@PathVariable Integer authorId) {
		try {
			return adao.readAuthorById(authorId);
		} catch (ClassNotFoundException | IllegalNameException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping(value = "/searchAuthors/{authorName}", method = RequestMethod.GET, produces="application/json")
	public List<Author> getAuthorsByName(@PathVariable String authorName) {

		try {
			return adao.readAllAuthorsByName(authorName);
		} catch (ClassNotFoundException | SQLException | IllegalNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@Transactional
	@RequestMapping(value = "/addAuthor", method = RequestMethod.POST, consumes="application/json")
	public void addAuthor(@RequestBody Author author) {
		try {
			adao.addAuthor(author);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          
	}
	@RequestMapping(value = "/viewAuthors", method = RequestMethod.GET, produces="application/json")
	public List<Author> viewAuthors() {
		
		List<Author> authors = new ArrayList<>();
		System.out.println("viewAuthors");
		try {
				authors = adao.readAllAuthors();
			
			for(Author a: authors){
				a.setBooks(bdao.readAllBooksByAuthorID(a.getAuthorId()));
			}
			return authors;
		} catch (ClassNotFoundException | SQLException | IllegalNameException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Transactional
	@RequestMapping(value = "/updateAuthor", method = RequestMethod.POST, consumes="application/json", produces="application/json")
	 public List<Author> updateAuthor(@RequestBody Author author) {
		try {
			adao.updateAuthor(author);
			return adao.readAllAuthors();
		} catch (ClassNotFoundException | SQLException | IllegalNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	 }
	@Transactional
	@RequestMapping(value = "/deleteAuthor", method = RequestMethod.POST, consumes="application/json", produces="application/json")
	public void  deleteAuthor(@RequestBody Author author) {
		try {
			adao.deleteAuthor(author);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
