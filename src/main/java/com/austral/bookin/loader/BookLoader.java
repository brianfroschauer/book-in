package com.austral.bookin.loader;

import com.austral.bookin.entity.Author;
import com.austral.bookin.entity.Book;
import com.austral.bookin.service.author.AuthorService;
import com.austral.bookin.service.book.BookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Profile("!test")
@Component
public class BookLoader implements CommandLineRunner, Ordered {

    private final BookService bookService;
    private final AuthorService authorService;

    public BookLoader(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @Override
    public void run(String... args) throws IOException {

        final List<Author> authors1 = Collections.singletonList(authorService.find(10L));
        final List<Author> authors2 = Collections.singletonList(authorService.find(11L));
        final List<Author> authors3 = Collections.singletonList(authorService.find(12L));
        final List<Author> authors4 = Collections.singletonList(authorService.find(13L));
        final List<Author> authors5 = Collections.singletonList(authorService.find(14L));
        final List<Author> authors6 = Collections.singletonList(authorService.find(15L));
        final List<Author> authors7 = Collections.singletonList(authorService.find(16L));
        final List<Author> authors8 = Collections.singletonList(authorService.find(17L));
        final List<Author> authors9 = Collections.singletonList(authorService.find(18L));

        final Book book1 = new Book(
                "Adventures of Huckleberry Finn",
                "Aventura",
                "en",
                new Date(-16, Calendar.DECEMBER, 1),
                authors1);

        final Book book2 = new Book(
                "The Gilded Age: A Tale of Today",
                "Educativos",
                "en",
                new Date(-27, Calendar.JANUARY, 1),
                authors2);

        final Book book3 = new Book(
                "The Mystery of Edwin Drood",
                "Terror",
                "es",
                new Date(-30, Calendar.DECEMBER, 1),
                authors2);

        final Book book4 = new Book(
                "Hamlet, Macbeth and Othello",
                "Aventura",
                "es",
                new Date(115, Calendar.DECEMBER, 1),
                authors3);

        final Book book5 = new Book(
                "Fantastic Beasts and Where to Find Them",
                "Fantasía",
                "en",
                new Date(101, Calendar.DECEMBER, 1),
                authors4);

        final Book book6 = new Book(
                "Harry Potter and the Philosopher's Stone",
                "Fantasía",
                "en",
                new Date(97, Calendar.DECEMBER, 26),
                authors4);

        final Book book7 = new Book(
                "The Kane Chronicles: The Red Pyramid",
                "Fantasía",
                "en",
                new Date(110, Calendar.MAY, 10),
                authors5);

        final Book book8 = new Book(
                "The Kane Chronicles: The Throne of Fire",
                "Fantasía",
                "en",
                new Date(111, Calendar.MAY, 3),
                authors5);

        final Book book9 = new Book(
                "The Kane Chronicles: The Serpent's Shadow",
                "Fantasía",
                "en",
                new Date(112, Calendar.MAY, 1),
                authors5);

        final Book book10 = new Book(
                "Orgullo y prejuicio",
                "Novela",
                "es",
                new Date(-87, Calendar.SEPTEMBER, 13),
                authors6);

        final Book book11 = new Book(
                "Persuasión",
                "Novela",
                "es",
                new Date(-70, Calendar.NOVEMBER, 23),
                authors6);

        final Book book12 = new Book(
                "Sentido y sensibilidad",
                "Novela",
                "es",
                new Date(-76, Calendar.MAY, 13),
                authors6);

        final Book book13 = new Book(
                "Delirium",
                "Ficción",
                "en",
                new Date(111, Calendar.FEBRUARY, 3),
                authors7);

        final Book book14 = new Book(
                "Pandemonium",
                "Ficción",
                "en",
                new Date(112, Calendar.FEBRUARY, 28),
                authors7);

        final Book book15 = new Book(
                "Requiem",
                "Ficción",
                "en",
                new Date(113, Calendar.MARCH, 5),
                authors7);

        final Book book16 = new Book(
                "El caballero de la armadura oxidada",
                "Auto-realización",
                "es",
                new Date(97, Calendar.DECEMBER, 26),
                authors8);

        final Book book17 = new Book(
                "This rough magic",
                "Terror",
                "en",
                new Date(64, Calendar.DECEMBER, 26),
                authors9);

        final Book book18 = new Book(
                "Una vacante imprevista",
                "Trágica",
                "es",
                new Date(112, Calendar.DECEMBER, 26),
                authors4);

        final Book book19 = new Book(
                "El ickabog",
                "Fantasía",
                "es",
                new Date(120, Calendar.MARCH, 26),
                authors4);

        final Book book20 = new Book(
                "Troubled blood",
                "Misterio",
                "en",
                new Date(120, Calendar.JULY, 16),
                authors4);

        final Book book21 = new Book(
                "The silkworm",
                "Policiales",
                "en",
                new Date(114, Calendar.MARCH, 2),
                authors4);

        final Book book22 = new Book(
                "Blanco letal",
                "Ficción",
                "es",
                new Date(118, Calendar.DECEMBER, 26),
                authors4);

        final Book book23 = new Book(
                "Romeo and Juliet",
                "Romance",
                "en",
                new Date(-303, Calendar.JULY, 18),
                authors3);

        final Book book24 = new Book(
                "El sueño de una noche de verano",
                "Comedia",
                "es",
                new Date(-295, Calendar.NOVEMBER, 30),
                authors3);

        final Book book25 = new Book(
                "La tempestad",
                "Drama",
                "es",
                new Date(-289, Calendar.JANUARY, 3),
                authors3);

        final Book book26 = new Book(
                "Las aventuras de Tom Sawyer",
                "Novela",
                "es",
                new Date(-24, Calendar.JULY, 24),
                authors1);

        final Book book27 = new Book(
                "El forastero misterioso",
                "Ficción",
                "es",
                new Date(16, Calendar.MARCH, 2),
                authors1);

        final Book book28 = new Book(
                "Oliver Twist",
                "Novela",
                "en",
                new Date(-62, Calendar.SEPTEMBER, 6),
                authors2);

        final Book book29 = new Book(
                "Cuento de navidad",
                "Fantasía",
                "es",
                new Date(-57, Calendar.MARCH, 13),
                authors2);

        final Book book30 = new Book(
                "David Copperfield",
                "Novela",
                "es",
                new Date(-50, Calendar.FEBRUARY, 19),
                authors2);

        bookService.save(book1, new MockMultipartFile("huckleberry.jpg", new FileInputStream(new File("src/main/resources/assets/books/huckleberry.jpg"))));
        bookService.save(book2, new MockMultipartFile("thefildedage.jpg", new FileInputStream(new File("src/main/resources/assets/books/thefildedage.jpg"))));
        bookService.save(book3, new MockMultipartFile("mysteryofedwin.jpg", new FileInputStream(new File("src/main/resources/assets/books/mysteryofedwin.jpg"))));
        bookService.save(book4, new MockMultipartFile("hamletotellomacbeth.jpg", new FileInputStream(new File("src/main/resources/assets/books/hamletothellomacbeth.jpg"))));
        bookService.save(book5, new MockMultipartFile("fantasticbeasts.jpg", new FileInputStream(new File("src/main/resources/assets/books/fantasticbeasts.jpg"))));
        bookService.save(book6, new MockMultipartFile("philosopher.jpg", new FileInputStream(new File("src/main/resources/assets/books/philosopher.jpg"))));
        bookService.save(book7, new MockMultipartFile("redpyramid.jpg", new FileInputStream(new File("src/main/resources/assets/books/redpyramid.jpg"))));
        bookService.save(book8, new MockMultipartFile("throneoffire.jpg", new FileInputStream(new File("src/main/resources/assets/books/throneoffire.jpg"))));
        bookService.save(book9, new MockMultipartFile("serpentshadow.jpg", new FileInputStream(new File("src/main/resources/assets/books/serpentshadow.jpg"))));
        bookService.save(book10, new MockMultipartFile("orgulloyprejuicio.jpg", new FileInputStream(new File("src/main/resources/assets/books/orgulloyprejuicio.jpg"))));
        bookService.save(book11, new MockMultipartFile("persuasion.png", new FileInputStream(new File("src/main/resources/assets/books/persuasion.png"))));
        bookService.save(book12, new MockMultipartFile("sentidoysensibilidad.jpg", new FileInputStream(new File("src/main/resources/assets/books/sentidoysensibilidad.jpg"))));
        bookService.save(book13, new MockMultipartFile("delirium.jpg", new FileInputStream(new File("src/main/resources/assets/books/delirium.jpg"))));
        bookService.save(book14, new MockMultipartFile("pandemonium.jpg", new FileInputStream(new File("src/main/resources/assets/books/pandemonium.jpg"))));
        bookService.save(book15, new MockMultipartFile("requiem.png", new FileInputStream(new File("src/main/resources/assets/books/requiem.png"))));
        bookService.save(book16, new MockMultipartFile("caballero.jpg", new FileInputStream(new File("src/main/resources/assets/books/caballero.jpg"))));
        bookService.save(book17, new MockMultipartFile("roughmagic.jpg", new FileInputStream(new File("src/main/resources/assets/books/roughmagic.jpg"))));
        bookService.save(book18, new MockMultipartFile("vacante.jpg", new FileInputStream(new File("src/main/resources/assets/books/vacante.jpg"))));
        bookService.save(book19, new MockMultipartFile("ickabog.jpg", new FileInputStream(new File("src/main/resources/assets/books/ickabog.jpg"))));
        bookService.save(book20, new MockMultipartFile("troubledblood.jpg", new FileInputStream(new File("src/main/resources/assets/books/troubledblood.jpg"))));
        bookService.save(book21, new MockMultipartFile("silkworm.jpg", new FileInputStream(new File("src/main/resources/assets/books/silkworm.jpg"))));
        bookService.save(book22, new MockMultipartFile("blancoletal.png", new FileInputStream(new File("src/main/resources/assets/books/blancoletal.png"))));
        bookService.save(book23, new MockMultipartFile("romeoandjuliet.jpg", new FileInputStream(new File("src/main/resources/assets/books/romeoandjuliet.jpg"))));
        bookService.save(book24, new MockMultipartFile("sueño.jpg", new FileInputStream(new File("src/main/resources/assets/books/sueño.jpg"))));
        bookService.save(book25, new MockMultipartFile("tempestad.jpg", new FileInputStream(new File("src/main/resources/assets/books/tempestad.jpg"))));
        bookService.save(book26, new MockMultipartFile("tomsawyer.jpg", new FileInputStream(new File("src/main/resources/assets/books/tomsawyer.jpg"))));
        bookService.save(book27, new MockMultipartFile("forastero.jpg", new FileInputStream(new File("src/main/resources/assets/books/forastero.jpg"))));
        bookService.save(book28, new MockMultipartFile("olivertwist.jpg", new FileInputStream(new File("src/main/resources/assets/books/olivertwist.jpg"))));
        bookService.save(book29, new MockMultipartFile("navidad.jpg", new FileInputStream(new File("src/main/resources/assets/books/navidad.jpg"))));
        bookService.save(book30, new MockMultipartFile("copperfield.jpg", new FileInputStream(new File("src/main/resources/assets/books/copperfield.jpg"))));
    }

    @Override
    public int getOrder() {
        return 3;
    }
}
