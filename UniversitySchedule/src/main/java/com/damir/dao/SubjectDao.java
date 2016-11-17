package com.damir.dao;

import java.util.List;

import com.damir.domain.Subject;

public interface SubjectDao {
	public Subject getById(Integer id) throws DaoException;
	public List<Subject> getAll() throws DaoException;
	public Subject persist(Subject subject) throws DaoException;
	public void deleteAll() throws DaoException;
	public void delete(Subject subject) throws DaoException;
}
