package solutions.egen.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import solution.egen.exceptions.AppException;
import solutions.egen.dao.EmployeeDAO;
import solutions.egen.model.Auth;
import solutions.egen.model.Employee;

@Path("/employee")
public class EmployeeController {

	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public AppResponse getAll() {
		AppResponse resp = new AppResponse();

		try {
			EmployeeDAO dao = new EmployeeDAO();
			List<Employee> empList = dao.getAll();
			resp.setPayload(empList);
		} catch (AppException e) {
			e.printStackTrace();
			resp.setStatus(AppResponse.ERROR);
			resp.setMessage(e.getMessage());
		}
		return resp;
	}

	@GET
	@Path("/get/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public AppResponse getEmployee(@PathParam("id") int empId) {
		AppResponse resp = new AppResponse();

		try {
			EmployeeDAO dao = new EmployeeDAO();
			Employee emp = dao.getPerson(empId);
			resp.setPayload(emp);
		} catch (AppException e) {
			e.printStackTrace();
			resp.setStatus(AppResponse.ERROR);
			resp.setMessage(e.getMessage());
		}
		return resp;
	}

	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public AppResponse addPerson(Employee emp) {
		AppResponse resp = new AppResponse();

		try {
			EmployeeDAO dao = new EmployeeDAO();
			emp = dao.addPerson(emp);
			resp.setMessage("Employee is added successfully");
			resp.setPayload(emp);
		} catch (AppException e) {
			e.printStackTrace();
			resp.setStatus(AppResponse.ERROR);
			resp.setMessage(e.getMessage());
		}
		return resp;
	}

	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public AppResponse login(Auth auth, @Context HttpServletRequest request) {
		AppResponse resp = new AppResponse();

		EmployeeDAO dao = new EmployeeDAO();
		boolean isAuthenticated = dao.authenticated(auth);
		
		HttpSession session = request.getSession(true);
		
		session.setAttribute("USER", auth);
		
		if (isAuthenticated) {
			resp.setMessage("Login Successful");
		} else {
			resp.setMessage("Login unuccessful");
			resp.setStatus(AppResponse.ERROR);
		}
		return resp;
	}
	
	@GET
	@Path("/checkLogin")
	@Produces(MediaType.APPLICATION_JSON)
	public AppResponse checkLogin(@Context HttpServletRequest request) {
		AppResponse resp = new AppResponse();
		
		HttpSession session = request.getSession(false);
		
		
		if(session != null && session.getAttribute("USER")!=null){
			resp.setMessage("User is logged in");
		} else {
			resp.setMessage("No user logged in");
			resp.setStatus(AppResponse.ERROR);
		}
		
		return resp;

	}
}
