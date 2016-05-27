package com.zephyr.load;

import java.io.IOException;
import java.util.List;

import com.zephyr.model.PubMedEntry;

public interface ILoader {
	public List<PubMedEntry> loadDataFromJson(String jsonFile) throws IOException;
}
