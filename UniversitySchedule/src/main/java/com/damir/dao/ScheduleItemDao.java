package com.damir.dao;

import java.util.Date;
import java.util.List;

import com.damir.domain.Group;
import com.damir.domain.Person;
import com.damir.domain.ScheduleItem;
import com.damir.domain.Subject;

public interface ScheduleItemDao {
	public ScheduleItem getById(Integer id) throws DaoException;
	public List<ScheduleItem> getByPerson(Person person) throws DaoException;
	public List<ScheduleItem> getAll() throws DaoException;
	public ScheduleItem persist(ScheduleItem scheduleItem) throws DaoException;
	public void delete(ScheduleItem scheduleItem) throws DaoException;
	public void delete(Date dateTime, Group group, Subject subject) throws DaoException;
	public List<Person> getLecturers() throws DaoException;
	public void deleteAll() throws DaoException;
}
