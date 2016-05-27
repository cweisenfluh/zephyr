package com.zephyr.load;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zephyr.model.PubMedEntry;

	// would probably implememnt this as a service
public class PubMedLoader implements ILoader {
	final static Charset ENCODING = StandardCharsets.UTF_8;
	
	public PubMedLoader() {
	}
	
	@Override
	public List<PubMedEntry> loadDataFromJson(String jsonFile) throws IOException {
		List<String> aList = null;
		
		try {
			aList = readTextFile(jsonFile);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		List<PubMedEntry> pmeList = new ArrayList<PubMedEntry>();
		
		final ObjectMapper mapper = new ObjectMapper();
		
		for (String entry : aList) {
			try {
			    PubMedEntry pme = mapper.readValue(entry, PubMedEntry.class);
			    pmeList.add(pme);
			} catch (Exception e) {
				e.printStackTrace();
				// log this sucker and move on
			}
		}
		
		return pmeList;
	}
	
	List<String> readTextFile(String aFileName) throws IOException {
		Path path = Paths.get(aFileName);
		return Files.readAllLines(path, ENCODING);
	}
}
