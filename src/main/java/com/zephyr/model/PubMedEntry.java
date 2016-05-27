package com.zephyr.model;

import java.util.List;

public class PubMedEntry {
	private final int SALT = 31;

	private String title;
	private List<Author> authorList;

	@Override
	public int hashCode() {
		int hash = 1;
		hash = (SALT * hash) + ((title == null) ? 0 : title.hashCode());
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
		
		if (getClass() != object.getClass()) {
			return false;
		}
		
		PubMedEntry pme = (PubMedEntry)object;
		
		if (title != null) {
			if (pme.getTitle() == null || title.equals(pme.getTitle()) != true) {
				return false;
			}
		}
		
		return true;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Author> getAuthorList() {
		return authorList;
	}

	public void setAuthorList(List<Author> authorList) {
		this.authorList = authorList;
	}
}
