package com.home.search.Dao;

import com.home.search.Model.Book;
import com.home.search.Model.BookImpl;

import java.util.List;

public interface BookDao {

    boolean saveBook(Book book) throws Exception;

    boolean deleteBook(String bookId) throws Exception;

    boolean deleteBook(Book book) throws Exception;

    BookImpl findBook(String bookId) throws Exception;

    List<BookImpl> findAllBooks() throws Exception;

    boolean updateBook(Book book) throws Exception;

}
