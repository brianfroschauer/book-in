package com.austral.bookin.loader;

import com.austral.bookin.entity.Author;
import com.austral.bookin.service.author.AuthorService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Profile("!test")
@Component
public class AuthorLoader implements CommandLineRunner, Ordered {

    private final AuthorService authorService;

    public AuthorLoader(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Override
    public void run(String... args) {
        final Author author1 = new Author(
                "Mark",
                "Twain",
                "US",
                new Date(-65, Calendar.SEPTEMBER, 30)
        );
        final Author author2 = new Author(
                "Charles",
                "Dickens",
                "GB",
                new Date(-88, Calendar.FEBRUARY, 7)
        );
        final Author author3 = new Author(
                "William",
                "Shakespeare",
                "GB",
                new Date(65, Calendar.JULY, 31)
        );
        final Author author4 = new Author(
                "Joanne",
                "Rowling",
                "GB",
                new Date(65, Calendar.JULY, 31)
        );

        authorService.save(author1, null);
        authorService.save(author2, null);
        authorService.save(author3, null);
        authorService.save(author4, null);
    }

    @Override
    public int getOrder() {
        return 2;
    }
}