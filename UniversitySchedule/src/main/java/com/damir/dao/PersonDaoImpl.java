package com.damir.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.damir.domain.Person;
import com.damir.domain.PersonImpl;

@Component
public class PersonDaoImpl implements PersonDao {
	@Autowired
	private DataSource dataSource;

	public static final Logger LOG = Logger.getLogger(AudienceDaoImpl.class);
	private final static String GET_BY_ID = "SELECT id, name FROM person WHERE id=?";
	private final static String GET_ALL = "SELECT id, name FROM person";
	private final static String INSERT = "INSERT INTO person(name) VALUES(?) RETURNING id";
	private final static String UPDATE = "UPDATE person SET name=? WHERE id=?";
	private final static String DELETE_ALL = "DELETE FROM person";
	private final static String DELETE = "DELETE FROM person WHERE id=?";

	public Person getById(Integer id) throws DaoException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Person person = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(GET_BY_ID);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			resultSet.next();
			person = new PersonImpl();
			person.setId(resultSet.getInt("id"));
			person.setName(resultSet.getString("name"));
		} catch (SQLException e) {
			LOG.info("An problem with sql command within getById method in PersonDaoImpl class", e);
			throw new DaoException("An problem with sql command within getById method in PersonDaoImpl class", e);
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
				LOG.error("Problem with closing ResultSet or PreparedStatement or Connection within getById method in PersonDaoImpl class", e);
				throw new DaoException(
						"Problem with closing ResultSet or PreparedStatement or Connection within getById method in PersonDaoImpl class", e);
			}
		}
		return person;
	}

	@Override
	public List<Person> getAll() throws DaoException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Person person = null;
		List<Person> personList = new ArrayList<Person>();
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(GET_ALL);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				person = new PersonImpl();
				person.setId(resultSet.getInt("id"));
				person.setName(resultSet.getString("name"));
				personList.add(person);
			}
		} catch (SQLException e) {
			LOG.info("An problem with sql command within getAll method in PersonDaoImpl class", e);
			throw new DaoException("An problem with sql command within getAll method in PersonDaoImpl class", e);
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
				LOG.error("Problem with closing ResultSet or PreparedStatement or Connection within getAll method in PersonDaoImpl class", e);
				throw new DaoException(
						"Problem with closing ResultSet or PreparedStatement or Connection within getAll method in PersonDaoImpl class", e);
			}
		}
		return personList;
	}

	@Override
	public Person persist(Person person) throws DaoException {
		Integer id = person.getId();
		if (id == null) {
			id = insert(person);
		} else {
			update(person);
		}
		person.setId(id);
		return person;
	}

	private Integer insert(Person person) throws DaoException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Integer id = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(INSERT);
			statement.setString(1, person.getName());
			resultSet = statement.executeQuery();
			if (resultSet != null && resultSet.next()) {
				id = resultSet.getInt(1);
			}
		} catch (SQLException e) {
			LOG.info("An problem with sql command within insert method in PersonDaoImpl class", e);
			throw new DaoException("An problem with sql command within insert method in PersonDaoImpl class", e);
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
				LOG.error("Problem with closing ResultSet or PreparedStatement or Connection within insert method in PersonDaoImpl class", e);
				throw new DaoException(
						"Problem with closing ResultSet or PreparedStatement or Connection within insert method in PersonDaoImpl class", e);
			}
		}
		return id;
	}

	private void update(Person person) throws DaoException {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(UPDATE);
			statement.setString(1, person.getName());
			statement.setInt(2, person.getId());
			statement.execute();
		} catch (SQLException e) {
			LOG.info("An problem with sql command within update method in PersonDaoImpl class", e);
			throw new DaoException("An problem with sql command within update method in PersonDaoImpl class", e);
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				LOG.error("Problem with closing ResultSet or PreparedStatement or Connection within update method in PersonDaoImpl class", e);
				throw new DaoException(
						"Problem with closing ResultSet or PreparedStatement or Connection within update method in PersonDaoImpl class", e);
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

	@Override
	public void delete(Person person) throws DaoException {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(DELETE);
			statement.setInt(1, person.getId());
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
