package com.damir.view.subject;

import java.io.IOException;
import java.util.List;

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
import com.damir.domain.University;

@WebServlet("/subject/list")
public class SubjectList extends HttpServlet {
	public static final Logger LOG = Logger.getLogger(SubjectList.class);
	private static final long serialVersionUID = 1L;
	private University university;
       
    public SubjectList() {
        super();
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
		this.university = context.getBean(University.class);
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			List<Subject> subjects = university.getSubjects();
			request.setAttribute("subjects", subjects);
			request.getRequestDispatcher("/subject/list.jsp").forward(request, response);            
		} catch (DomainException e) {
			LOG.info("An problem with handl user responce", e);
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

}