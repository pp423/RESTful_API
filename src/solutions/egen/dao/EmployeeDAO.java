package solutions.egen.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import solution.egen.exceptions.AppException;
import solutions.egen.model.Auth;
import solutions.egen.model.Employee;
import solutions.egen.utils.DBUtil;

public class EmployeeDAO {

	public List<Employee> getAll() throws AppException {
		List<Employee> empList = new ArrayList<Employee>();

		Connection con = DBUtil.connectToDB();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement("select * from employee");
			rs = ps.executeQuery();
			while (rs.next()) {
				Employee emp = new Employee();
				emp.setId(rs.getInt("ID"));
				emp.setFirstName(rs.getString("FIRST_NAME"));
				emp.setLastName(rs.getString("LAST_NAME"));
				emp.setAddress1(rs.getString("ADDRESS1"));
				emp.setAddress2(rs.getString("ADDRESS2"));
				emp.setCity(rs.getString("CITY"));
				emp.setState(rs.getString("STATE"));
				emp.setZip(rs.getInt("ZIP"));
				emp.setEmail(rs.getString("EMAIL"));
				emp.setPhone(rs.getString("PHONE"));

				empList.add(emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AppException("Error in fetching records from database", e.getCause());
		} finally {
			DBUtil.closeResources(ps, rs, con);
		}
		return empList;
	}
	
	public Employee getPerson(int empId) throws AppException {
		Employee emp = new Employee();

		Connection con = DBUtil.connectToDB();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement("select * from employee where id=?");
			ps.setInt(1, empId);
			
			rs = ps.executeQuery();
			
			if (rs.next()) {
				emp.setId(rs.getInt("ID"));
				emp.setFirstName(rs.getString("FIRST_NAME"));
				emp.setLastName(rs.getString("LAST_NAME"));
				emp.setAddress1(rs.getString("ADDRESS1"));
				emp.setAddress2(rs.getString("ADDRESS2"));
				emp.setCity(rs.getString("CITY"));
				emp.setState(rs.getString("STATE"));
				emp.setZip(rs.getInt("ZIP"));
				emp.setEmail(rs.getString("EMAIL"));
				emp.setPhone(rs.getString("PHONE"));
			} else {
				throw new AppException("Employee with ID: " + empId + " does not exists.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AppException("Error in fetching records from database", e.getCause());
		} finally {
			DBUtil.closeResources(ps, rs, con);
		}
		
		return emp;
	}
	
	public Employee addPerson(Employee emp) throws AppException {

		Connection con = DBUtil.connectToDB();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement("INSERT INTO emp_db.employee (FIRST_NAME, LAST_NAME, EMAIL, ADDRESS1, ADDRESS2, CITY, ZIP, PHONE, STATE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, emp.getFirstName());
			ps.setString(2, emp.getLastName());
			ps.setString(3, emp.getEmail());
			ps.setString(4, emp.getAddress1());
			ps.setString(5, emp.getAddress2());
			ps.setString(6, emp.getCity());
			ps.setInt(7, emp.getZip());
			ps.setString(8, emp.getPhone());
			ps.setString(9, emp.getState());
			
			ps.executeUpdate();
			
			rs = ps.getGeneratedKeys();
			
			if(rs.next()){
				emp.setId(rs.getInt(1));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AppException("Error in adding new entry to the database", e.getCause());
		} finally {
			DBUtil.closeResources(ps, rs, con);
		}
		
		return emp;
	}
	
	public boolean authenticated(Auth auth){
		
		return true;
	}
}
