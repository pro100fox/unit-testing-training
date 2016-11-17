package com.damir.view.group;

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
import com.damir.domain.Group;
import com.damir.domain.GroupImpl;
import com.damir.domain.University;

@WebServlet("/group/new")
public class GroupNew extends HttpServlet {
	public static final Logger LOG = Logger.getLogger(GroupNew.class);
	private static final long serialVersionUID = 1L;
	private University university;
       
    public GroupNew() {
        super();
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
		this.university = context.getBean(University.class);
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Group group = new GroupImpl();
			Group persistedGroup = university.setGroup(group);
			response.sendRedirect("edit/" + persistedGroup.getId());
		} catch (DomainException e) {
			LOG.info("An problem with handl user responce", e);
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}
}
