package com.damir.view.subject;

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
import com.damir.domain.Subject;
import com.damir.domain.SubjectImpl;
import com.damir.domain.University;

@WebServlet("/subject/edit/*")
public class SubjectEdit extends HttpServlet {
	public static final Logger LOG = Logger.getLogger(SubjectEdit.class);
	private static final long serialVersionUID = 1L;
	private University university;
       
    public SubjectEdit() {
        super();
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
		this.university = context.getBean(University.class);
    }

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] urlParts = request.getRequestURI().split("/");
		Subject subject = new SubjectImpl();
		request.setCharacterEncoding("UTF-8");
		subject.setId(Integer.parseInt(request.getParameter("id")));
		subject.setTitle(request.getParameter("title"));
		try {
			university.setSubject(subject);
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
			Subject subject = university.getSubject(id);
			request.setAttribute("subject", subject);
			request.getRequestDispatcher("/subject/edit.jsp").forward(request, response);            
		} catch (DomainException e) {
			LOG.info("An problem with handl user responce", e);
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

}
