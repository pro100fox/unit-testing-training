package com.damir.view.group;

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
import com.damir.domain.Group;
import com.damir.domain.GroupImpl;
import com.damir.domain.Person;
import com.damir.domain.University;

@WebServlet("/group/edit/*")
public class GroupEdit extends HttpServlet {
	public static final Logger LOG = Logger.getLogger(GroupEdit.class);
	private static final long serialVersionUID = 1L;
	private University university;
       
    public GroupEdit() {
        super();
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
		this.university = context.getBean(University.class);
    }

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String[] urlParts = request.getRequestURI().split("/");
		String action = request.getParameter("action");
		if (action.equals("editGroup")) {
			Group group = new GroupImpl();
			group.setId(Integer.parseInt(request.getParameter("id")));
			group.setName(request.getParameter("name"));
			try {
				university.setGroup(group);
				response.sendRedirect("/" + urlParts[1] + "/" + urlParts[2] + "/list");
			} catch (DomainException e) {
				LOG.info("An problem with persisting entity", e);
				request.getRequestDispatcher("/error.jsp").forward(request, response);
			}			
		} else {
			Integer groupId = Integer.parseInt(urlParts[4]);
			Integer studentId = Integer.parseInt(request.getParameter("student"));
			try {
				Group group = university.getGroup(groupId);
				Person student = university.getPerson(studentId);
				List<Person> students = group.getStudents();
				students.add(student);
				group.setStudents(students);
				university.setGroup(group);
				doGet(request, response);  
			} catch (DomainException e) {
				LOG.info("An problem with persisting entity", e);
				request.getRequestDispatcher("/error.jsp").forward(request, response);
			}
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] urlParts = request.getRequestURI().split("/");
		try {
			Integer id = Integer.parseInt(urlParts[4]);
			Group group = university.getGroup(id);
			request.setAttribute("group", group);
			List<Person> persons = university.getPersons();
			List<Person> students = group.getStudents();
			request.setAttribute("persons", persons);
			request.setAttribute("students", students);
			request.getRequestDispatcher("/group/edit.jsp").forward(request, response);            
		} catch (DomainException e) {
			LOG.info("An problem with handl user responce", e);
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

}
