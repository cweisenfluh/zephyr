package com.zephyr.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Author {
	private final int SALT = 31;

	protected String firstName = null;
	protected String lastName = null;
	protected String middleName = null; 
	protected String prefix = null; 
	protected String postfix = null; 

	
		// only for the json constructor, sure there is a better way, but would have to noodle on it
	private String affiliation = null;

	protected String firstLast = null;
	
	public Author() {
		super();
	}

	public Author(String firstName, String lastName, String affiliation) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.affiliation = affiliation;
		this.buildFirstLast();
	}

	public Author(Author author) {
		super();
		this.firstName = author.firstName;
		this.lastName = author.lastName;
		this.firstLast = author.firstLast;
		this.affiliation = author.affiliation;
	}

	private void buildFirstLast() {
		StringBuffer sb = new StringBuffer();
		sb.append(cleanFirstName(firstName)).append(" ").append(cleanLastName(lastName));		
		firstLast = sb.toString();
	}
	
	private String cleanFirstName(String name) {
		String cleaned =  cleanName(name);
		
		if (cleaned.isEmpty()) {
			return cleaned;
		}
		
		String parts[] = cleaned.split(" ");
		String newFirst = null;

			// the right way to do this is with fuzzy matching...
		if (parts.length > 1) {
			if (parts[0].length() < 3) {
				this.prefix = parts[0];
				newFirst = parts[1];
			} else {
				newFirst = parts[0].toLowerCase();
				middleName = parts[1].toLowerCase().trim();
			}
		} else {
			newFirst = parts[0];
		}
		
		return newFirst.toLowerCase().trim();
	}

	private String cleanLastName(String name) {
		String cleaned =  cleanName(name);
		
		if (cleaned.isEmpty()) {
			return cleaned;
		}
		
		String parts[] = cleaned.split(" ");
		String newLast = null;

		if (parts.length > 1) {
			if (parts[1].length() < 3) {
				this.postfix = parts[1];
				newLast = parts[0];
			} else {
				middleName = parts[0].toLowerCase().trim();
				newLast = parts[1];
			}
		} else {
			newLast = parts[0];
		}
		
		return newLast.toLowerCase().trim();
	}
	
	private String cleanName(String name) {
		if (name == null) {
			return "";
		}
		
		name.replaceAll(".", "");
		name.replaceAll(",", "");
		return name;
	}


	@Override
	public int hashCode() {
		int hash = 1;
		hash = (SALT * hash) + ((firstName == null) ? 0 : firstName.hashCode());
		hash = (SALT * hash) + ((lastName == null) ? 0 : lastName.hashCode());

		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (object == null) {
			return false;
		}
		
//		if (getClass() != object.getClass()) {
//			return false;
//		}
		
		Author ae = (Author)object;
		
		if (this.getFirstLast() == null || ae.getFirstLast() == null) {
			return this.getFirstLast() == ae.getFirstLast();
		}
		
		return this.getFirstLast().equals(ae.getFirstLast());
		
//		if (firstName != null) {
//			if (ae.getFirstName() == null || firstName.equals(ae.getFirstName()) != true) {
//				return false;
//			}
//		 }
//		
//		if (lastName != null) {
//			if (ae.getLastName() == null || lastName.equals(ae.getLastName()) != true) {
//				return false;
//			}
//		 }
//
//		return true;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
		buildFirstLast();
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
		buildFirstLast();	// there is a better way, but not to be in this example...
	}

	public String getAffiliation() {
		return affiliation;
	}

	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}

	public String getFirstLast() {
		return firstLast;
	}

	public void setFirstLast(String firstLast) {
		this.firstLast = firstLast;
	}
}
