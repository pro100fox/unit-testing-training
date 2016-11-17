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

@WebServlet("/student/new")
public class StudentNew extends HttpServlet {
	public static final Logger LOG = Logger.getLogger(StudentNew.class);
	private static final long serialVersionUID = 1L;
	private University university;
       
    public StudentNew() {
        super();
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
		this.university = context.getBean(University.class);
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Person student = new PersonImpl();
			Person persistedStudent = university.setStudent(student);
			response.sendRedirect("edit/" + persistedStudent.getId());
		} catch (DomainException e) {
			LOG.info("An problem with handl user responce", e);
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}
}
