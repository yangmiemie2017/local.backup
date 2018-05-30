package com.strong.service;

import com.strong.model.entity.Book;
import com.strong.model.entity.BookWithBookStore;

import java.util.List;
import java.util.Optional;

/**
 * @author cy
 */
public interface BookService {
    public static final String BEAN_NAME = "bookService";
    
    Optional<Book> getBookById(Long id);

    List<Book> getBooksByAuthor(String author);

    List<Book> getBooksByPage(Integer page, Integer perPage);

    List<String> getAllBookNames();

    Optional<BookWithBookStore> getBookWithBookStoreById(Long id);

    Integer getTotalPage(Integer perPage);

    boolean saveBook(Book book);

    boolean modifyBookOnNameById(Book book);

    boolean deleteBookById(Long id);

}
