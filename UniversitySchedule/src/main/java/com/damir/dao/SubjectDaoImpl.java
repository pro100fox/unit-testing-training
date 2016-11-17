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

import com.damir.domain.Subject;
import com.damir.domain.SubjectImpl;

@Component
public class SubjectDaoImpl implements SubjectDao {
	@Autowired
	private DataSource dataSource;

	public static final Logger LOG = Logger.getLogger(AudienceDaoImpl.class);
	private final static String GET_BY_ID = "SELECT id, title FROM subject WHERE id=?";
	private final static String GET_ALL = "SELECT id, title FROM subject";
	private final static String INSERT = "INSERT INTO subject(title) VALUES(?) RETURNING id";
	private final static String UPDATE = "UPDATE subject SET title=? WHERE id=?";
	private final static String DELETE_ALL = "DELETE FROM subject";
	private final static String DELETE = "DELETE FROM subject WHERE id=?";

	public Subject getById(Integer id) throws DaoException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Subject subject = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(GET_BY_ID);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			resultSet.next();
			subject = new SubjectImpl();
			subject.setId(resultSet.getInt("id"));
			subject.setTitle(resultSet.getString("title"));
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
		return subject;
	}

	@Override
	public List<Subject> getAll() throws DaoException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Subject subject = null;
		List<Subject> subjectList = new ArrayList<Subject>();
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(GET_ALL);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				subject = new SubjectImpl();
				subject.setId(resultSet.getInt("id"));
				subject.setTitle(resultSet.getString("title"));
				subjectList.add(subject);
			}
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
		return subjectList;
	}

	@Override
	public Subject persist(Subject subject) throws DaoException {
		Integer id = subject.getId();
		if (id == null) {
			id = insert(subject);
		} else {
			update(subject);
		}
		subject.setId(id);
		return subject;
	}

	private Integer insert(Subject subject) throws DaoException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Integer id = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(INSERT);
			statement.setString(1, subject.getTitle());
			resultSet = statement.executeQuery();
			if (resultSet != null && resultSet.next()) {
				id = resultSet.getInt(1);
			}
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
		return id;
	}

	private void update(Subject subject) throws DaoException {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(UPDATE);
			statement.setString(1, subject.getTitle());
			statement.setInt(2, subject.getId());
			statement.execute();
		} catch (SQLException e) {
			LOG.info("An problem with sql command within getById method", e);
			throw new DaoException("An problem with sql command within getById method", e);
		} finally {
			try {
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
	public void delete(Subject subject) throws DaoException {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(DELETE);
			statement.setInt(1, subject.getId());
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
