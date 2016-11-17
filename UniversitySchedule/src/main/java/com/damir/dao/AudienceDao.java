package com.damir.dao;

import java.util.List;

import com.damir.domain.Audience;

public interface AudienceDao {
	public Audience getById(Integer id) throws DaoException;
	public List<Audience> getAll() throws DaoException;
	public Audience persist(Audience audience) throws DaoException;
	public void deleteAll() throws DaoException;
	public void delete(Audience audience) throws DaoException;
}
