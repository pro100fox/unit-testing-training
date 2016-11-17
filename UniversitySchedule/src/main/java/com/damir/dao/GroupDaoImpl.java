package com.damir.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.damir.domain.Group;
import com.damir.domain.GroupImpl;
import com.damir.domain.Person;
import com.damir.domain.PersonImpl;

@Component
public class GroupDaoImpl implements GroupDao {
	
	@Autowired
	private DataSource dataSource;
	@Autowired
	private PersonDao studentDao;

	public static final Logger LOG = Logger.getLogger(AudienceDaoImpl.class);
	private final static String GET_BY_ID = "SELECT id, name FROM \"group\" WHERE id=?";
	private final static String GET_STUDENTS_BY_GROUP_ID = "SELECT \"studentsOfGroup\".student as student, person.name FROM \"studentsOfGroup\" LEFT JOIN person ON \"studentsOfGroup\".student = person.id WHERE \"studentsOfGroup\".\"group\"=?";
	private final static String DELETE_STUDENTS_BY_GROUP_ID = "DELETE FROM \"studentsOfGroup\" WHERE \"studentsOfGroup\".\"group\" = ?";
	private final static String INSERT_STUDENT_IN_GROUP = "INSERT INTO \"studentsOfGroup\" (\"group\", student) VALUES (?, ?)";
	private final static String GET_ALL = "SELECT id, name FROM \"group\"";
	private final static String GET_STUDENTS = "SELECT DISTINCT \"studentsOfGroup\".student, person.name FROM \"studentsOfGroup\" LEFT JOIN person ON \"studentsOfGroup\".student = person.id";
	private final static String INSERT = "INSERT INTO \"group\"(name) VALUES(?) RETURNING id";
	private final static String UPDATE = "UPDATE \"group\" SET name=? WHERE id=?";
	private final static String DELETE_ALL = "DELETE FROM \"group\"";
	private final static String DELETE = "DELETE FROM \"group\" WHERE id=?";
	private final static String DELETE_ALL_STUDENTS = "DELETE FROM \"studentsOfGroup\"";

	public Group getById(Integer id) throws DaoException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Group group = null;
		Person student = null;
		List<Person> students = new LinkedList<Person>();
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(GET_BY_ID);
			System.out.println(connection);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			resultSet.next();
			group = new GroupImpl();
			group.setId(resultSet.getInt("id"));
			group.setName(resultSet.getString("name"));
			resultSet.close();
			statement.close();

			statement = connection.prepareStatement(GET_STUDENTS_BY_GROUP_ID);
			System.out.println(connection);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {

				student = new PersonImpl();
				student.setId(resultSet.getInt("student"));
				student.setName(resultSet.getString("name"));

				students.add(student);
			}
			group.setStudents(students);
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
		return group;
	}

	@Override
	public List<Group> getAll() throws DaoException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		PreparedStatement statement2 = null;
		ResultSet resultSet2 = null;
		Group group = null;
		List<Group> groupList = new ArrayList<Group>();
		Person student = null;
		List<Person> students = new LinkedList<Person>();
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(GET_ALL);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				group = new GroupImpl();
				group.setId(resultSet.getInt("id"));
				group.setName(resultSet.getString("name"));

				statement2 = connection.prepareStatement(GET_STUDENTS_BY_GROUP_ID);
				statement2.setInt(1, resultSet.getInt("id"));
				resultSet2 = statement2.executeQuery();

				while (resultSet2.next()) {

					student = new PersonImpl();
					student.setId(resultSet2.getInt("student"));
					student.setName(resultSet2.getString("name"));

					students.add(student);
				}

				group.setStudents(students);
				groupList.add(group);

				resultSet2.close();
				statement2.close();
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
		return groupList;
	}

	@Override
	public Group persist(Group group) throws DaoException {
		Integer id = group.getId();
		if (id == null) {
			id = insert(group);
		} else {
			update(group);
		}
		group.setId(id);
		return group;
	}

	private Integer insert(Group group) throws DaoException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Integer id = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(INSERT);
			statement.setString(1, group.getName());
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				id = resultSet.getInt(1);
				resultSet.close();
				statement.close();

				List<Person> students = group.getStudents();
				for (Person student : students) {
					if (student.getId() == null) {
						studentDao.persist(student);
					}
					statement = connection.prepareStatement(INSERT_STUDENT_IN_GROUP);
					statement.setInt(1, id);
					statement.setInt(2, student.getId());
					statement.execute();

					resultSet.close();
					statement.close();
				}
			} else {
				LOG.info("An problem was occured with getting primary key from inserted row");
				throw new DaoException("An problem was occured with getting primary key from inserted row");
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

	private void update(Group group) throws DaoException {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(UPDATE);
			statement.setString(1, group.getName());
			statement.setInt(2, group.getId());
			statement.execute();
			statement.close();

			statement = connection.prepareStatement(DELETE_STUDENTS_BY_GROUP_ID);
			statement.setInt(1, group.getId());
			statement.execute();
			statement.close();

			List<Person> students = group.getStudents();
			for (Person student : students) {
				if (student.getId() == null) {
					studentDao.persist(student);
				}
				statement = connection.prepareStatement(INSERT_STUDENT_IN_GROUP);
				statement.setInt(1, group.getId());
				statement.setInt(2, student.getId());
				statement.execute();
				
				statement.close();
			}
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
	public List<Person> getStudents() throws DaoException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Person student = null;
		List<Person> students = new LinkedList<Person>();
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(GET_STUDENTS);
			System.out.println(connection);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {

				student = new PersonImpl();
				student.setId(resultSet.getInt("student"));
				student.setName(resultSet.getString("name"));

				students.add(student);
			}
		} catch (SQLException e) {
			LOG.info("An problem with sql command within getStudents method", e);
			throw new DaoException("An problem with sql command within getStudents method", e);
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
				LOG.error("Problem with closing ResultSet or PreparedStatement or Connection within getStudents method", e);
				throw new DaoException(
						"Problem with closing ResultSet or PreparedStatement or Connection within getStudents method", e);
			}
		}
		return students;
	}

	@Override
	public void deleteAll() throws DaoException {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(DELETE_ALL_STUDENTS);
			statement.execute();
			statement.close();
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

	@Override
	public void delete(Group group) throws DaoException {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(DELETE);
			statement.setInt(1, group.getId());
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
