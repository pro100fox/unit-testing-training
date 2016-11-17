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

@WebServlet("/group/delete/*")
public class GroupDelete extends HttpServlet {
	public static final Logger LOG = Logger.getLogger(GroupDelete.class);
	private static final long serialVersionUID = 1L;
	private University university;
       
    public GroupDelete() {
        super();
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
		this.university = context.getBean(University.class);
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] urlParts = request.getRequestURI().split("/");
		try {
			Integer id = Integer.parseInt(urlParts[4]);
			Group group = new GroupImpl();
			group.setId(id);
			university.deleteGroup(group);
			request.getRequestDispatcher("/group/list").forward(request, response);
		} catch (DomainException e) {
			LOG.info("An problem with persisting entity", e);
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

}
