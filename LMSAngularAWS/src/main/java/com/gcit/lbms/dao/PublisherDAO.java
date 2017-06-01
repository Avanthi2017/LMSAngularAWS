package com.gcit.lbms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lbms.entity.Publisher;
import com.gcit.lbms.exception.IllegalNameException;

public class PublisherDAO extends BaseDAO implements ResultSetExtractor<List<Publisher>>{
	
	public void addPublisher(Publisher publisher) throws ClassNotFoundException, SQLException{
		template.update("insert into tbl_publisher (publisherId, publisherName,publisherAddress,publisherPhone) values (? , ?,?,?)", new Object[]{publisher.getPublisherId(),publisher.getPublisherName(),
				publisher.getPublisherAddress(),publisher.getPublisherPhone()});
	}

	public void updatePublisher(Publisher publisher) throws ClassNotFoundException, SQLException{
		template.update("update tbl_publisher set publisherName = ?, publisherAddress = ?,publisherPhone=? where publisherId = ?", new Object[]{publisher.getPublisherName(),
				publisher.getPublisherAddress(),publisher.getPublisherPhone(),publisher.getPublisherId()});
	}
	
	public void deletePublisher(Publisher publisher) throws ClassNotFoundException, SQLException{
		template.update("delete from tbl_publisher where publisherId= ?", new Object[]{publisher.getPublisherId()});
	}
	
	public List<Publisher> readAllPublisherByName(String publisherName) throws ClassNotFoundException, SQLException, IllegalNameException{
		return template.query("select * from tbl_publisher where tbl_publisher like ?", new Object[]{"%"+publisherName+"%"},this);
	}
	public List<Publisher> readAllPublisher(Integer pageNo) throws ClassNotFoundException, SQLException, IllegalNameException{
		setPageNo(pageNo);
		return template.query("select * from tbl_publisher", this);
	}
	public List<Publisher> readAllPublisher() throws ClassNotFoundException, SQLException, IllegalNameException{
		return template.query("select * from tbl_publisher", this);
	}
//	public Integer getPublisherCount() throws ClassNotFoundException, SQLException{
//		return getCount("select count(*) as COUNT from tbl_publisher");
//	}

	
	
	public Publisher readPublisherById(Integer publisherId) throws ClassNotFoundException, SQLException, IllegalNameException{
		List<Publisher> publishers = template.query("select * from tbl_publisher where publisherId = ?", new Object[]{publisherId},this);
		if(publishers!=null && !publishers.isEmpty()){
			return publishers.get(0);
		}
		return null;
	}
	@Override
	public List<Publisher> extractData(ResultSet rs) throws SQLException {
		List<Publisher> publishers = new ArrayList<>();
		while(rs.next()){
			Publisher p = new Publisher();
			p.setPublisherId(rs.getInt("publisherId"));
			
			try {
				p.setPublisherName(rs.getString("publisherName"));
				p.setPublisherPhone(rs.getString("publisherPhone"));
				p.setPublisherAddress(rs.getString("publisherAddress"));
			} catch (IllegalNameException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			publishers.add(p);
		}
		return publishers;
	}

}
