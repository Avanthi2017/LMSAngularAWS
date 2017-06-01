package com.gcit.lbms.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gcit.lbms.dao.BookDAO;
import com.gcit.lbms.dao.GenreDAO;
import com.gcit.lbms.entity.Genre;
import com.gcit.lbms.exception.IllegalNameException;
@RestController
public class GenreService {
	@Autowired
	GenreDAO genredao;
	@Autowired
	BookDAO bdao;
	@RequestMapping(value = "/initGenre", method = RequestMethod.GET, produces="application/json")
	public Genre initGenre() {
		return new Genre();
	}
	@RequestMapping(value = "/viewGenreById/{genreId}", method = RequestMethod.GET, produces="application/json")
	public Genre readGenreById(@PathVariable Integer genreId)  {
		try {
			return genredao.readGenreById(genreId);
		} catch (ClassNotFoundException | IllegalNameException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping(value = "/searchGenres/{genreName}", method = RequestMethod.GET, produces="application/json")
	public List<Genre> getGenreByName(@PathVariable String genreName) {

		try {
			return genredao.readBooksByGenreName(genreName);
		} catch (ClassNotFoundException | SQLException | IllegalNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/viewGenres", method = RequestMethod.GET, produces="application/json")
	public List<Genre> readAllGenres()  {
		try {
			List<Genre> genres=genredao.readAllGenres();
			for(Genre a: genres){
				a.setBooks(bdao.readbygenreId(a.getGenreId()));
			}
			return genres;
		} catch (ClassNotFoundException | IllegalNameException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Transactional
	@RequestMapping(value = "/addGenre", method = RequestMethod.POST, consumes="application/json")
	public void addgenre(@RequestBody Genre genre)  {
		try {
			genredao.addGenre(genre);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Transactional
	@RequestMapping(value = "/updateGenre", method = RequestMethod.POST, consumes="application/json")
	public void updateGenre(@RequestBody Genre genre)  {
		try {
			genredao.updateGenre(genre);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Transactional
	@RequestMapping(value = "/deleteGenre", method = RequestMethod.POST, consumes="application/json")
	public void deleteGenre(@RequestBody Genre genre)  {
		try {
			genredao.deleteGenre(genre);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
