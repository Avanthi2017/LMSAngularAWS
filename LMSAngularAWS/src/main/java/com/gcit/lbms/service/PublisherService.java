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

import com.gcit.lbms.dao.BookDAO;
import com.gcit.lbms.dao.PublisherDAO;
import com.gcit.lbms.entity.Publisher;
import com.gcit.lbms.exception.IllegalNameException;

@RestController
public class PublisherService {
	@Autowired
	PublisherDAO pdao;
	@Autowired
	BookDAO bdao;
	
	@RequestMapping(value = "/initPublisher", method = RequestMethod.GET, produces="application/json")
	public Publisher initPublisher() {
		return new Publisher();
	}
	@RequestMapping(value = "/searchPublishers/{publisherName}", method = RequestMethod.GET, produces="application/json")
	public List<Publisher> getPublisherByName(@PathVariable String publisherName) {

		try {
			return pdao.readAllPublisherByName(publisherName);
		} catch (ClassNotFoundException | SQLException | IllegalNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/viewPublishers", method = RequestMethod.GET, produces="application/json")
	public List<Publisher> viewPublishers() {
		List<Publisher> publishers = new ArrayList<>();
		try {
				publishers =pdao.readAllPublisher();
			for(Publisher p: publishers){
				p.setBooks(bdao.readAllBooksByPubID(p.getPublisherId()));
			}
			return publishers;
		} catch (ClassNotFoundException | SQLException | IllegalNameException e) {
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping(value = "/viewPublisherById/{publisherId}", method = RequestMethod.GET,produces="application/json" )
	public Publisher readPublisherById(@PathVariable Integer publisherId)  {
		try {
			Publisher publisher=pdao.readPublisherById(publisherId);
			publisher.setBooks(bdao.readAllBooksByPubID(publisherId));
			return publisher;
		} catch (ClassNotFoundException | IllegalNameException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping(value = "/addPublisher", method = RequestMethod.POST, produces="application/json")
	@Transactional
	public void  addPublisher(@RequestBody Publisher publisher)  {
		try {
			pdao.addPublisher(publisher);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	
		}

	@RequestMapping(value = "/updatePublisher", method = RequestMethod.POST, consumes="application/json", produces="application/json")
	@Transactional
	public void updatePublisher(@RequestBody Publisher publisher)  {
		try {
			pdao.updatePublisher(publisher);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	@RequestMapping(value = "/deletePublisher", method = RequestMethod.POST, consumes="application/json", produces="application/json")
	@Transactional
	public void deletePublisher(@RequestBody Publisher publisher)  {
		try {
			pdao.deletePublisher(publisher);

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
}
