package com.damir.view.audience;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.damir.dao.SpringConfiguration;
import com.damir.domain.Audience;
import com.damir.domain.AudienceImpl;
import com.damir.domain.DomainException;
import com.damir.domain.University;

@WebServlet("/audience/edit/*")
@Configurable
public class AudienceEdit extends HttpServlet {
	public static final Logger LOG = Logger.getLogger(AudienceEdit.class);
	private static final long serialVersionUID = 1L;
	private University university;

	public AudienceEdit() {
        super();
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
		this.university = context.getBean(University.class);
    }

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] urlParts = request.getRequestURI().split("/");
		Audience audience = new AudienceImpl();
		request.setCharacterEncoding("UTF-8");
		audience.setId(Integer.parseInt(request.getParameter("id")));
		audience.setName(request.getParameter("name"));
		try {
			university.setAudience(audience);
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
			Audience audience = university.getAudience(id);
			request.setAttribute("audience", audience);
			request.getRequestDispatcher("/audience/edit.jsp").forward(request, response);            
		} catch (DomainException e) {
			LOG.info("An problem with handl user responce", e);
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

}
