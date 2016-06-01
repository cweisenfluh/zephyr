package com.zephyr.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorEntity extends Author {
	private final int SALT = 31;

	private Set<String> affiliationSet= null;
	private Set<String> titleSet = null;
	private int pubs;
	
	public AuthorEntity() {
		super();
		affiliationSet = new HashSet<String>();
		titleSet = new HashSet<String>();
	}

	public AuthorEntity(Author author) {
		super(author);
		affiliationSet = new HashSet<String>();
		titleSet = new HashSet<String>();
		affiliationSet.add(author.getAffiliation());
	}

	public AuthorEntity(String firstName, String lastName, String titles, String affiliation, int pubs) {
		super(firstName, lastName, affiliation);
		this.affiliationSet = new HashSet<String>();;
		this.titleSet = new HashSet<String>();
		this.affiliationSet.add(affiliation);
		this.pubs = pubs;
		addTitles(titles);
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void addAffilition(String affiliation) {
		this.affiliationSet.add(affiliation);
	}
	
	public void addTitles(String titles) {
		String parts[] = titles.split(",");
		for (String part : parts) {
			titleSet.add(part);
		}
	}

	public Set<String> getAffiliationSet() {
		return affiliationSet;
	}

	public void setAffiliationSet(Set<String> affiliationSet) {
		this.affiliationSet = affiliationSet;
	}

	public Set<String> getTitles() {
		return titleSet;
	}

	public void setTitles(Set<String> titleSet) {
		this.titleSet = titleSet;
	}

	public int getPubs() {
		return pubs;
	}

	public void setPubs(int pubs) {
		this.pubs = pubs;
	}
}
