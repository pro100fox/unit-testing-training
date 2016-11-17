package com.damir.domain;

import java.util.Date;
import java.util.List;

public interface University {
	public List<Group> getGroups() throws DomainException;
	public void setGroups(List<Group> groups) throws DomainException;
	
	public List<Subject> getSubjects() throws DomainException;
	public void setSubjects(List<Subject> subjects) throws DomainException;
	
	public List<Audience> getAudiences() throws DomainException;
	public void setAudiences(List<Audience> audiences) throws DomainException;
	
	public List<Person> getStudents() throws DomainException;
	
	public List<Person> getLecturers() throws DomainException;
	
	public void addToSchedule(Date dateTime, Person lecturer, Group group, Audience audience, Subject subject) throws DomainException;
	
	public List<ScheduleItem> getSchedule() throws DomainException;
	public void setSchedule(List<ScheduleItem> schedule) throws DomainException;
	public void removeFromSchedule(Date dateTime, Group group, Subject subject) throws DomainException;
	public List<ScheduleItem> getSchedule(Person person) throws DomainException;

	public void deleteAll() throws DomainException;
	public Audience getAudience(Integer id) throws DomainException;
	public Audience setAudience(Audience audience) throws DomainException;

	public Person getPerson(Integer id) throws DomainException;
	public Person setPerson(Person person) throws DomainException;
	public Person getStudent(Integer id) throws DomainException;
	public Person setStudent(Person student) throws DomainException;
	public Person getLecturer(Integer id) throws DomainException;
	public Person setLecturer(Person lecturer) throws DomainException;
	
	public Subject getSubject(Integer id) throws DomainException;
	public Subject setSubject(Subject subject) throws DomainException;
	
	public Group getGroup(Integer id) throws DomainException;
	public Group setGroup(Group group) throws DomainException;
	
	public ScheduleItem getSchedule(Integer id) throws DomainException;
	public ScheduleItem setSchedule(ScheduleItem schedule) throws DomainException;
	
	public void deleteAllAudiences() throws DomainException;
	public void deleteAllSchedules() throws DomainException;
	public void deleteAllGroups() throws DomainException;
	public void deleteAllSubjects() throws DomainException;
	public void deleteAllPersons() throws DomainException;
	
	public void deleteAudience(Audience audience) throws DomainException;
	public void deletePerson(Person person) throws DomainException;
	public void deleteSubject(Subject subject) throws DomainException;
	public void deleteGroup(Group group) throws DomainException;
	public void deleteSchedule(ScheduleItem schedule) throws DomainException;
	public void deleteAllStudents() throws DomainException;
	public void deleteStudent(Person student) throws DomainException;
	public void deleteAllLecturers() throws DomainException;
	public void deleteLecturer(Person lecturer) throws DomainException;
	public List<Person> getPersons() throws DomainException;
}
