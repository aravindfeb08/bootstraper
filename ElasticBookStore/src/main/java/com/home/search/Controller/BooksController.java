package com.home.search.Controller;

import com.home.search.Dao.BooksRepository;
import com.home.search.Form.BooksForm;
import com.home.search.Model.Book;
import com.home.search.Model.BookImpl;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BooksController {

    @Autowired
    private BooksRepository booksRepository;

    @RequestMapping("/hello")
    public String sayHello() {
        return "Hello Book Store";
    }

    @RequestMapping("/all")
    public List<Book> getAllBooks() {

        //JSONObject json = new JSONObject();
        List<Book> allBooks = new ArrayList<>();
        allBooks.add(new BookImpl("101","origin of species","charles darwin","species","200$"));
        allBooks.add(new BookImpl("102","the alchemist","paulo cehlo","motivation","150$"));
        allBooks.add(new BookImpl("103","Bible","jesus","fiction","180$"));
        booksRepository.findAll().forEach(allBooks::add);
        //json.put("allBooks",allBooks);
        //return new ResponseEntity<>(json.toString(), HttpStatus.OK);
        return allBooks;
    }

    public ResponseEntity<String> saveBook(@ModelAttribute("booksForm")BooksForm booksForm) {

       /* Books book = new Books();
        book.setTitle(booksForm.getTitle());
        book.setAuthor(booksForm.getAuthor());
        book.setCategory(booksForm.getCategory());
        book.setPrice(booksForm.getPrice());
        booksRepository.save(book);*/

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

}
