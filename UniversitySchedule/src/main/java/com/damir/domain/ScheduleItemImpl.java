package com.damir.domain;

import java.util.Date;

public class ScheduleItemImpl implements ScheduleItem {
	private Integer id;
	private Date dateTime;
	private Person lecturer;
	private Group group;
	private Audience audience;
	private Subject subject;

	public ScheduleItemImpl(Date dateTime, Person lecturer, Group group, Audience audience, Subject subject) {
		super();
		this.dateTime = dateTime;
		this.lecturer = lecturer;
		this.group = group;
		this.audience = audience;
		this.subject = subject;
	}

	public ScheduleItemImpl() {
	}

	@Override
	public Date getDateTime() {
		return dateTime;
	}

	@Override
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	@Override
	public Person getLecturer() {
		return lecturer;
	}

	@Override
	public void setLecturer(Person lecturer) {
		this.lecturer = lecturer;
	}

	@Override
	public Group getGroup() {
		return group;
	}

	@Override
	public void setGroup(Group group) {
		this.group = group;
	}

	@Override
	public Audience getAudience() {
		return audience;
	}

	@Override
	public void setAudience(Audience audience) {
		this.audience = audience;
	}

	@Override
	public Subject getSubject() {
		return subject;
	}

	@Override
	public void setSubject(Subject subject) {
		this.subject = subject;
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
		result = prime * result + ((audience == null) ? 0 : audience.hashCode());
		result = prime * result + ((dateTime == null) ? 0 : dateTime.hashCode());
		result = prime * result + ((group == null) ? 0 : group.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lecturer == null) ? 0 : lecturer.hashCode());
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
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
		ScheduleItemImpl other = (ScheduleItemImpl) obj;
		if (audience == null) {
			if (other.audience != null)
				return false;
		} else if (!audience.equals(other.audience))
			return false;
		if (dateTime == null) {
			if (other.dateTime != null)
				return false;
		} else if (!dateTime.equals(other.dateTime))
			return false;
		if (group == null) {
			if (other.group != null)
				return false;
		} else if (!group.equals(other.group))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lecturer == null) {
			if (other.lecturer != null)
				return false;
		} else if (!lecturer.equals(other.lecturer))
			return false;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Время: " + dateTime + ", лектор: " + lecturer + ", группа: " + group
				+ ", аудитория: " + audience + ", предмет: " + subject + "]";
	}
	

}
