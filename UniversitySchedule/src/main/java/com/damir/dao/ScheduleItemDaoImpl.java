package com.damir.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.damir.domain.Audience;
import com.damir.domain.Group;
import com.damir.domain.Person;
import com.damir.domain.PersonImpl;
import com.damir.domain.ScheduleItem;
import com.damir.domain.ScheduleItemImpl;
import com.damir.domain.Subject;

@Component
public class ScheduleItemDaoImpl implements ScheduleItemDao {
	
	@Autowired
	private DataSource dataSource;	
	@Autowired
	private AudienceDao audienceDao;	
	@Autowired
	private PersonDao personDao;	
	@Autowired
	private GroupDao groupDao;	
	@Autowired
	private SubjectDao subjectDao;

	public static final Logger LOG = Logger.getLogger(AudienceDaoImpl.class);
	private final static String GET_BY_ID = "SELECT id, \"dateTime\", lecturer, \"group\", audience, subject FROM \"scheduleItem\" WHERE id=?";
	private final static String GET_BY_PERSON = "SELECT DISTINCT \"scheduleItem\".id, \"scheduleItem\".\"dateTime\", \"scheduleItem\".lecturer, \"scheduleItem\".\"group\", \"scheduleItem\".audience, \"scheduleItem\".subject FROM \"scheduleItem\" LEFT JOIN \"studentsOfGroup\" ON  \"scheduleItem\".\"group\" = \"studentsOfGroup\".\"group\" WHERE \"scheduleItem\".lecturer = ? OR \"studentsOfGroup\".student = ?";
	private final static String GET_ALL = "SELECT id, \"dateTime\", lecturer, \"group\", audience, subject FROM \"scheduleItem\"";
	private final static String GET_LECTURERS = "SELECT DISTINCT \"scheduleItem\".lecturer, person.name FROM \"scheduleItem\" LEFT JOIN person ON \"scheduleItem\".lecturer = person.id";
	private final static String INSERT = "INSERT INTO \"scheduleItem\"(\"dateTime\", lecturer, \"group\", audience, subject) VALUES(?, ?, ?, ?, ?) RETURNING id";
	private final static String UPDATE = "UPDATE \"scheduleItem\" SET \"dateTime\"=?, lecturer=?, \"group\"=?, audience=?, subject=? WHERE id=?";
	private final static String DELETE = "DELETE FROM \"scheduleItem\" WHERE id=?";
	private final static String DELETE_BY_DATE_GROUP_SUBJECT = "DELETE FROM \"scheduleItem\" WHERE \"dateTime\"=? AND \"group\"=? AND subject=?";
	private final static String DELETE_ALL = "DELETE FROM \"scheduleItem\"";

	public ScheduleItem getById(Integer id) throws DaoException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		ScheduleItem scheduleItem = null;
		Timestamp timestamp;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(GET_BY_ID);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			resultSet.next();
			scheduleItem = new ScheduleItemImpl();
			scheduleItem.setId(resultSet.getInt("id"));

			timestamp = resultSet.getTimestamp("dateTime");
			scheduleItem.setDateTime(new java.util.Date(timestamp.getTime()));

			Person lecturer = personDao.getById(resultSet.getInt("lecturer"));
			scheduleItem.setLecturer(lecturer);

			Group group = groupDao.getById(resultSet.getInt("group"));
			scheduleItem.setGroup(group);

			Audience audience = audienceDao.getById(resultSet.getInt("audience"));
			scheduleItem.setAudience(audience);

			Subject subject = subjectDao.getById(resultSet.getInt("subject"));
			scheduleItem.setSubject(subject);
		} catch (SQLException e) {
			LOG.info("An problem with sql command within getById method", e);
			throw new DaoException("An problem with sql command within getById method", e);
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				LOG.error("Problem with closing ResultSet or PreparedStatement or Connection within getById method", e);
				throw new DaoException(
						"Problem with closing ResultSet or PreparedStatement or Connection within getById method", e);
			}
		}
		return scheduleItem;
	}

	@Override
	public List<ScheduleItem> getByPerson(Person person) throws DaoException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		ScheduleItem scheduleItem = null;
		List<ScheduleItem> scheduleItemList = new ArrayList<ScheduleItem>();
		if (person.getId() == null) {
			return scheduleItemList;
		}
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(GET_BY_PERSON);
			statement.setInt(1, person.getId());
			statement.setInt(2, person.getId());
			resultSet = statement.executeQuery();
			Timestamp timestamp;
			while (resultSet.next()) {

				scheduleItem = new ScheduleItemImpl();
				scheduleItem.setId(resultSet.getInt("id"));

				timestamp = resultSet.getTimestamp("dateTime");
				scheduleItem.setDateTime(new java.util.Date(timestamp.getTime()));

				Person lecturer = personDao.getById(resultSet.getInt("lecturer"));
				scheduleItem.setLecturer(lecturer);

				Group group = groupDao.getById(resultSet.getInt("group"));
				scheduleItem.setGroup(group);

				Audience audience = audienceDao.getById(resultSet.getInt("audience"));
				scheduleItem.setAudience(audience);

				Subject subject = subjectDao.getById(resultSet.getInt("subject"));
				scheduleItem.setSubject(subject);

				scheduleItemList.add(scheduleItem);
			}
		} catch (SQLException e) {
			LOG.info("An problem with sql command within getAll method", e);
			throw new DaoException("An problem with sql command within getAll method", e);
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				LOG.error("Problem with closing ResultSet or PreparedStatement or Connection within getAll method", e);
				throw new DaoException(
						"Problem with closing ResultSet or PreparedStatement or Connection within getAll method", e);
			}
		}
		return scheduleItemList;
	}

	@Override
	public List<ScheduleItem> getAll() throws DaoException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		ScheduleItem scheduleItem = null;
		Timestamp timestamp;
		List<ScheduleItem> scheduleItemList = new ArrayList<ScheduleItem>();
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(GET_ALL);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {

				scheduleItem = new ScheduleItemImpl();
				scheduleItem.setId(resultSet.getInt("id"));

				timestamp = resultSet.getTimestamp("dateTime");
				scheduleItem.setDateTime(new java.util.Date(timestamp.getTime()));

				Person lecturer = personDao.getById(resultSet.getInt("lecturer"));
				scheduleItem.setLecturer(lecturer);

				Group group = groupDao.getById(resultSet.getInt("group"));
				scheduleItem.setGroup(group);

				Audience audience = audienceDao.getById(resultSet.getInt("audience"));
				scheduleItem.setAudience(audience);

				Subject subject = subjectDao.getById(resultSet.getInt("subject"));
				scheduleItem.setSubject(subject);

				scheduleItemList.add(scheduleItem);
			}
		} catch (SQLException e) {
			LOG.info("An problem with sql command within getAll method", e);
			throw new DaoException("An problem with sql command within getAll method", e);
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				LOG.error("Problem with closing ResultSet or PreparedStatement or Connection within getAll method", e);
				throw new DaoException(
						"Problem with closing ResultSet or PreparedStatement or Connection within getAll method", e);
			}
		}
		return scheduleItemList;
	}

	@Override
	public ScheduleItem persist(ScheduleItem scheduleItem) throws DaoException {
		Integer id = scheduleItem.getId();
		if (id == null) {
			id = insert(scheduleItem);
		} else {
			update(scheduleItem);
		}
		scheduleItem.setId(id);
		return scheduleItem;
	}

	private Integer insert(ScheduleItem scheduleItem) throws DaoException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Integer id = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(INSERT);
			statement.setTimestamp(1, new Timestamp(scheduleItem.getDateTime().getTime()));						    
			statement.setInt(2, scheduleItem.getLecturer().getId());
			statement.setInt(3, scheduleItem.getGroup().getId());
			statement.setInt(4, scheduleItem.getAudience().getId());
			statement.setInt(5, scheduleItem.getSubject().getId());
			resultSet = statement.executeQuery();
			if (resultSet != null && resultSet.next()) {
				id = resultSet.getInt(1);
			}
		} catch (SQLException e) {
			LOG.info("An problem with sql command within insert method", e);
			throw new DaoException("An problem with sql command within insert method", e);
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				LOG.error("Problem with closing ResultSet or PreparedStatement or Connection within insert method", e);
				throw new DaoException(
						"Problem with closing ResultSet or PreparedStatement or Connection within insert method", e);
			}
		}
		return id;
	}

	private void update(ScheduleItem scheduleItem) throws DaoException {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(UPDATE);
			statement.setTimestamp(1, new Timestamp(scheduleItem.getDateTime().getTime()));	
			statement.setInt(2, scheduleItem.getLecturer().getId());
			statement.setInt(3, scheduleItem.getGroup().getId());
			statement.setInt(4, scheduleItem.getAudience().getId());
			statement.setInt(5, scheduleItem.getSubject().getId());
			statement.setInt(6, scheduleItem.getId());
			statement.execute();
		} catch (SQLException e) {
			LOG.info("An problem with sql command within update method", e);
			throw new DaoException("An problem with sql command within update method", e);
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				LOG.error("Problem with closing ResultSet or PreparedStatement or Connection within update method", e);
				throw new DaoException(
						"Problem with closing ResultSet or PreparedStatement or Connection within update method", e);
			}
		}
	}

	@Override
	public List<Person> getLecturers() throws DaoException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Person student = null;
		List<Person> students = new LinkedList<Person>();
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(GET_LECTURERS);
			System.out.println(connection);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {

				student = new PersonImpl();
				student.setId(resultSet.getInt("lecturer"));
				student.setName(resultSet.getString("name"));

				students.add(student);
			}
		} catch (SQLException e) {
			LOG.info("An problem with sql command within getLecturers method", e);
			throw new DaoException("An problem with sql command within getLecturers method", e);
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				LOG.error("Problem with closing ResultSet or PreparedStatement or Connection within getLecturers method", e);
				throw new DaoException(
						"Problem with closing ResultSet or PreparedStatement or Connection within getLecturers method", e);
			}
		}
		return students;
	}

	@Override
	public void delete(ScheduleItem scheduleItem) throws DaoException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(DELETE);
			statement.setInt(1, scheduleItem.getId());
			resultSet = statement.executeQuery();
		} catch (SQLException e) {
			LOG.info("An problem with sql command within delete method", e);
			throw new DaoException("An problem with sql command within delete method", e);
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				LOG.error("Problem with closing ResultSet or PreparedStatement or Connection within delete method", e);
				throw new DaoException(
						"Problem with closing ResultSet or PreparedStatement or Connection within delete method", e);
			}
		}
	}

	@Override
	public void delete(Date dateTime, Group group, Subject subject) throws DaoException {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(DELETE_BY_DATE_GROUP_SUBJECT);
			statement.setTimestamp(1, new Timestamp(dateTime.getTime()));	
			statement.setInt(2, group.getId());
			statement.setInt(3, subject.getId());
			statement.execute();
		} catch (SQLException e) {
			LOG.info("An problem with sql command within delete method", e);
			throw new DaoException("An problem with sql command within delete method", e);
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				LOG.error("Problem with closing ResultSet or PreparedStatement or Connection within delete method", e);
				throw new DaoException(
						"Problem with closing ResultSet or PreparedStatement or Connection within delete method", e);
			}
		}
	}

	@Override
	public void deleteAll() throws DaoException {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(DELETE_ALL);
			statement.execute();
		} catch (SQLException e) {
			LOG.info("An problem with sql command within delete method", e);
			throw new DaoException("An problem with sql command within delete method", e);
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				LOG.error("Problem with closing ResultSet or PreparedStatement or Connection within delete method", e);
				throw new DaoException(
						"Problem with closing ResultSet or PreparedStatement or Connection within delete method", e);
			}
		}
	}
}
