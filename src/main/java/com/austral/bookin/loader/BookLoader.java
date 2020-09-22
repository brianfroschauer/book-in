package com.austral.bookin.loader;

import com.austral.bookin.entity.Book;
import com.austral.bookin.service.book.BookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

@Profile("!test")
@Component
public class BookLoader implements CommandLineRunner, Ordered {

    private BookService bookService;

    public BookLoader(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws IOException {

        final Book book1 = new Book(
                "Adventures of Huckleberry Finn",
                "Aventura",
                "Inglés",
                new Date(-16, Calendar.DECEMBER, 1));

        final Book book2 = new Book(
                "The Gilded Age: A Tale of Today",
                "Educativos",
                "Inglés",
                new Date(-27, Calendar.JANUARY, 1));

        final Book book3 = new Book(
                "The Mystery of Edwin Drood",
                "Terror",
                "Inglés",
                new Date(-30, Calendar.DECEMBER, 1));

        final Book book4 = new Book(
                "Hamlet, Macbeth and Othello",
                "Aventura",
                "Inglés",
                new Date(115, Calendar.DECEMBER, 1));

        final Book book5 = new Book(
                "Fantastic Beasts and Where to Find Them",
                "Fantasía",
                "Inglés",
                new Date(101, Calendar.DECEMBER, 1));

        final Book book6 = new Book(
                "Harry Potter and the Philosopher's Stone",
                "Fantasía",
                "Inglés",
                new Date(97, Calendar.DECEMBER, 26));

        bookService.save(book1, new MockMultipartFile("huckleberry.jpg", new FileInputStream(new File("src/main/java/com/austral/bookin/util/books/huckleberry.jpg"))));
        bookService.save(book2, new MockMultipartFile("thefildedage.jpg", new FileInputStream(new File("src/main/java/com/austral/bookin/util/books/thefildedage.jpg"))));
        bookService.save(book3, new MockMultipartFile("mysteryofedwin.jpg", new FileInputStream(new File("src/main/java/com/austral/bookin/util/books/mysteryofedwin.jpg"))));
        bookService.save(book4, new MockMultipartFile("hamletotellomacbeth.jpg", new FileInputStream(new File("src/main/java/com/austral/bookin/util/books/hamletothellomacbeth.jpg"))));
        bookService.save(book5, new MockMultipartFile("fantasticbeasts.jpg", new FileInputStream(new File("src/main/java/com/austral/bookin/util/books/fantasticbeasts.jpg"))));
        bookService.save(book6, new MockMultipartFile("philosopher.jpg", new FileInputStream(new File("src/main/java/com/austral/bookin/util/books/philosopher.jpg"))));
    }

    @Override
    public int getOrder() {
        return 3;
    }
}