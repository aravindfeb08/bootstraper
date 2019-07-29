package com.home.search.Dao;

import com.home.search.Model.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksRepository extends ElasticsearchRepository<Book,String> {

}
