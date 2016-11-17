package com.damir.dao;

import java.util.List;

import com.damir.domain.Group;
import com.damir.domain.Person;

public interface GroupDao {
	public Group getById(Integer id) throws DaoException;
	public List<Group> getAll() throws DaoException;
	public Group persist(Group group) throws DaoException;
	public List<Person> getStudents() throws DaoException;
	public void deleteAll() throws DaoException;
	public void delete(Group group) throws DaoException;
}
