package com.home.search.Dao;

import com.home.search.Model.Book;

public interface BooksDao {

    boolean saveBook(Book book) throws Exception;

    boolean deleteBook(String bookId) throws Exception;

}
