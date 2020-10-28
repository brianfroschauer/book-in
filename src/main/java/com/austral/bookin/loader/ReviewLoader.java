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

        final Book book1 = bookService.find(19L);
        final Book book2 = bookService.find(20L);
        final Book book3 = bookService.find(21L);
        final Book book4 = bookService.find(22L);
        final Book book5 = bookService.find(23L);
        final Book book6 = bookService.find(24L);
        final Book book7 = bookService.find(25L);
        final Book book8 = bookService.find(26L);
        final Book book9 = bookService.find(27L);
        final Book book10 = bookService.find(28L);
        final Book book11 = bookService.find(29L);
        final Book book12 = bookService.find(30L);
        final Book book13 = bookService.find(31L);
        final Book book14 = bookService.find(32L);
        final Book book15 = bookService.find(33L);
        final Book book16 = bookService.find(34L);
        final Book book17 = bookService.find(35L);
        final Book book18 = bookService.find(36L);
        final Book book19 = bookService.find(37L);
        final Book book20 = bookService.find(38L);
        final Book book21 = bookService.find(39L);
        final Book book22 = bookService.find(40L);
        final Book book23 = bookService.find(41L);
        final Book book24 = bookService.find(42L);
        final Book book25 = bookService.find(43L);
        final Book book26 = bookService.find(44L);
        final Book book27 = bookService.find(45L);
        final Book book28 = bookService.find(46L);
        final Book book29 = bookService.find(47L);
        final Book book30 = bookService.find(48L);

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

        Review review17 = new Review(1, "Muy aburrido, no leería los próximos", user2, book7);
        Review review27 = new Review(4, "Muy bueno para aprender sobre mitología griega", user3, book7);

        Review review18 = new Review(4, "Mucho mejor que el primero! Súper divertido", user5, book8);
        Review review28 = new Review(5, "Excelente", user6, book8);

        Review review19 = new Review(5, "Excelente final", user1, book9);
        Review review29 = new Review(4, "Final inesperado, muy bueno", user2, book9);

        Review review110 = new Review(5, "Un clásico excelente", user3, book10);

        Review review111 = new Review(2, "", user4, book11);

        Review review112 = new Review(5, "", user6, book12);
        Review review212 = new Review(3, "", user7, book12);

        Review review113 = new Review(5, "Super atrapante", user2, book13);
        Review review213 = new Review(3, "Muy divertido pero predecible", user5, book13);

        Review review114 = new Review(5, "Me encanta, no puedo esperar al próximo", user2, book14);
        Review review214 = new Review(2, "El primero era mejor", user6, book14);

        Review review115 = new Review(3, "Medio pesado, pero vale la pena", user1, book15);
        Review review215 = new Review(4, "El final es muy bueno", user3, book15);

        Review review116 = new Review(5, "De los mejores clásicos", user5, book16);
        Review review216 = new Review(5, "", user6, book16);

        Review review117 = new Review(2, "Un poco aburrido, no da miedo", user3, book17);

        Review review118 = new Review(5, "Para llorar toda la tarde :(", user2, book18);

        Review review119 = new Review(5, "Muy divertido y fantasioso", user2, book19);
        Review review219 = new Review(3, "Muy predecible y para niños", user6, book19);

        Review review120 = new Review(2, "Muy controversial", user4, book20);

        Review review121 = new Review(5, "Excelente obra de JK", user7, book21);

        Review review122 = new Review(3, "Un poco pesado", user5, book22);

        Review review123 = new Review(5, "El mejor clásico de la historia", user1, book23);
        Review review223 = new Review(5, "Una historia de amor hermosa", user2, book23);
        Review review323 = new Review(5, "Uno de los mejores libros que existen", user4, book23);

        Review review124 = new Review(4, "Muy divertida", user6, book24);

        Review review125 = new Review(3, "Interesante pero predecible", user3, book25);

        Review review126 = new Review(4, "Muy divertido, excelente obra", user5, book26);

        Review review127 = new Review(5, "Inesperado final, muy bueno", user2, book27);
        Review review227 = new Review(3, "Un poco complejo pero divertido", user7, book27);

        Review review128 = new Review(4, "Un clásico", user1, book28);

        Review review129 = new Review(5, "Un clásico muy divertido y festivo", user2, book29);
        Review review229 = new Review(2, "Un poco aburrido", user6, book29);

        Review review130 = new Review(4, "Excelente obra", user4, book30);

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
        reviewService.save(review17);
        reviewService.save(review27);
        reviewService.save(review18);
        reviewService.save(review28);
        reviewService.save(review19);
        reviewService.save(review29);
        reviewService.save(review110);
        reviewService.save(review111);
        reviewService.save(review112);
        reviewService.save(review212);
        reviewService.save(review113);
        reviewService.save(review213);
        reviewService.save(review114);
        reviewService.save(review214);
        reviewService.save(review115);
        reviewService.save(review215);
        reviewService.save(review116);
        reviewService.save(review216);
        reviewService.save(review117);
        reviewService.save(review118);
        reviewService.save(review119);
        reviewService.save(review219);
        reviewService.save(review120);
        reviewService.save(review121);
        reviewService.save(review122);
        reviewService.save(review123);
        reviewService.save(review223);
        reviewService.save(review323);
        reviewService.save(review124);
        reviewService.save(review125);
        reviewService.save(review126);
        reviewService.save(review127);
        reviewService.save(review227);
        reviewService.save(review128);
        reviewService.save(review129);
        reviewService.save(review229);
        reviewService.save(review130);
    }

    @Override
    public int getOrder() {
        return 4;
    }
}
