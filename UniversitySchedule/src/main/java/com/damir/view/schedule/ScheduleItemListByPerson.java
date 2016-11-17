package com.damir.view.schedule;

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
import com.damir.domain.Person;
import com.damir.domain.PersonImpl;
import com.damir.domain.ScheduleItem;
import com.damir.domain.University;

@WebServlet("/schedule/listByPerson/*")
public class ScheduleItemListByPerson extends HttpServlet {
	public static final Logger LOG = Logger.getLogger(ScheduleItemListByPerson.class);
	private static final long serialVersionUID = 1L;
	private University university;
       
    public ScheduleItemListByPerson() {
        super();
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
		this.university = context.getBean(University.class);
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String[] urlParts = request.getRequestURI().split("/");
			Person person = new PersonImpl();
			person.setId(Integer.parseInt(urlParts[4]));
			
			List<ScheduleItem> scheduleItems = university.getSchedule(person);
			request.setAttribute("scheduleItems", scheduleItems);
			request.getRequestDispatcher("/schedule/list.jsp").forward(request, response);            
		} catch (DomainException e) {
			LOG.info("An problem with handl user responce", e);
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

}
