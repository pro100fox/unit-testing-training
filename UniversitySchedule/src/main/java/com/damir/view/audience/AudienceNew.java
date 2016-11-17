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
import com.damir.domain.Audience;
import com.damir.domain.AudienceImpl;
import com.damir.domain.DomainException;
import com.damir.domain.University;

@WebServlet("/audience/new")
public class AudienceNew extends HttpServlet {
	public static final Logger LOG = Logger.getLogger(AudienceNew.class);
	private static final long serialVersionUID = 1L;
	private University university;
       
    public AudienceNew() {
        super();
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
		this.university = context.getBean(University.class);
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Audience audience = new AudienceImpl();
			Audience persistedAudience = university.setAudience(audience);
			response.sendRedirect("edit/" + persistedAudience.getId());
		} catch (DomainException e) {
			LOG.info("An problem with handl user responce", e);
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}
}
