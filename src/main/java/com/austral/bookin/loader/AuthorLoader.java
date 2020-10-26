package com.austral.bookin.loader;

import com.austral.bookin.entity.Author;
import com.austral.bookin.service.author.AuthorService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
    public void run(String... args) throws IOException {
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

        authorService.save(author1, new MockMultipartFile("mark_twain.jpg", new FileInputStream(new File("src/main/resources/assets/authors/mark_twain.jpg"))));
        authorService.save(author2, new MockMultipartFile("charles_dickens.jpg", new FileInputStream(new File("src/main/resources/assets/authors/charles_dickens.jpg"))));
        authorService.save(author3, new MockMultipartFile("william_shakespeare.jpg", new FileInputStream(new File("src/main/resources/assets/authors/william_shakespeare.jpg"))));
        authorService.save(author4, new MockMultipartFile("jk_rowling.jpg", new FileInputStream(new File("src/main/resources/assets/authors/jk_rowling.jpg"))));
    }

    @Override
    public int getOrder() {
        return 2;
    }
}