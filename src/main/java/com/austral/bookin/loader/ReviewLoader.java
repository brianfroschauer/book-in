package com.austral.bookin.loader;

import com.austral.bookin.entity.Book;
import com.austral.bookin.entity.Review;
import com.austral.bookin.entity.User;
import com.austral.bookin.service.book.BookService;
import com.austral.bookin.service.review.ReviewService;
import com.austral.bookin.service.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Profile("!test")
@Component
public class ReviewLoader implements CommandLineRunner, Ordered {

    private final ReviewService reviewService;
    private final UserService userService;
    private final BookService bookService;

    public ReviewLoader(ReviewService reviewService, UserService userService, BookService bookService) {
        this.reviewService = reviewService;
        this.userService = userService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) {

        final User user1 = userService.find(2L);
        final User user2 = userService.find(3L);
        final User user3 = userService.find(4L);
        final User user4 = userService.find(5L);
        final User user5 = userService.find(6L);
        final User user6 = userService.find(7L);
        final User user7 = userService.find(8L);
        final User user8 = userService.find(9L);

        final Book book1 = bookService.find(14L);
        final Book book2 = bookService.find(15L);
        final Book book3 = bookService.find(16L);
        final Book book4 = bookService.find(17L);
        final Book book5 = bookService.find(18L);
        final Book book6 = bookService.find(19L);

        Review review11 = new Review(5, "Excelente libro!", user1, book1);
        Review review21 = new Review(2, "Podría haber sido mejor", user3, book1);
        Review review31 = new Review(4, "Lo recomiendo", user7, book1);
        Review review41 = new Review(1, "Pésimo, no lo lean", user8, book1);

        Review review12 = new Review(4, "Muy entretenido y fácil de leer!", user2, book2);
        Review review22 = new Review(3, "Divertido pero muy largo :(", user5, book2);
        Review review32 = new Review(5, "Lo leería un millón de veces más", user8, book2);

        Review review13 = new Review(5, "Da mucho miedo! Me encantó", user1, book3);
        Review review23 = new Review(3, "No es mi estilo de lectura", user4, book3);
        Review review33 = new Review(2, "No me gusta como escribe el autor, podría ser mejor", user6, book3);
        Review review43 = new Review(3, "Buena trama pero mal desarrollo", user7, book3);
        Review review53 = new Review(1, "No lo recomiendo", user8, book3);

        Review review14 = new Review(5, "Un clásico excelente!", user2, book4);
        Review review24 = new Review(4, "Difícil de leer pero vale la pena", user4, book4);
        Review review34 = new Review(3, "Lectura para gente con mucho vocabulario! Igual entretenido", user5, book4);

        Review review15 = new Review(5, "Muy divertido, te atrapa cada palabra :)", user3, book5);
        Review review25 = new Review(4, "Muy bueno para leer antes de dormir", user4, book5);
        Review review35 = new Review(3, "Muy atrapante pero con un final muy predecible :(", user6, book5);
        Review review45 = new Review(5, "Excelente desarrollo y personajes, ME ENCANTÓ", user2, book5);

        Review review16 = new Review(3, "Muy buen libro pero demasiado fantasioso para mi gusto", user4, book6);
        Review review26 = new Review(5, "Excelente libro, JK es una genia!", user7, book6);
        Review review36 = new Review(5, "Muy divertido y atrapante, lo leería mil veces más", user8, book6);

        reviewService.save(review11);
        reviewService.save(review21);
        reviewService.save(review31);
        reviewService.save(review41);
        reviewService.save(review12);
        reviewService.save(review22);
        reviewService.save(review32);
        reviewService.save(review13);
        reviewService.save(review23);
        reviewService.save(review33);
        reviewService.save(review43);
        reviewService.save(review53);
        reviewService.save(review14);
        reviewService.save(review24);
        reviewService.save(review34);
        reviewService.save(review15);
        reviewService.save(review25);
        reviewService.save(review35);
        reviewService.save(review45);
        reviewService.save(review16);
        reviewService.save(review26);
        reviewService.save(review36);
    }

    @Override
    public int getOrder() {
        return 4;
    }
}