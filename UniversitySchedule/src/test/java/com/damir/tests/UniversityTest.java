
package com.damir.tests;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;

import org.apache.commons.dbcp.cpdsadapter.DriverAdapterCPDS;
import org.apache.commons.dbcp.datasources.SharedPoolDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;

import com.damir.dao.DaoException;
import com.damir.dao.GroupDao;
import com.damir.dao.SpringConfiguration;
import com.damir.domain.*;

public class UniversityTest {
	
	private GroupDao groupDao;
	
	private University university;
	
	@BeforeClass
	public static void setUpDataSource() throws Exception {
	    try {
	        SimpleNamingContextBuilder builder = new SimpleNamingContextBuilder();
	        DriverAdapterCPDS cpds = new DriverAdapterCPDS();
	        cpds.setDriver("org.postgresql.Driver");
	        cpds.setUrl("jdbc:postgresql://localhost:5432/UniversitySchedule_test");
	        cpds.setUser("admin");
	        cpds.setPassword("admin");

	        SharedPoolDataSource dataSource = new SharedPoolDataSource();
	        dataSource.setConnectionPoolDataSource(cpds);
	        dataSource.setMaxActive(10);
	        dataSource.setMaxWait(50);
	        builder.bind("java:/datasources/universitySchedule", dataSource);
	        builder.activate();
	    } catch (NamingException ex) {
	        ex.printStackTrace();
	    }
	}

	@Before
	public void setUp() throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
		this.university = context.getBean(University.class);
		this.groupDao = context.getBean(GroupDao.class);
		university.deleteAll();
	}

	@After
	public void tearDown() throws Exception {
		university.deleteAll();
	}
	
	@Test
	public void scheduleWithCommonGroupOfStudentsIsntMixedGetScheduleTest() throws DomainException {

		Calendar calendar = Calendar.getInstance();
		calendar.set(2016, 11, 20, 9, 15);
		Date dateTime = calendar.getTime();
		Person lecturer = new PersonImpl("Иванов Иван Иванович");
		Person student = new PersonImpl("Плисова Татьяна Михайловна");
		Person student2 = new PersonImpl("Терентьев Алексей Станиславович");

		Group group = new GroupImpl("15689");
		List<Person> students = group.getStudents();
		students.add(student);
		students.add(student2);
		group.setStudents(students);

		Group group2 = new GroupImpl("14623");
		students = group2.getStudents();
		students.add(student);
		group2.setStudents(students);

		Audience audience = new AudienceImpl("а568");
		Subject subject = new SubjectImpl("Органическая химия");
		university.addToSchedule(dateTime, lecturer, group, audience, subject);

		calendar.set(2016, 11, 21, 9, 15);
		dateTime = calendar.getTime();
		Person lecturer2 = new PersonImpl("Арсеньев Вячеслав Измануилович");
		university.addToSchedule(dateTime, lecturer2, group, audience, subject);

		calendar.set(2016, 11, 22, 9, 15);
		dateTime = calendar.getTime();
		university.addToSchedule(dateTime, lecturer, group, audience, subject);

		calendar.set(2016, 11, 23, 9, 15);
		dateTime = calendar.getTime();
		university.addToSchedule(dateTime, lecturer, group2, audience, subject);

		assertEquals(4, university.getSchedule(student).size());

		university.deleteAll();
	}

	@Test
	public void blankScheduleGetScheduleTest() throws DomainException {
		Person lecturer = new PersonImpl("Иванов Иван Иванович");
		assertEquals(0, university.getSchedule(lecturer).size());
		university.deleteAll();
	}

	@Test
	public void blankScheduleWithWrongLecturerGetScheduleTest() throws DomainException {

		Calendar calendar = Calendar.getInstance();
		calendar.set(2016, 11, 20, 9, 15);
		Date dateTime = calendar.getTime();
		Person lecturer = new PersonImpl("Иванов Иван Иванович");
		Group group = new GroupImpl("15689");
		Audience audience = new AudienceImpl("а568");
		Subject subject = new SubjectImpl("Органическая химия");
		university.addToSchedule(dateTime, lecturer, group, audience, subject);
		assertEquals(1, university.getSchedule(lecturer).size());

		PersonImpl lecturer2 = new PersonImpl("Арсеньев Вячеслав Измануилович");
		assertEquals(0, university.getSchedule(lecturer2).size());
		university.deleteAll();

	}

	@Test
	public void singleScheduleIsFundGetScheduleTest() throws DomainException {

		Calendar calendar = Calendar.getInstance();
		calendar.set(2016, 11, 20, 9, 15);
		Date dateTime = calendar.getTime();
		Person lecturer = new PersonImpl("Иванов Иван Иванович");
		Group group = new GroupImpl("15689");
		Audience audience = new AudienceImpl("а568");
		Subject subject = new SubjectImpl("Органическая химия");
		university.addToSchedule(dateTime, lecturer, group, audience, subject);

		assertEquals(1, university.getSchedule(lecturer).size());
		assertEquals(calendar.getTime(), university.getSchedule(lecturer).get(0).getDateTime());
		assertEquals("15689", university.getSchedule(lecturer).get(0).getGroup().getName());
		assertEquals("Иванов Иван Иванович", university.getSchedule(lecturer).get(0).getLecturer().getName());
		assertEquals("Органическая химия", university.getSchedule(lecturer).get(0).getSubject().getTitle());
		assertEquals("а568", university.getSchedule(lecturer).get(0).getAudience().getName());

		university.deleteAll();

	}

	@Test
	public void singleScheduleWithLecturerFromUniversityGetScheduleTest() throws DomainException {

		Calendar calendar = Calendar.getInstance();
		calendar.set(2016, 11, 20, 9, 15);
		Date dateTime = calendar.getTime();
		Person lecturer = new PersonImpl("Иванов Иван Иванович");
		Group group = new GroupImpl("15689");
		Audience audience = new AudienceImpl("а568");
		Subject subject = new SubjectImpl("Органическая химия");
		university.addToSchedule(dateTime, lecturer, group, audience, subject);

		Person lecturerFromUniversity = university.getLecturers().get(0);

		assertEquals(1, university.getSchedule(lecturerFromUniversity).size());
		assertEquals(calendar.getTime(), university.getSchedule(lecturerFromUniversity).get(0).getDateTime());
		assertEquals("15689", university.getSchedule(lecturerFromUniversity).get(0).getGroup().getName());
		assertEquals("Иванов Иван Иванович",
				university.getSchedule(lecturerFromUniversity).get(0).getLecturer().getName());
		assertEquals("Органическая химия",
				university.getSchedule(lecturerFromUniversity).get(0).getSubject().getTitle());
		assertEquals("а568", university.getSchedule(lecturerFromUniversity).get(0).getAudience().getName());

		university.deleteAll();

	}

	@Test
	public void scheduleOfDifferentLecturersIsntMixedGetScheduleTest() throws DomainException {

		Calendar calendar = Calendar.getInstance();
		calendar.set(2016, 11, 20, 9, 15);
		Date dateTime = calendar.getTime();
		Person lecturer = new PersonImpl("Иванов Иван Иванович");
		Group group = new GroupImpl("15689");
		Audience audience = new AudienceImpl("а568");
		Subject subject = new SubjectImpl("Органическая химия");
		university.addToSchedule(dateTime, lecturer, group, audience, subject);

		calendar.set(2016, 11, 21, 9, 15);
		dateTime = calendar.getTime();
		Person lecturer2 = new PersonImpl("Арсеньев Вячеслав Измануилович");
		university.addToSchedule(dateTime, lecturer2, group, audience, subject);

		calendar.set(2016, 11, 22, 9, 15);
		dateTime = calendar.getTime();
		university.addToSchedule(dateTime, lecturer, group, audience, subject);

		assertEquals(2, university.getSchedule(lecturer).size());

		university.deleteAll();

	}

	@Test
	public void addingStudentToExistingGroupWithinScheduleGetScheduleTest() throws DomainException {

		Calendar calendar = Calendar.getInstance();
		calendar.set(2016, 11, 20, 9, 15);
		Date dateTime = calendar.getTime();
		Person lecturer = new PersonImpl("Иванов Иван Иванович");
		Group group = new GroupImpl("15689");
		Audience audience = new AudienceImpl("а568");
		Subject subject = new SubjectImpl("Органическая химия");
		university.addToSchedule(dateTime, lecturer, group, audience, subject);

		calendar.set(2016, 11, 21, 9, 15);
		dateTime = calendar.getTime();
		Person lecturer2 = new PersonImpl("Арсеньев Вячеслав Измануилович");
		university.addToSchedule(dateTime, lecturer2, group, audience, subject);

		calendar.set(2016, 11, 22, 9, 15);
		dateTime = calendar.getTime();
		university.addToSchedule(dateTime, lecturer, group, audience, subject);

		Person student = new PersonImpl("Плисова Татьяна Михайловна");
		List<Person> students = group.getStudents();
		students.add(student);
		group.setStudents(students);
		try {
			groupDao.persist(group);
		} catch (DaoException e) {
			throw new DomainException(
					"An problem within addingStudentToExistingGroupWithinScheduleGetScheduleTest method", e);
		}

		assertEquals(3, university.getSchedule(student).size());

		university.deleteAll();
	}


	@Test
	public void deletingEventFromScheduleGetScheduleTest() throws DomainException {

		Calendar calendar = Calendar.getInstance();
		calendar.set(2016, 11, 20, 9, 15);
		Date dateTime = calendar.getTime();
		Person lecturer = new PersonImpl("Иванов Иван Иванович");
		Group group = new GroupImpl("15689");
		Audience audience = new AudienceImpl("а568");
		Subject subject = new SubjectImpl("Органическая химия");
		Person student = new PersonImpl("Плисова Татьяна Михайловна");
		List<Person> students = group.getStudents();
		students.add(student);
		group.setStudents(students);
		university.addToSchedule(dateTime, lecturer, group, audience, subject);

		calendar.set(2016, 11, 21, 9, 15);
		dateTime = calendar.getTime();
		PersonImpl lecturer2 = new PersonImpl("Арсеньев Вячеслав Измануилович");
		university.addToSchedule(dateTime, lecturer2, group, audience, subject);

		calendar.set(2016, 11, 22, 9, 15);
		dateTime = calendar.getTime();
		university.addToSchedule(dateTime, lecturer, group, audience, subject);

		university.removeFromSchedule(dateTime, group, subject);

		assertEquals(2, university.getSchedule(student).size());

		university.deleteAll();
	}

	@Test
	public void addToScheduleTest() throws DomainException {

		Calendar calendar = Calendar.getInstance();
		calendar.set(2016, 11, 20, 9, 15);
		Date dateTime = calendar.getTime();
		Person lecturer = new PersonImpl("Иванов Иван Иванович");
		Group group = new GroupImpl("15689");
		Audience audience = new AudienceImpl("а568");
		Subject subject = new SubjectImpl("Органическая химия");

		university.addToSchedule(dateTime, lecturer, group, audience, subject);

		assertEquals(1, university.getSchedule().size());
		assertEquals(calendar.getTime(), university.getSchedule().get(0).getDateTime());
		assertEquals("15689", university.getSchedule().get(0).getGroup().getName());
		assertEquals("Иванов Иван Иванович", university.getSchedule().get(0).getLecturer().getName());
		assertEquals("Органическая химия", university.getSchedule().get(0).getSubject().getTitle());
		assertEquals("а568", university.getSchedule().get(0).getAudience().getName());

		assertEquals(1, university.getLecturers().size());
		assertEquals("Иванов Иван Иванович", university.getLecturers().get(0).getName());

		assertEquals(1, university.getGroups().size());
		assertEquals("15689", university.getGroups().get(0).getName());

		assertEquals(1, university.getAudiences().size());
		assertEquals("а568", university.getAudiences().get(0).getName());

		assertEquals(1, university.getSubjects().size());
		assertEquals("Органическая химия", university.getSubjects().get(0).getTitle());

		university.addToSchedule(dateTime, lecturer, group, audience, subject);

		assertEquals(2, university.getSchedule().size());
		assertEquals(1, university.getLecturers().size());
		assertEquals(1, university.getGroups().size());
		assertEquals(1, university.getAudiences().size());
		assertEquals(1, university.getSubjects().size());

		university.deleteAll();
	}

	@Test
	public void removeFromScheduleTest() throws DomainException {

		Calendar calendar = Calendar.getInstance();
		calendar.set(2016, 11, 20, 9, 15);
		Date dateTime = calendar.getTime();
		Person lecturer = new PersonImpl("Иванов Иван Иванович");
		Group group = new GroupImpl("15689");
		Audience audience = new AudienceImpl("а568");
		Subject subject = new SubjectImpl("Органическая химия");

		university.addToSchedule(dateTime, lecturer, group, audience, subject);

		assertEquals(1, university.getSchedule().size());

		university.removeFromSchedule(dateTime, group, subject);

		assertEquals(0, university.getSchedule().size());

		university.deleteAll();
	}

}
