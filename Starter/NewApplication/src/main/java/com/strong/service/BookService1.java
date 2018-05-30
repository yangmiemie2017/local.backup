package com.strong.service;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.strong.common.Data;
import com.strong.dataaccess.DLBook;
import com.strong.model.entity.Book;
import com.strong.model.entity.BookWithBookStore;

@Service(BookService.BEAN_NAME)
public class BookService1  implements BookService {
	private static Logger logger = LoggerFactory.getLogger(BookService1.class);
	
	public Data getBooks(String test) throws SQLException {
		Data data = new DLBook().getBooks(test);

		for (Map.Entry<String, List<LinkedHashMap<String, Object>>> entry : data.data.entrySet()) {
			logger.info(entry.getKey());
			for (Map<String, Object> map : entry.getValue()) {
				for (Map.Entry<String, Object> me : map.entrySet()) {
					logger.info(me.getKey() + "=" + me.getValue()+",");
				}
				logger.info("");
			}
			logger.info("");
		}
		return data;
	}
	
	
    public Optional<Book> getBookById(Long id){
    	return null;
    };

    public List<Book> getBooksByAuthor(String author){
    	return null;
    }

    public List<Book> getBooksByPage(Integer page, Integer perPage){
    	return null;
    }
    public List<String> getAllBookNames(){
    	return null;
    }
    
    public Optional<BookWithBookStore> getBookWithBookStoreById(Long id){
    	return null;
    }
    
    public Integer getTotalPage(Integer perPage) {
		return null;
	}
    
    public boolean saveBook(Book book){
    	return true;
    }
    public boolean modifyBookOnNameById(Book book){
    	return true;
    }
    public boolean deleteBookById(Long id){
    	return true;
    }
}
