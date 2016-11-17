package com.damir.domain;

import java.util.Date;
//import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Component;

import com.damir.dao.*;

@Component
public class UniversityImpl implements University {
	public static final Logger LOG = Logger.getLogger(AudienceDaoImpl.class);

//	private ScheduleItemDao scheduleItemDao = new ScheduleItemDaoImpl();
	@Autowired
	private ScheduleItemDao scheduleItemDao;
	
//    @Required
//    public void setScheduleItemDao(ScheduleItemDao scheduleItemDao) {
//        this.scheduleItemDao = scheduleItemDao;
//    }

	@Autowired
	private GroupDao groupDao;
	@Autowired
	private SubjectDao subjectDao;
	@Autowired
	private AudienceDao audienceDao;
	@Autowired
	private PersonDao personDao;
	
//	private GroupDao groupDao = new GroupDaoImpl();
//	private SubjectDao subjectDao = new SubjectDaoImpl();
//	private AudienceDao audienceDao = new AudienceDaoImpl();
//	private PersonDao personDao = new PersonDaoImpl();

	@Override
	public List<ScheduleItem> getSchedule() throws DomainException {
		List<ScheduleItem> schedule = null;
		try {
			schedule = scheduleItemDao.getAll();
		} catch (DaoException e) {
			LOG.info("An problem within getSchedule method", e);
			throw new DomainException("An problem within getSchedule method", e);
		}
		return schedule;
	}

	@Override
	public void setSchedule(List<ScheduleItem> schedule) throws DomainException {
		try {
			for (ScheduleItem scheduleItem : schedule) {
				scheduleItemDao.persist(scheduleItem);
			}
		} catch (DaoException e) {
			LOG.info("An problem within setSchedule method", e);
			throw new DomainException("An problem within setSchedule method", e);
		}
	}

	@Override
	public List<Group> getGroups() throws DomainException {
		List<Group> groups = null;
		try {
			groups = groupDao.getAll();
		} catch (DaoException e) {
			LOG.info("An problem within getGroups method", e);
			throw new DomainException("An problem within getGroups method", e);
		}
		return groups;
	}

	@Override
	public void setGroups(List<Group> groups) throws DomainException {
		try {
			for (Group group : groups) {
				groupDao.persist(group);
			}
		} catch (DaoException e) {
			LOG.info("An problem within setGroups method", e);
			throw new DomainException("An problem within setGroups method", e);
		}
	}

	@Override
	public List<Subject> getSubjects() throws DomainException {
		List<Subject> subjects = null;
		try {
			subjects = subjectDao.getAll();
		} catch (DaoException e) {
			LOG.info("An problem within getSubjects method", e);
			throw new DomainException("An problem within getSubjects method", e);
		}
		return subjects;
	}

	@Override
	public void setSubjects(List<Subject> subjects) throws DomainException {
		try {
			for (Subject subject : subjects) {
				subjectDao.persist(subject);
			}
		} catch (DaoException e) {
			LOG.info("An problem within setSubjects method", e);
			throw new DomainException("An problem within setSubjects method", e);
		}
	}

	@Override
	public List<Audience> getAudiences() throws DomainException {
		List<Audience> audiences = null;
		try {
			audiences = audienceDao.getAll();
		} catch (DaoException e) {
			LOG.info("An problem within getAudiences method", e);
			throw new DomainException("An problem within getAudiences method", e);
		}
		return audiences;
	}

	@Override
	public Audience getAudience(Integer id) throws DomainException {
		Audience audience = null;
		try {
			audience = audienceDao.getById(id);
		} catch (DaoException e) {
			LOG.info("An problem within getAudience method", e);
			throw new DomainException("An problem within getAudience method", e);
		}
		return audience;
	}

	@Override
	public Audience setAudience(Audience audience) throws DomainException {
		Audience persistedAudience = null;
		try {
			persistedAudience = audienceDao.persist(audience);
		} catch (DaoException e) {
			LOG.info("An problem within setAudience method", e);
			throw new DomainException("An problem within setAudience method", e);
		}
		return persistedAudience;
	}

	@Override
	public void setAudiences(List<Audience> audiences) throws DomainException {
		try {
			for (Audience audience : audiences) {
				audienceDao.persist(audience);
			}
		} catch (DaoException e) {
			LOG.info("An problem within setAudiences method", e);
			throw new DomainException("An problem within setAudiences method", e);
		}
	}

	@Override
	public List<Person> getStudents() throws DomainException {
		List<Person> students = null;
		try {
			students = groupDao.getStudents();
		} catch (DaoException e) {
			LOG.info("An problem within getStudents method", e);
			throw new DomainException("An problem within getStudents method", e);
		}
		return students;
	}

	@Override
	public List<Person> getLecturers() throws DomainException {
		List<Person> lecturers = null;
		try {
			lecturers = scheduleItemDao.getLecturers();
		} catch (DaoException e) {
			LOG.info("An problem within getLecturers method", e);
			throw new DomainException("An problem within getLecturers method", e);
		}
		return lecturers;
	}

	@Override
	public void addToSchedule(Date dateTime, Person lecturer, Group group, Audience audience, Subject subject) throws DomainException {
		try {
			if (lecturer.getId() == null) {
				personDao.persist(lecturer);
			}
			if (group.getId() == null) {
				groupDao.persist(group);
			}
			if (audience.getId() == null) {
				audienceDao.persist(audience);
			}
			if (subject.getId() == null) {
				subjectDao.persist(subject);
			}
	
			ScheduleItemImpl scheduleItem = new ScheduleItemImpl(dateTime, lecturer, group, audience, subject);			
			scheduleItemDao.persist(scheduleItem);
		} catch (DaoException e) {
			LOG.info("An problem within addToSchedule method", e);
			throw new DomainException("An problem within addToSchedule method", e);
		}
	}

	@Override
	public void removeFromSchedule(Date dateTime, Group group, Subject subject) throws DomainException {
		try {
			scheduleItemDao.delete(dateTime, group, subject);
		} catch (DaoException e) {
			LOG.info("An problem within removeFromSchedule method", e);
			throw new DomainException("An problem within removeFromSchedule method", e);
		}
	}

	@Override
	public List<ScheduleItem> getSchedule(Person person) throws DomainException {
		List<ScheduleItem> personsSchedule = null;
		try {
			personsSchedule = scheduleItemDao.getByPerson(person);
		} catch (DaoException e) {
			LOG.info("An problem within getSchedule method", e);
			throw new DomainException("An problem within getSchedule method", e);
		}		
		return personsSchedule;
	}

	@Override
	public void deleteAll() throws DomainException {
		try {
			scheduleItemDao.deleteAll();
			groupDao.deleteAll();
			subjectDao.deleteAll();
			audienceDao.deleteAll();
			personDao.deleteAll();
		} catch (DaoException e) {
			LOG.info("An problem within deleteAll method", e);
			throw new DomainException("An problem within deleteAll method", e);
		}
	}

	@Override
	public void deleteAllAudiences() throws DomainException {
		try {
			audienceDao.deleteAll();
		} catch (DaoException e) {
			LOG.info("An problem within deleteAllAudiences method", e);
			throw new DomainException("An problem within deleteAllAudiences method", e);
		}		
	}

	@Override
	public void deleteAllSchedules() throws DomainException {
		try {
			scheduleItemDao.deleteAll();
		} catch (DaoException e) {
			LOG.info("An problem within deleteAllSchedules method", e);
			throw new DomainException("An problem within deleteAllSchedules method", e);
		}
	}

	@Override
	public void deleteAllGroups() throws DomainException {
		try {
			groupDao.deleteAll();
		} catch (DaoException e) {
			LOG.info("An problem within deleteAllGroups method", e);
			throw new DomainException("An problem within deleteAllGroups method", e);
		}
	}

	@Override
	public void deleteAllSubjects() throws DomainException {
		try {
			subjectDao.deleteAll();
		} catch (DaoException e) {
			LOG.info("An problem within deleteAllSubjects method", e);
			throw new DomainException("An problem within deleteAllSubjects method", e);
		}
	}

	@Override
	public void deleteAllPersons() throws DomainException {
		try {
			personDao.deleteAll();
		} catch (DaoException e) {
			LOG.info("An problem within deleteAllPersons method", e);
			throw new DomainException("An problem within deleteAllPersons method", e);
		}
	}

	@Override
	public void deleteSchedule(ScheduleItem schedule) throws DomainException {
		try {
			scheduleItemDao.delete(schedule);
		} catch (DaoException e) {
			LOG.info("An problem within deleteAll method", e);
			throw new DomainException("An problem within deleteAll method", e);
		}
	}

	@Override
	public void deleteGroup(Group group) throws DomainException {
		try {
			groupDao.delete(group);
		} catch (DaoException e) {
			LOG.info("An problem within deleteAll method", e);
			throw new DomainException("An problem within deleteAll method", e);
		}
	}

	@Override
	public void deleteSubject(Subject subject) throws DomainException {
		try {
			subjectDao.delete(subject);
		} catch (DaoException e) {
			LOG.info("An problem within deleteAll method", e);
			throw new DomainException("An problem within deleteAll method", e);
		}
	}

	@Override
	public void deleteAudience(Audience audience) throws DomainException {
		try {
			audienceDao.delete(audience);
		} catch (DaoException e) {
			LOG.info("An problem within deleteAll method", e);
			throw new DomainException("An problem within deleteAll method", e);
		}
	}

	@Override
	public void deletePerson(Person person) throws DomainException {
		try {
			personDao.delete(person);
		} catch (DaoException e) {
			LOG.info("An problem within deleteAll method", e);
			throw new DomainException("An problem within deleteAll method", e);
		}
	}

	@Override
	public Person getPerson(Integer id) throws DomainException {
		Person person = null;
		try {
			person = personDao.getById(id);
		} catch (DaoException e) {
			LOG.info("An problem within getAudience method", e);
			throw new DomainException("An problem within getAudience method", e);
		}
		return person;
	}

	@Override
	public Person setPerson(Person person) throws DomainException {
		Person persistedPerson = null;
		try {
			persistedPerson = personDao.persist(person);
		} catch (DaoException e) {
			LOG.info("An problem within setAudience method", e);
			throw new DomainException("An problem within setAudience method", e);
		}
		return persistedPerson;
	}

	@Override
	public Person getStudent(Integer id) throws DomainException {
		return getPerson(id) ;
	}

	@Override
	public Person setStudent(Person student) throws DomainException {
		return setPerson(student);
	}

	@Override
	public Person getLecturer(Integer id) throws DomainException {
		return getPerson(id) ;
	}

	@Override
	public Person setLecturer(Person lecturer) throws DomainException {
		return setPerson(lecturer);
	}

	@Override
	public Subject getSubject(Integer id) throws DomainException {
		Subject subject = null;
		try {
			subject = subjectDao.getById(id);
		} catch (DaoException e) {
			LOG.info("An problem within getAudience method", e);
			throw new DomainException("An problem within getAudience method", e);
		}
		return subject;
	}

	@Override
	public Subject setSubject(Subject subject) throws DomainException {
		Subject persistedSubject = null;
		try {
			persistedSubject = subjectDao.persist(subject);
		} catch (DaoException e) {
			LOG.info("An problem within setAudience method", e);
			throw new DomainException("An problem within setAudience method", e);
		}
		return persistedSubject;
	}

	@Override
	public Group getGroup(Integer id) throws DomainException {
		Group group = null;
		try {
			group = groupDao.getById(id);
		} catch (DaoException e) {
			LOG.info("An problem within getAudience method", e);
			throw new DomainException("An problem within getAudience method", e);
		}
		return group;
	}

	@Override
	public Group setGroup(Group group) throws DomainException {
		Group persistedAudience = null;
		try {
			persistedAudience = groupDao.persist(group);
		} catch (DaoException e) {
			LOG.info("An problem within setAudience method", e);
			throw new DomainException("An problem within setAudience method", e);
		}
		return persistedAudience;
	}

	@Override
	public ScheduleItem getSchedule(Integer id) throws DomainException {
		ScheduleItem schedule = null;
		try {
			schedule = scheduleItemDao.getById(id);
		} catch (DaoException e) {
			LOG.info("An problem within getAudience method", e);
			throw new DomainException("An problem within getAudience method", e);
		}
		return schedule;
	}

	@Override
	public ScheduleItem setSchedule(ScheduleItem schedule) throws DomainException {
		ScheduleItem persistedSchedule = null;
		try {
			persistedSchedule = scheduleItemDao.persist(schedule);
		} catch (DaoException e) {
			LOG.info("An problem within setAudience method", e);
			throw new DomainException("An problem within setAudience method", e);
		}
		return persistedSchedule;
	}

	@Override
	public void deleteAllStudents() throws DomainException {
		deleteAllPersons();		
	}

	@Override
	public void deleteStudent(Person student) throws DomainException {
		deletePerson(student);	
	}

	@Override
	public void deleteAllLecturers() throws DomainException {
		deleteAllPersons();		
	}

	@Override
	public void deleteLecturer(Person lecturer) throws DomainException {
		deletePerson(lecturer);		
	}

	@Override
	public List<Person> getPersons() throws DomainException {
		List<Person> persons = null;
		try {
			persons = personDao.getAll();
		} catch (DaoException e) {
			LOG.info("An problem within getPersons method", e);
			throw new DomainException("An problem within getPersons method", e);
		}
		return persons;
	}
}
