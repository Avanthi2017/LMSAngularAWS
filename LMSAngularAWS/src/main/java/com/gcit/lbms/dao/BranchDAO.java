package com.gcit.lbms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lbms.entity.Branch;
import com.gcit.lbms.exception.IllegalNameException;

public class BranchDAO extends BaseDAO implements ResultSetExtractor<List<Branch>>{

		// TODO Auto-generated constructor stub
		public void addBranch(Branch branch) throws SQLException, ClassNotFoundException {
			template.update("insert into tbl_library_branch (branchName,branchAddress) values (?,?)", new Object[] {branch.getBranchName(),branch.getBranchAddress()});
		}

		public void updateBranch(Branch branch) throws SQLException, ClassNotFoundException {
			template.update("update tbl_library_branch set branchName=?,branchAddress=? where branchId = ?", new Object[] {branch.getBranchName(),branch.getBranchAddress(),branch.getBranchId()});
		}

		public void deleteBranch(Branch branch) throws SQLException, ClassNotFoundException {
			template.update("delete from tbl_library_branch where branchId = ?", new Object[] {branch.getBranchId()});
		}
		
		
		public List<Branch> readAllBranchesByName(String branchName) throws ClassNotFoundException, SQLException, IllegalNameException{
			return template.query("select * from tbl_library_branch where branchName like ?", new Object[]{"%"+branchName+"%"},this);
		}
		public List<Branch> readAllBranchs() throws ClassNotFoundException, SQLException, IllegalNameException{
			return template.query("select * from tbl_library_branch", this);
		}
		public List<Branch> readAllBranchs(Integer pageNo) throws ClassNotFoundException, SQLException, IllegalNameException{
			setPageNo(pageNo);
			return template.query("select * from tbl_library_branch", this);
		}
//		public Integer getBranchCount() throws ClassNotFoundException, SQLException{
//			return getCount("select count(*) as COUNT from tbl_library_branch");
//		}

		public Branch readBranchById(Integer branchId) throws ClassNotFoundException, SQLException, IllegalNameException{
			List<Branch> branches = template.query("select * from tbl_library_branch where branchId = ?", new Object[]{branchId},this);
			if(branches!=null && !branches.isEmpty()){
				return branches.get(0);
			}
			return null;
		}
		
		
		public List<Branch> extractData(ResultSet rs) throws SQLException{
			List<Branch> branches = new ArrayList<>();
			while(rs.next()){
				Branch b = new Branch();
				b.setBranchId(rs.getInt("branchId"));
				try {
					b.setBranchName(rs.getString("branchName"));
				} catch (IllegalNameException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				b.setBranchAddress(rs.getString("branchAddress"));
				branches.add(b);
			}
			return branches;
		}

	}

