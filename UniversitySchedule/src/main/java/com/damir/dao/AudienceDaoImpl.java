package com.damir.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

import com.damir.domain.Audience;
import com.damir.domain.AudienceImpl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AudienceDaoImpl implements AudienceDao {
	public static final Logger LOG = Logger.getLogger(AudienceDaoImpl.class);
	private final static String GET_BY_ID = "SELECT id, name FROM audience WHERE id=?";
	private final static String GET_ALL = "SELECT id, name FROM audience";
	private final static String INSERT = "INSERT INTO audience(name) VALUES(?) RETURNING id";
	private final static String UPDATE = "UPDATE audience SET name=? WHERE id=?";
	private final static String DELETE_ALL = "DELETE FROM audience";
	private final static String DELETE = "DELETE FROM audience WHERE id=?";
	
	@Autowired
	private DataSource dataSource;

	public Audience getById(Integer id) throws DaoException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Audience audience = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(GET_BY_ID);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			resultSet.next();
			audience = new AudienceImpl();
			audience.setId(resultSet.getInt("id"));
			audience.setName(resultSet.getString("name"));
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
		return audience;
	}

	@Override
	public List<Audience> getAll() throws DaoException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Audience audience = null;
		List<Audience> audienceList = new ArrayList<Audience>();
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(GET_ALL);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				audience = new AudienceImpl();
				audience.setId(resultSet.getInt("id"));
				audience.setName(resultSet.getString("name"));
				audienceList.add(audience);
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
		return audienceList;
	}

	@Override
	public Audience persist(Audience audience) throws DaoException {
		Integer id = audience.getId();
		if (id == null) {
			id = insert(audience);
		} else {
			update(audience);
		}
		audience.setId(id);
		return audience;
	}

	private Integer insert(Audience audience) throws DaoException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Integer id = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(INSERT);
			statement.setString(1, audience.getName());
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

	private void update(Audience audience) throws DaoException {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(UPDATE);
			statement.setString(1, audience.getName());
			statement.setInt(2, audience.getId());
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
	public void delete(Audience audience) throws DaoException {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(DELETE);
			statement.setInt(1, audience.getId());
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
