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

@WebServlet("/lecturer/edit/*")
public class LecturerEdit extends HttpServlet {
	public static final Logger LOG = Logger.getLogger(LecturerEdit.class);
	private static final long serialVersionUID = 1L;
	private University university;
       
    public LecturerEdit() {
        super();
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
		this.university = context.getBean(University.class);
    }

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] urlParts = request.getRequestURI().split("/");
		Person lecturer = new PersonImpl();
		request.setCharacterEncoding("UTF-8");
		lecturer.setId(Integer.parseInt(request.getParameter("id")));
		lecturer.setName(request.getParameter("name"));
		try {
			university.setPerson(lecturer);
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
			Person lecturer = university.getPerson(id);
			request.setAttribute("lecturer", lecturer);
			request.getRequestDispatcher("/lecturer/edit.jsp").forward(request, response);            
		} catch (DomainException e) {
			LOG.info("An problem with handl user responce", e);
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

}
