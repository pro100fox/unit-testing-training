package com.damir.view.schedule;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.damir.domain.Audience;
import com.damir.domain.DomainException;
import com.damir.domain.Group;
import com.damir.domain.Person;
import com.damir.domain.ScheduleItem;
import com.damir.domain.ScheduleItemImpl;
import com.damir.domain.Subject;
import com.damir.domain.University;

@WebServlet("/schedule/new")
public class ScheduleItemNew extends HttpServlet {
	public static final Logger LOG = Logger.getLogger(ScheduleItemNew.class);
	private static final long serialVersionUID = 1L;
	private University university ;
       
    public ScheduleItemNew() {
        super();
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
		this.university = context.getBean(University.class);
    }

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] urlParts = request.getRequestURI().split("/");
		try {
			ScheduleItem scheduleItem = new ScheduleItemImpl();
			request.setCharacterEncoding("UTF-8");
			Audience audience = university.getAudience(Integer.parseInt(request.getParameter("audience")));
			scheduleItem.setAudience(audience);
			Date dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(request.getParameter("dateTime"));
			scheduleItem.setDateTime(dateTime);
			Group group = university.getGroup(Integer.parseInt(request.getParameter("group")));
			scheduleItem.setGroup(group);
			Person lecturer = university.getLecturer(Integer.parseInt(request.getParameter("lecturer")));
			scheduleItem.setLecturer(lecturer);
			Subject subject = university.getSubject(Integer.parseInt(request.getParameter("subject")));
			scheduleItem.setSubject(subject);
			university.setSchedule(scheduleItem);
			response.sendRedirect("/" + urlParts[1] + "/" + urlParts[2] + "/list");
		} catch (DomainException e) {
			LOG.info("An problem with persisting entity", e);
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		} catch (ParseException e) {
			LOG.info("An problem with parsing date time", e);
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ScheduleItem scheduleItem = new ScheduleItemImpl();
			List<Person> persons = university.getPersons();
			List<Subject> subjects = university.getSubjects();
			List<Audience> audiences = university.getAudiences();
			List<Group> groups = university.getGroups();
			request.setAttribute("scheduleItem", scheduleItem);
			request.setAttribute("persons", persons);
			request.setAttribute("subjects", subjects);
			request.setAttribute("audiences", audiences);
			request.setAttribute("groups", groups);
			request.getRequestDispatcher("/schedule/edit.jsp").forward(request, response); 
		} catch (DomainException e) {
			LOG.info("An problem with handl user responce", e);
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}
}
