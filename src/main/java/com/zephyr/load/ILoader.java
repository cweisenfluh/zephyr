package com.zephyr.load;

import java.io.IOException;
import java.util.List;

public interface ILoader<T> {
	public List<T> loadDataFromJson(String jsonFile) throws IOException;
}
