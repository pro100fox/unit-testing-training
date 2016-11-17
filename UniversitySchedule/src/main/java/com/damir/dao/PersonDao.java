package com.damir.dao;

import java.util.List;

import com.damir.domain.Person;

public interface PersonDao {
	public Person getById(Integer id) throws DaoException;
	public List<Person> getAll() throws DaoException;
	public Person persist(Person person) throws DaoException;
	public void deleteAll() throws DaoException;
	public void delete(Person person) throws DaoException;
}
