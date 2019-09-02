package com.home.search.Service;

import com.home.search.Dao.BookDao;
import com.home.search.Model.Book;
import com.home.search.Model.BookImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("BookService")
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;

    @Override
    public boolean saveBook(Book book) throws Exception {
        return bookDao.saveBook(book);
    }

    @Override
    public boolean deleteBook(String bookId) throws Exception {
        return bookDao.deleteBook(bookId);
    }

    @Override
    public boolean deleteBook(Book book) throws Exception {
        return bookDao.deleteBook(book);
    }

    @Override
    public BookImpl findBook(String bookId) throws Exception {
        return bookDao.findBook(bookId);
    }

    @Override
    public List<BookImpl> findAllBooks() throws Exception {
        return bookDao.findAllBooks();
    }

}
