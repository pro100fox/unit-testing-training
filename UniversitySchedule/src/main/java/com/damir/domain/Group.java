package com.damir.domain;

import java.util.List;

public interface Group {
	public Integer getId();
	public void setId(Integer id);
	public String getName();
	public void setName(String name);
	public List<Person> getStudents();
	public void setStudents(List<Person> students);
}
