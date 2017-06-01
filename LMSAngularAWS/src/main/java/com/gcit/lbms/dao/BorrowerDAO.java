package com.gcit.lbms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lbms.entity.Borrower;
import com.gcit.lbms.exception.IllegalNameException;

public class BorrowerDAO extends BaseDAO implements ResultSetExtractor<List<Borrower>>{


	public void addBorrower(Borrower borrower) throws SQLException, ClassNotFoundException {
		template.update("insert into tbl_borrower (name,address,phone) values(?,?,?)",
				new Object[] { borrower.getName(), borrower.getAddress(), borrower.getPhone() });
	}

	public void updateBorrower(Borrower borrower) throws SQLException, ClassNotFoundException {
		template.update("update tbl_borrower set name=?,address=?,phone=? where cardNo=?",
				new Object[] { borrower.getName(), borrower.getAddress(), borrower.getPhone(), borrower.getCardNo() });
	}
	public void deleteBorrower(Borrower borrower) throws SQLException, ClassNotFoundException {
		template.update("delete from tbl_borrower where cardNo=?",
				new Object[] {borrower.getCardNo()});
	}
//	public Integer getBorrowerCount() throws ClassNotFoundException, SQLException{
//		return getCount("select count(*) as COUNT from tbl_borrower");
//	}
	public Borrower readBorrowerByCardNo(Integer cardNo) throws ClassNotFoundException, SQLException, IllegalNameException{
		List<Borrower> borrowers = template.query("select * from tbl_borrower where cardNo = ?", new Object[]{cardNo},this);
		if(borrowers!=null && !borrowers.isEmpty()){
			return borrowers.get(0);
		}
		return null;
	}
	public List<Borrower> readBorrowerByBranchId(Integer branchId) throws ClassNotFoundException, SQLException, IllegalNameException{
		return template.query("select * from tbl_borrower where cardNo IN (select cardNo from tbl_book_loans where branchId = ? and dateIn is null)", new Object[]{branchId},this);
		
	}
	public List<Borrower> readAllBorrowersbyName(String borrower_name) throws ClassNotFoundException, SQLException, IllegalNameException{
		return template.query("select * from tbl_borrower where name like ?", new Object[]{"%"+borrower_name+"%"},this);
	}
	public List<Borrower> readAllBorrowers() throws ClassNotFoundException, SQLException, IllegalNameException{
		return template.query("select * from tbl_borrower", this);
	}
	@Override
	public List<Borrower> extractData(ResultSet rs) throws SQLException {
		List<Borrower>borrowers= new ArrayList<>();
		
		while(rs.next())
		{
			Borrower borrower =new Borrower();
		borrower.setCardNo(rs.getInt("cardNo"));
		try {
			borrower.setName(rs.getString("name"));
		} catch (IllegalNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		borrower.setPhone(rs.getString("phone"));
		borrower.setAddress(rs.getString("address"));
		borrowers.add(borrower);
		}
		return borrowers;

	}


}
