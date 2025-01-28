package de.wetie.springboot.service.impl;

import de.wetie.springboot.entity.Book;
import de.wetie.springboot.service.BookService;
import de.wetie.springboot.service.exception.BookNotFoundException;
import de.wetie.springboot.service.exception.IdNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

    @Override
    public void addBook(Book book) {
        System.out.println(book);
        //log.info(String.valueOf(book));
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {
        List<Book> bookList = getBookList()
                .stream()
                .filter(book -> book.getAuthor().equals(author))
                .toList();

        if (bookList.isEmpty()) {
            throw new BookNotFoundException("Book with this author not found");
        }

        return bookList;
    }

    @Override
    public List<Book> getBooksByCategory(String category) {
        List<Book> bookList = getBookList()
                .stream()
                .filter(book -> book.getCategory().equals(category))
                .collect(Collectors.toList());

        if (bookList.isEmpty()) {
            throw new BookNotFoundException("Book with this category not found");
        }

        return bookList;
    }

    @Override
    public Book getBookById(int bookId) {
        if (bookId <= 0) {
            throw new RuntimeException("other type of exception");
        }

        return getBookList()
                .stream()
                .filter(book -> book.getBookId() == bookId)
                .findAny()
                .orElseThrow(() -> new IdNotFoundException("Invalid id"));


        /*Optional<Book> bookOptional = getBookList()
                .stream()
                .filter(book -> book.getBookId() == bookId)
                .findAny();

        if (bookOptional.isPresent()) {
            return bookOptional.get();
        } else {
            throw  new IdNotFoundException("Invalid id");
        }*/
    }

    private List<Book> getBookList() {
        return Arrays.asList(
                new Book("Java", "Wetie", "Tech", 10),
                new Book("Spring", "Nepkiah", "Tech", 11),
                new Book("Angular", "Mensih", "Fiction", 12),
                new Book("C#", "Lontsi", "Fiction", 13),
                new Book("C++", "Nana", "Comic", 14),
                new Book("TypeScript", "Liwat", "Horror", 15)
        );
    }
}
