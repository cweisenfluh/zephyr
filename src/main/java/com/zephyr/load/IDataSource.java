package com.zephyr.load;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.zephyr.model.AuthorEntity;
import com.zephyr.model.PubMedEntry;

public interface IDataSource<T> {
	public List<T> getEntries();
	public List<PubMedEntry> loadDataFromJson(String jsonFile) throws IOException;
	Map<String, AuthorEntity> getAuthors();
}
