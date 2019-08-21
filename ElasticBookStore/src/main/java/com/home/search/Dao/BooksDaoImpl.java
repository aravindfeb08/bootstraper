package com.home.search.Dao;

import com.home.search.Model.Book;
import com.home.search.Model.BookImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository("BooksDao")
public class BooksDaoImpl implements BooksDao {

    private static final Log LOG = LogFactory.getLog(BooksDaoImpl.class);

    MongoOperations operations;

    private static final String BOOK = "book";

    public BooksDaoImpl(MongoOperations operations) {
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
        }
        return isRemoved;
    }

}