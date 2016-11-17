package com.damir.domain;

import java.util.Date;

public interface ScheduleItem {
	public Integer getId();
	public void setId(Integer id);
	public Date getDateTime();
	public void setDateTime(Date dateTime);
	public Person getLecturer();
	public void setLecturer(Person lecturer);
	public Group getGroup();
	public void setGroup(Group group);
	public Audience getAudience();
	public void setAudience(Audience audience);
	public Subject getSubject();
	public void setSubject(Subject subject);
}
