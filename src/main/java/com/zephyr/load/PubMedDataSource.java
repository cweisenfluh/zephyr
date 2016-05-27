package com.zephyr.load;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.zephyr.model.Author;
import com.zephyr.model.AuthorEntity;
import com.zephyr.model.PubMedEntry;

public class PubMedDataSource implements IDataSource<PubMedEntry> {
	private List<PubMedEntry> pmeList = null;
	private Map<String, AuthorEntity> authorMap = null;
		
	public PubMedDataSource(String pubMedJsonfile) throws IOException {
		pmeList = loadDataFromJson(pubMedJsonfile);
	}

	@Override
	public List<PubMedEntry> getEntries() {
		return pmeList;
	}

	@Override
	public Map<String, AuthorEntity> getAuthors() {
		return authorMap;		
	}
	
	@Override
	public List<PubMedEntry> loadDataFromJson(String jsonFile) throws IOException {
		pmeList = new PubMedLoader().loadDataFromJson(jsonFile);
		
		authorMap = new HashMap<String, AuthorEntity>();
		
		for (PubMedEntry pme : pmeList) {
			for (Author author : pme.getAuthorList()) {
				
				String firstLast  = author.getFirstLast();
				AuthorEntity authorEntity = null;
				
				if (authorMap.containsKey(firstLast)) {
					authorEntity = authorMap.get(firstLast);
					authorEntity.addAffilition(author.getAffiliation());
				} else {
					authorEntity = new AuthorEntity(author);
				}
				
				authorMap.put(firstLast, authorEntity);
			}
		}
		
		return pmeList;		
	}
}
