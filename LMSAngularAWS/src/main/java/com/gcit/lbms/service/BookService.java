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
import com.gcit.lbms.dao.BookCopiesDAO;
import com.gcit.lbms.dao.BookDAO;
import com.gcit.lbms.dao.BranchDAO;
import com.gcit.lbms.dao.GenreDAO;
import com.gcit.lbms.dao.PublisherDAO;
import com.gcit.lbms.entity.Author;
import com.gcit.lbms.entity.Book;
import com.gcit.lbms.entity.BookCopies;
import com.gcit.lbms.entity.Branch;
import com.gcit.lbms.entity.Genre;
import com.gcit.lbms.exception.IllegalNameException;

@RestController
public class BookService {
	@Autowired
	BookDAO bdao;
	@Autowired
	BranchDAO brdao;
	@Autowired
	PublisherDAO pdao;
	@Autowired
	AuthorDAO adao;
	@Autowired
	GenreDAO gdao;
	@Autowired
	BookCopiesDAO bcdao;

	int newBookId;

	@RequestMapping(value = "/initBook", method = RequestMethod.GET, produces = "application/json")
	public Book initBook() {
		return new Book();
	}

	@RequestMapping(value = "/viewBookById/{bookId}", method = RequestMethod.GET, produces = "application/json")
	public Book readBookById(@PathVariable Integer bookId) {
		try {
			Book book = bdao.readBookById(bookId);
			List<BookCopies> bookCopies = bcdao.readAllBookCopiesByBookId(book.getBookId());

			for (int i = 0; i < bookCopies.size(); i++) {
				if (bookCopies.get(0).getNoOfCopies() > 0) {
					BookCopies bc = new BookCopies();
					bc.setBranch(brdao.readBranchById(bookCopies.get(i).getBranch().getBranchId()));
					bc.setNoOfCopies(bookCopies.get(i).getNoOfCopies());
					bookCopies.set(i, bc);
				}
			}
			book.setAuthors(adao.readBookAuthorsbybookId(book.getBookId()));
			book.setBookCopies(bookCopies);
			book.setGenres(gdao.readBookGenrebybookId(book.getBookId()));
			book.setPublisher(pdao.readPublisherById(book.getPubId()));

			return book;
		} catch (ClassNotFoundException | IllegalNameException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/viewBooks", method = RequestMethod.GET, produces = "application/json")
	public List<Book> readBooks() {
		List<Book> books = new ArrayList<>();
		try {
			books = bdao.readAllBooks();

			for (Book b : books) {
				List<BookCopies> bookCopies = bcdao.readAllBookCopiesByBookId(b.getBookId());

				for (int i = 0; i < bookCopies.size(); i++) {
					if (bookCopies.get(0).getNoOfCopies() > 0) {
						BookCopies bc = new BookCopies();
						bc.setBranch(brdao.readBranchById(bookCopies.get(i).getBranch().getBranchId()));
						bc.setNoOfCopies(bookCopies.get(i).getNoOfCopies());
						bookCopies.set(i, bc);
					}
				}
				b.setAuthors(adao.readBookAuthorsbybookId(b.getBookId()));
				b.setBookCopies(bookCopies);
				b.setGenres(gdao.readBookGenrebybookId(b.getBookId()));
				b.setPublisher(pdao.readPublisherById(b.getPubId()));
			}
			return books;
		} catch (ClassNotFoundException | SQLException | IllegalNameException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/searchBooks/{bookName}", method = RequestMethod.GET, produces = "application/json")
	public List<Book> getBooksByName(@PathVariable String bookName) {

		try {
			return bdao.readAllBooksByName(bookName);
		} catch (ClassNotFoundException | SQLException | IllegalNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Transactional
	@RequestMapping(value = "/addBook", method = RequestMethod.POST, consumes = "application/json")
	public void addBookWithID(@RequestBody Book book) {

		try {

			newBookId = bdao.addBookWithID(book);
			System.out.println(newBookId);
			for (Author a : book.getAuthors()) {
				bdao.addBookAuthor(newBookId, a.getAuthorId());
			}
			for (Genre g : book.getGenres()) {
				bdao.addBookGenre(g.getGenreId(), newBookId);
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	@Transactional
	@RequestMapping(value = "/updateBook", method = RequestMethod.POST, consumes = "application/json")
	public void udpateBook(@RequestBody Book book) {
		try {
			if(book.getBookCopies()==null||book.getBookCopies().isEmpty())
				bdao.deleteBook(book);
			if(book.getAuthors()!=null){
				bdao.deleteBookAuthorsByBookId(book.getBookId());
				bdao.deleteBookGenreByBookId(book.getBookId());
				bdao.updateBook(book);
				this.addBookAuthor(book);
				this.addBookGenre(book);
			}
			

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	@Transactional
	@RequestMapping(value = "/updateBookCopiesByBookId", method = RequestMethod.POST, consumes = "application/json")
	public void updateBookCopiesByBookId(@RequestBody List<Branch> branchs) {
		try {
			if (branchs == null || branchs.size() == 0) {
				return;

			}
			for (Branch b : branchs) {
				if (b.getBookCopies().get(0).getNoOfCopies() > 0) {
					BookCopies bookCopies = bcdao.readBookCopiesByBranchIdAndBookId(b.getBranchId(),
							b.getBookCopies().get(0).getBook().getBookId());
					if (bookCopies == null || bookCopies.getNoOfCopies() == 0) {
						bcdao.addBookCopies(b.getBookCopies().get(0));
					} else {
						bcdao.updateBookCopies(b.getBookCopies().get(0));
					}
				}

			}
		} catch (ClassNotFoundException | SQLException | IllegalNameException e) {
			e.printStackTrace();
		}
	}
	@Transactional
	@RequestMapping(value = "/addBookCopiesByBook", method = RequestMethod.POST, consumes = "application/json")
	public void addBookCopiesByBook(@RequestBody List<Branch> branchs) {
		System.out.println("addBookCopiesByBook"+newBookId);
		if (newBookId <= 0) {
			return;
		}
		try {
			Book book = new Book();
			book.setBookId(newBookId);
			for (Branch b : branchs) {
				System.out.println("copies"+b.getBookCopies().get(0).getNoOfCopies());
				if (b.getBookCopies().get(0).getNoOfCopies() > 0) {
					BookCopies bookCopies = new BookCopies();
					bookCopies.setBranch(b);
					bookCopies.setBook(book);
					bookCopies.setNoOfCopies(b.getBookCopies().get(0).getNoOfCopies());
					bcdao.addBookCopies(bookCopies);
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	@Transactional
	@RequestMapping(value = "/deleteBook", method = RequestMethod.POST, consumes = "application/json")
	public void deleteBook(@RequestBody Book book) {
		
		try {
			if ( book.getBookCopies() == null||book.getBookCopies().size()<=1) {
				bdao.deleteBook(book);
				return;
			}
			BookCopies bookcopies = new BookCopies();
			bookcopies.setBook(book);
			bookcopies.setBranch(book.getBookCopies().get(0).getBranch());
			bcdao.deleteBookCopies(bookcopies);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	@Transactional
	@RequestMapping(value = "/addBookAuthor", method = RequestMethod.POST, consumes = "application/json")
	public void addBookAuthor(@RequestBody Book book) {
		try {
			for (Author a : book.getAuthors()) {
				bdao.addBookAuthor(book.getBookId(), a.getAuthorId());
			}

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	@RequestMapping(value = "/viewBookAuthorByBookId", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
	public List<Author> readBookAuthorByBookId(int bookId) {
		try {
			return adao.readBookAuthorsbybookId(bookId);
		} catch (ClassNotFoundException | IllegalNameException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	@Transactional
	@RequestMapping(value = "/addBookGenre", method = RequestMethod.POST, consumes = "application/json")
	public void addBookGenre(@RequestBody Book book) {
		try {
			for (Genre g : book.getGenres()) {
				bdao.addBookGenre(g.getGenreId(), book.getBookId());
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/viewBookGenreByBookId", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public List<Genre> readBookGenreByBookId(int bookId) {
		try {
			return gdao.readBookGenrebybookId(bookId);
		} catch (ClassNotFoundException | IllegalNameException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/addBookCopies", method = RequestMethod.POST, produces = "application/json")
	public void addBookCopies(@RequestBody BookCopies bookCopies) {
		try {
			if (bookCopies.getNoOfCopies() > 0)
				bcdao.addBookCopies(bookCopies);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	@RequestMapping(value = "/updateBookCopies", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public void updateBookCopies(BookCopies bookcopies) {
		try {
			bcdao.updateBookCopies(bookcopies);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/viewBookCopies", method = RequestMethod.GET, produces = "application/json")
	public List<BookCopies> readAllBookCopies() {
		try {
			List<BookCopies> bookCopies = bcdao.readAllBookCopies();
			for (int i = 0; i < bookCopies.size(); i++) {
				Book book = bdao.readBookById(bookCopies.get(i).getBook().getBookId());
				Branch branch = brdao.readBranchById(bookCopies.get(i).getBranch().getBranchId());
				BookCopies bc = new BookCopies();
				bc.setBook(book);
				bc.setBranch(branch);
				bookCopies.set(i, bc);
			}

			return bookCopies;
		} catch (ClassNotFoundException | SQLException | IllegalNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/deleteBookCopies", method = RequestMethod.GET, consumes = "application/json")
	public void deleteBookCopies(@RequestBody Book book) {
		try {
			for (BookCopies b : book.getBookCopies()) {
				bcdao.deleteBookCopies(b);
			}

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<BookCopies> readBookCopiesbyBookId(int bookId) {
		try {
			return bcdao.readAllBookCopiesByBookId(bookId);
		} catch (ClassNotFoundException | SQLException | IllegalNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	@RequestMapping(value = "/viewBookCopiesByBranchId/{branchId}", method = RequestMethod.GET, produces = "application/json")
	public List<BookCopies> readBookCopiesByBranchId(@PathVariable Integer branchId) {
		try {
			List<BookCopies> bookCopies = bcdao.readAllBookCopiesByBranchId(branchId);
			for (int i = 0; i < bookCopies.size(); i++) {
				Book book = bdao.readBookById(bookCopies.get(i).getBook().getBookId());
				Branch branch = brdao.readBranchById(bookCopies.get(i).getBranch().getBranchId());
				BookCopies bc = new BookCopies();
				bc.setBook(book);
				bc.setBranch(branch);
				bookCopies.set(i, bc);
			}
			return bookCopies;

		} catch (ClassNotFoundException | SQLException | IllegalNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@RequestMapping(value = "/viewBookCopiesByBookIdAndBranchId/{bookId}/{branchId}", method = RequestMethod.GET, produces = "application/json")
	public BookCopies readBookCopiesByBookIdAndBranchId(@PathVariable int bookId, @PathVariable int branchId) {
		try {
			return bcdao.readBookCopiesByBranchIdAndBookId(bookId, branchId);
		} catch (ClassNotFoundException | SQLException | IllegalNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}
