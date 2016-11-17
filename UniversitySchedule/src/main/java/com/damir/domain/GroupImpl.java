package com.damir.domain;

import java.util.LinkedList;
import java.util.List;

public class GroupImpl implements Group {
	private Integer id;
	private String name;
	private List<Person> students = new LinkedList<Person>();

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public List<Person> getStudents() {
		return students;
	}

	@Override
	public void setStudents(List<Person> students) {
		this.students = students;
	}

	public GroupImpl() {
	}

	public GroupImpl(String name) {
		this.name = name;
	}

	public GroupImpl(String name, List<Person> students) {
		this.name = name;
		this.students = students;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((students == null) ? 0 : students.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GroupImpl other = (GroupImpl) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (students == null) {
			if (other.students != null)
				return false;
		} else if (!students.equals(other.students))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return name;
	}

}
