package com.damir.view.schedule;

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
import com.damir.domain.ScheduleItem;
import com.damir.domain.ScheduleItemImpl;
import com.damir.domain.University;

@WebServlet("/schedule/delete/*")
public class ScheduleItemDelete extends HttpServlet {
	public static final Logger LOG = Logger.getLogger(ScheduleItemDelete.class);
	private static final long serialVersionUID = 1L;
	private University university;
       
    public ScheduleItemDelete() {
        super();
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
		this.university = context.getBean(University.class);
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] urlParts = request.getRequestURI().split("/");
		try {
			Integer id = Integer.parseInt(urlParts[4]);
			ScheduleItem scheduleItem = new ScheduleItemImpl();
			scheduleItem.setId(id);
			university.deleteSchedule(scheduleItem);
			request.getRequestDispatcher("/schedule/list").forward(request, response);
		} catch (DomainException e) {
			LOG.info("An problem with persisting entity", e);
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

}
