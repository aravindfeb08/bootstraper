package com.home.search.Dao;

import com.home.search.Model.Book;
import com.home.search.Model.BookImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("BookDao")
public class BookDaoImpl implements BookDao {

    private static final Log LOG = LogFactory.getLog(BookDaoImpl.class);

    MongoOperations operations;

    private static final String BOOK = "book";

    public BookDaoImpl(MongoOperations operations) {
        this.operations = operations;
    }

    @Override
    public boolean saveBook(Book book) throws Exception {
        boolean isSaved = false;
        try {
            this.operations.save(book, BOOK);
            isSaved = true;
        } catch (Exception exp) {
            exp.printStackTrace();
            LOG.error("Exception in saveBook method : "+exp);
        }
        return isSaved;
    }

    @Override
    public boolean deleteBook(String bookId) throws Exception {
        boolean isRemoved = false;
        try {
            Query query = new Query(Criteria.where("bookId").is(bookId));
            this.operations.remove(query, BookImpl.class, BOOK);
            isRemoved = true;

        } catch (Exception exp) {
            exp.printStackTrace();
            LOG.error("Exception in deleteBook method : "+exp);
        }
        return isRemoved;
    }

    @Override
    public boolean deleteBook(Book book) throws Exception {
        boolean isRemoved = false;
        try {
            this.operations.remove(book);
            isRemoved = true;

        } catch (Exception exp) {
            exp.printStackTrace();
            LOG.error("Exception in deleteBook method : "+exp);
        }
        return isRemoved;
    }

    public BookImpl findBook(String bookId) throws Exception {
        BookImpl newBook = null;
        try{
            Query query = new Query(Criteria.where("bookId").is(bookId));
            newBook = this.operations.findOne(query, BookImpl.class, BOOK);
        }catch (Exception exp){
            exp.printStackTrace();
            LOG.error("Exception in findBook method : "+exp);
        }
        return newBook;
    }

    @Override
    public List<BookImpl> findAllBooks() throws Exception {
        List<BookImpl> allBooks = null;
        try{

        }catch (Exception exp){
            exp.printStackTrace();
            LOG.error("Exception in findAllBooks method : "+exp);
        }
        return null;
    }

    @Override
    public boolean updateBook(Book book) throws Exception {
        boolean isUpdated = false;
        try {
            this.operations.save(book, BOOK);
            isUpdated = true;
        } catch (Exception exp) {
            exp.printStackTrace();
            LOG.error("Exception in saveBook method : "+exp);
        }
        return isUpdated;
    }


}