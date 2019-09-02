package com.home.search.Controller;

import com.home.search.Form.BookForm;
import com.home.search.Model.Book;
import com.home.search.Model.BookImpl;

import com.home.search.Service.BookService;
import com.home.search.Service.SearchIndexingListener;
import com.home.search.Service.UserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BooksController {

    private static final Log LOG = LogFactory.getLog(BooksController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private SearchIndexingListener searchIndexingListener;

    @RequestMapping("/hello")
    public String sayHello() {
        return "Hello Book Store";
    }

    @PostMapping("/addBook")
    public ResponseEntity<String> addBook(@ModelAttribute("bookForm") BookForm bookForm, HttpServletRequest request,
                                          HttpServletResponse response) {

        JSONObject json = new JSONObject();
        String adminId = (String) request.getAttribute("adminId");
        boolean isAdded = false;
        try {
            if (adminId != null) {
                Book book = new BookImpl();
                book.setBookId(bookForm.getBookId());
                book.setAuthor(bookForm.getAuthor());
                book.setCategory(bookForm.getCategory());
                book.setPrice(bookForm.getPrice());
                book.setTitle(bookForm.getTitle());
                isAdded = bookService.saveBook(book);
                if (isAdded) {
                    searchIndexingListener.updateBookIndexing((BookImpl) book);
                    json.put("success", "book saved successfully");
                } else {
                    json.put("failure", "book is not saved");
                }
            } else {
                json.put("failure", "Please login to continue");
            }
        } catch (Exception exp) {
            exp.printStackTrace();
            LOG.error("Exception in addBook method : "+exp);
        }
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @PostMapping("/editBook")
    public ResponseEntity<String> editBook(@ModelAttribute("bookForm") BookForm bookForm, HttpServletRequest request,
                                          HttpServletResponse response) {

        JSONObject json = new JSONObject();
        String adminId = (String) request.getAttribute("adminId");
        boolean isAdded = false;
        try {
            if (adminId != null) {
                Book book = new BookImpl();
                book.setBookId(bookForm.getBookId());
                book.setAuthor(bookForm.getAuthor());
                book.setCategory(bookForm.getCategory());
                book.setPrice(bookForm.getPrice());
                book.setTitle(bookForm.getTitle());
                isAdded = bookService.saveBook(book);
                if (isAdded) {
                    searchIndexingListener.updateBookIndexing((BookImpl) book);
                    json.put("success", "book saved successfully");
                } else {
                    json.put("failure", "book is not saved");
                }
            } else {
                json.put("failure", "Please login to continue");
            }
        } catch (Exception exp) {
            exp.printStackTrace();
            LOG.error("Exception in addBook method : "+exp);
        }
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @PostMapping("/removeBook")
    public ResponseEntity<String> removeBook(@ModelAttribute("bookForm") BookForm bookForm, HttpServletRequest request) {

        JSONObject json = new JSONObject();
        String adminId = (String) request.getAttribute("adminId");
        boolean isRemoved = false;
        try {
            if (adminId != null) {
                String bookId = bookForm.getBookId();
                isRemoved = bookService.deleteBook(bookId);
                if (isRemoved) {
                    searchIndexingListener.deleteBookIndexing(bookId);
                    json.put("success", "book removed successfully");
                } else {
                    json.put("failure", "book is not removed");
                }
            } else {
                json.put("failure", "Please login to continue");
            }
        } catch (Exception exp) {
            exp.printStackTrace();
            LOG.error("Exception in removeBook method : "+exp);
        }
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    public ResponseEntity<String> getAllBooks(@ModelAttribute("bookForm") BookForm bookForm, HttpServletRequest request) {
        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        String adminId = (String) request.getAttribute("adminId");
        List<BookImpl> bookList = new ArrayList<>();
        try{
            if (adminId != null) {
                bookList = bookService.findAllBooks();
                if (bookList.size() > 0) {
                    for (BookImpl book : bookList) {
                        jsonArray.add(book);
                    }
                    json.put("books",jsonArray);
                }else {
                    json.put("failure", "No books found");
                }

            } else {
                json.put("failure", "Please login to continue");
            }
        }catch (Exception exp) {
            exp.printStackTrace();
            LOG.error("Exception in getAllBooks method : "+exp);
        }finally {
            if (jsonArray.size() > 0){
                jsonArray.clear();
                jsonArray = null;
            }
            adminId = null;
            if (bookList != null) {
                bookList.clear();
                bookList = null;
            }
            request.removeAttribute("adminId");
        }

        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    public ResponseEntity<String> searchBooks(@ModelAttribute("bookForm") BookForm bookForm, HttpServletRequest request) {
        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        String adminId = (String) request.getAttribute("adminId");
        List<BookImpl> bookList = new ArrayList<>();
        try{
            if (adminId != null) {


            } else {
                json.put("failure", "Please login to continue");
            }
        }catch (Exception exp) {
            exp.printStackTrace();
            LOG.error("Exception in searchBooks method : "+exp);
        }finally {
            if (jsonArray.size() > 0){
                jsonArray.clear();
                jsonArray = null;
            }
            adminId = null;
            if (bookList != null) {
                bookList.clear();
                bookList = null;
            }
            request.removeAttribute("adminId");
        }
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);

    }


}
