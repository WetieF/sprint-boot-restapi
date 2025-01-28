package de.wetie.springboot.service;

import de.wetie.springboot.entity.Book;
import de.wetie.springboot.service.exception.BookNotFoundException;
import de.wetie.springboot.service.exception.IdNotFoundException;

import java.util.List;

public interface BookService {

    void addBook(Book book);
    List<Book> getBooksByAuthor(String author) throws BookNotFoundException;
    List<Book> getBooksByCategory(String category) throws BookNotFoundException;
    Book getBookById(int bookId) throws IdNotFoundException;
}
