package com.example.lavrastore.dao;

import org.springframework.dao.DataAccessException;

public interface SequenceDao {
	public int getNextId(String name) throws DataAccessException;
}
