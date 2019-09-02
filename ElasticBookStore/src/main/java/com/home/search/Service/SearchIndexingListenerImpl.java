package com.home.search.Service;

import com.home.search.Model.BookImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchIndexingListenerImpl implements SearchIndexingListener {

    private static final Log LOG = LogFactory.getLog(SearchIndexingListenerImpl.class);

    @Autowired
    private BookService bookService;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    public void bookIndexing() throws InterruptedException {

        LOG.info("Book details indexing started");

        try {
            elasticsearchTemplate.deleteIndex(BookImpl.class);
            elasticsearchTemplate.createIndex(BookImpl.class);
            elasticsearchTemplate.putMapping(BookImpl.class);
            elasticsearchTemplate.refresh(BookImpl.class);

            List<IndexQuery> queries = new ArrayList<>();
            List<BookImpl> bookList = bookService.findAllBooks();

            if (bookList.size() > 0) {
                LOG.info("Book details are being indexed");
                for (BookImpl book : bookList) {
                    IndexQuery query = new IndexQuery();
                    query.setId(book.getBookId());
                    query.setObject(book);
                    queries.add(query);
                }
                elasticsearchTemplate.putMapping(BookImpl.class);
                if (queries.size() > 0) {
                    elasticsearchTemplate.bulkIndex(queries);
                    LOG.info("Book details are indexed successfully");
                }
                elasticsearchTemplate.refresh(BookImpl.class);
                LOG.info("Book details indexing end");
            }

        }catch (Exception exp) {
            exp.printStackTrace();
            LOG.error("Exception in bookIndexing method : "+exp);
        }

    }

    public void updateBookIndexing(BookImpl book) throws InterruptedException {

        try {
            elasticsearchTemplate.delete("book","books",book.getBookId());
            elasticsearchTemplate.refresh(BookImpl.class);

            IndexQuery query = new IndexQuery();
            query.setId(book.getBookId());
            query.setObject(book);
            elasticsearchTemplate.putMapping(BookImpl.class);
            elasticsearchTemplate.index(query);
            elasticsearchTemplate.refresh(BookImpl.class);


        }catch (Exception exp) {
            exp.printStackTrace();
            LOG.error("Exception in updateBookIndexing method : "+exp);
        }
    }

    public void deleteBookIndexing(String bookId) throws InterruptedException {

        try {
            elasticsearchTemplate.delete("book","books",bookId);
            elasticsearchTemplate.refresh(BookImpl.class);

        }catch (Exception exp) {
            exp.printStackTrace();
            LOG.error("Exception in updateBookIndexing method : "+exp);
        }
    }
}
