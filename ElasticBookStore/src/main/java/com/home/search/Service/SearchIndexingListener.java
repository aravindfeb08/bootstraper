package com.home.search.Service;

import com.home.search.Model.BookImpl;

public interface SearchIndexingListener {

    public void bookIndexing() throws Exception;

    public void updateBookIndexing(BookImpl book) throws InterruptedException;

    public void deleteBookIndexing(String bookId) throws InterruptedException;

}
