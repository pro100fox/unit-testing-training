package com.damir.view.student;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.damir.dao.SpringConfiguration;
import com.damir.domain.DomainException;
import com.damir.domain.Person;
import com.damir.domain.PersonImpl;
import com.damir.domain.University;

@WebServlet("/student/edit/*")
public class StudentEdit extends HttpServlet {
	public static final Logger LOG = Logger.getLogger(StudentEdit.class);
	private static final long serialVersionUID = 1L;
	private University university;
       
    public StudentEdit() {
        super();
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
		this.university = context.getBean(University.class);
    }

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] urlParts = request.getRequestURI().split("/");
		Person student = new PersonImpl();
		request.setCharacterEncoding("UTF-8");
		student.setId(Integer.parseInt(request.getParameter("id")));
		student.setName(request.getParameter("name"));
		try {
			university.setStudent(student);
//			doGet(request, response);
//			response.sendRedirect("list");
			response.sendRedirect("/" + urlParts[1] + "/" + urlParts[2] + "/list");
		} catch (DomainException e) {
			LOG.info("An problem with persisting entity", e);
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] urlParts = request.getRequestURI().split("/");
		try {
			Integer id = Integer.parseInt(urlParts[4]);
			Person student = university.getPerson(id);
			request.setAttribute("student", student);
			request.getRequestDispatcher("/student/edit.jsp").forward(request, response);            
		} catch (DomainException e) {
			LOG.info("An problem with handl user responce", e);
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

}
