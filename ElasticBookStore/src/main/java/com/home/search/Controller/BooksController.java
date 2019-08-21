package com.home.search.Controller;

import com.home.search.Form.BooksForm;
import com.home.search.Model.Book;
import com.home.search.Model.BookImpl;

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

    @RequestMapping("/hello")
    public String sayHello() {
        return "Hello Book Store";
    }

    @PostMapping("/addbook")
    public ResponseEntity<String> addbooks(@ModelAttribute("bookForm") BooksForm booksForm, HttpServletRequest request, HttpServletResponse response) {

        JSONObject json = new JSONObject();
        String role = request.getHeader("role");
        try{
            if (("ADMIN").equals(role)){
                Book book = new BookImpl();
                book.setBookId(booksForm.getBookId());
                book.setAuthor(booksForm.getAuthor());
                book.setCategory(booksForm.getCategory());
                book.setPrice(booksForm.getPrice());
                book.setTitle(booksForm.getTitle());

            }else {
                json.put("failure","Unauthroized to access this url");
            }
        }catch (Exception exp){
            exp.printStackTrace();
        }
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }


}
