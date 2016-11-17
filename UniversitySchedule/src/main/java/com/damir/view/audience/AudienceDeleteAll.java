package com.damir.view.audience;

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
import com.damir.domain.University;

@WebServlet("/audience/deleteAll")
public class AudienceDeleteAll extends HttpServlet {
	public static final Logger LOG = Logger.getLogger(AudienceDeleteAll.class);
	private static final long serialVersionUID = 1L;
	private University university;
       
    public AudienceDeleteAll() {
        super();
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
		this.university = context.getBean(University.class);
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			university.deleteAllAudiences();
			request.getRequestDispatcher("/audience/list").forward(request, response);
		} catch (DomainException e) {
			LOG.info("An problem with persisting entity", e);
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

}
