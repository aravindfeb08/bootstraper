package com.home.search.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

//@Document(indexName = "my_index", type = "books")
@Document(collection = "books")
public class BookImpl implements Book {
	
	@Id
	@Field("primaryId")
	private String primaryId;

    private String bookId;
    private String title;
    private String author;
    private String category;
    private String price;

	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public BookImpl(String bookId, String title, String author, String category, String price) {
		super();
		this.bookId = bookId;
		this.title = title;
		this.author = author;
		this.category = category;
		this.price = price;
	}

}
