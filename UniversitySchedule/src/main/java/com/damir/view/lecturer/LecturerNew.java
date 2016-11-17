package com.damir.view.lecturer;

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

@WebServlet("/lecturer/new")
public class LecturerNew extends HttpServlet {
	public static final Logger LOG = Logger.getLogger(LecturerNew.class);
	private static final long serialVersionUID = 1L;
	private University university;
       
    public LecturerNew() {
        super();
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
		this.university = context.getBean(University.class);
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Person lecturer = new PersonImpl();
			Person persistedLecturer = university.setLecturer(lecturer);
			response.sendRedirect("edit/" + persistedLecturer.getId());
		} catch (DomainException e) {
			LOG.info("An problem with handl user responce", e);
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}
}
