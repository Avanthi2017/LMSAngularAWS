package com.gcit.lbms;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.gcit.lbms.dao.AuthorDAO;
import com.gcit.lbms.dao.BookAuthorDAO;
import com.gcit.lbms.dao.BookCopiesDAO;
import com.gcit.lbms.dao.BookDAO;
import com.gcit.lbms.dao.BookGenreDAO;
import com.gcit.lbms.dao.BookLoansDAO;
import com.gcit.lbms.dao.BorrowerDAO;
import com.gcit.lbms.dao.BranchDAO;
import com.gcit.lbms.dao.GenreDAO;
import com.gcit.lbms.dao.PublisherDAO;
import com.gcit.lbms.service.AuthorService;
import com.gcit.lbms.service.BookService;

@Configuration
public class LMSConfig {
	private String driver = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://rdsmysql.cbvt1xjs2m25.us-east-1.rds.amazonaws.com/library";
	private String userName = "sailu";
	private String password = "sailusha";

	@Bean
	public BasicDataSource dataSource() {
		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setUrl(url);
		basicDataSource.setUsername(userName);
		basicDataSource.setPassword(password);
		basicDataSource.setDriverClassName(driver);
		return basicDataSource;

	}
	@Bean
	public PlatformTransactionManager txManager(){
		DataSourceTransactionManager dsManager= new DataSourceTransactionManager();
		dsManager.setDataSource(dataSource());
		return dsManager;
	}

	@Bean
	@Qualifier(value = "template")
	public JdbcTemplate template() {
		return new JdbcTemplate(dataSource());
	}
	@Bean
	public AuthorDAO authordao(){
		return new AuthorDAO();
	}
	@Bean
	public BookDAO bookdao(){
		return new BookDAO();
	}
	@Bean
	public GenreDAO genredao(){
		return new GenreDAO();
	}
	@Bean
	public PublisherDAO publisherdao(){
		return new PublisherDAO();
	}
	@Bean
	public BranchDAO branchdao(){
		return new BranchDAO();
	}
	@Bean
	public BorrowerDAO borrowerdao(){
		return new BorrowerDAO();
	}
	@Bean
	public BookCopiesDAO bookcopiesdao(){
		return new BookCopiesDAO();
	}
	@Bean
	public BookAuthorDAO bookauthordao(){
		return new BookAuthorDAO();
	}
	@Bean
	public BookGenreDAO bookgenredao(){
		return new BookGenreDAO();
	}
	@Bean
	public BookLoansDAO bookloansdao(){
		return new BookLoansDAO();
	}
	@Bean
	@Qualifier(value="authorService")
	public AuthorService authorService(){
		return new AuthorService();
	}
	@Bean
	public BookService bookService(){
		return new BookService();
	}
	
}
