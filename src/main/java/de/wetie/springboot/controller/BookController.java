package de.wetie.springboot.controller;

import de.wetie.springboot.entity.Book;
import de.wetie.springboot.service.BookService;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book-restapi")
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        String result = "Hello Mensih";
        HttpHeaders header = new HttpHeaders();
        header.add("desc", "Online Book Application");
        return new ResponseEntity<>(result, header, HttpStatus.OK);
    }

    @PostMapping("/admin/books")
    //@PreAuthorize("hasAuthority('ADMIN')")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> addBook(@RequestBody Book book) {
        bookService.addBook(book);
        HttpHeaders header = new HttpHeaders();
        header.add("desc", "Adding one Book");
        return ResponseEntity.ok().headers(header).build();
    }

    @GetMapping("/admin/books/{id}")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Book> getBookById(@PathVariable("id") int bookId) {
        Book book = bookService.getBookById(bookId);
        HttpHeaders header = new HttpHeaders();
        header.add("desc", "Getting Book by id");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(book);
    }

    @GetMapping("/user/books/books-by-author/{auth}")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Book>> getBooksByAuthor(@PathVariable("auth") String author) {
        List<Book> bookList = bookService.getBooksByAuthor(author);
        return ResponseEntity.ok().body(bookList);
    }

    @GetMapping("/user/books/books-by-category")
    //@PreAuthorize("hasAuthority('ADMIN')")
    //@RolesAllowed("ADMIN")
    public ResponseEntity<List<Book>> getBooksByCategory(@RequestParam("category") String category) {
        HttpHeaders header = new HttpHeaders();
        header.add("desc", "List of Books by category");
        header.add("type", "book Object");
        List<Book> bookList = bookService.getBooksByCategory(category);
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(bookList);
    }
}
