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
                new Date(-65, Calendar.SEPTEMBER, 30));

        final Author author2 = new Author(
                "Charles",
                "Dickens",
                "GB",
                new Date(-88, Calendar.FEBRUARY, 7));

        final Author author3 = new Author(
                "William",
                "Shakespeare",
                "GB",
                new Date(65, Calendar.JULY, 31));

        final Author author4 = new Author(
                "Joanne",
                "Rowling",
                "GB",
                new Date(65, Calendar.JULY, 31));

        final Author author5 = new Author(
                "Rick",
                "Riordan",
                "US",
                new Date(64, Calendar.JUNE, 5));

        final Author author6 = new Author(
                "Jane",
                "Austen",
                "US",
                new Date(-125, Calendar.DECEMBER, 16));

        final Author author7 = new Author(
                "Lauren",
                "Oliver",
                "US",
                new Date(82, Calendar.NOVEMBER, 8));

        final Author author8 = new Author(
                "Robert",
                "Fisher",
                "US",
                new Date(82, Calendar.JUNE, 4));

        final Author author9 = new Author(
                "Mary",
                "Stewart",
                "GB",
                new Date(16, Calendar.JULY, 2));

        authorService.save(author1, new MockMultipartFile("mark_twain.jpg", new FileInputStream(new File("src/main/resources/assets/authors/mark_twain.jpg"))));
        authorService.save(author2, new MockMultipartFile("charles_dickens.jpg", new FileInputStream(new File("src/main/resources/assets/authors/charles_dickens.jpg"))));
        authorService.save(author3, new MockMultipartFile("william_shakespeare.jpg", new FileInputStream(new File("src/main/resources/assets/authors/william_shakespeare.jpg"))));
        authorService.save(author4, new MockMultipartFile("jk_rowling.jpg", new FileInputStream(new File("src/main/resources/assets/authors/jk_rowling.jpg"))));
        authorService.save(author5, new MockMultipartFile("rick_riordan.jpg", new FileInputStream(new File("src/main/resources/assets/authors/rick_riordan.jpg"))));
        authorService.save(author6, new MockMultipartFile("jane_austen.jpg", new FileInputStream(new File("src/main/resources/assets/authors/jane_austen.jpg"))));
        authorService.save(author7, new MockMultipartFile("lauren_oliver.jpg", new FileInputStream(new File("src/main/resources/assets/authors/lauren_oliver.jpeg"))));
        authorService.save(author8, new MockMultipartFile("robert_fisher.jpg", new FileInputStream(new File("src/main/resources/assets/authors/robert_fisher.jpg"))));
        authorService.save(author9, new MockMultipartFile("mary_stewart.jpeg", new FileInputStream(new File("src/main/resources/assets/authors/mary_stewart.jpeg"))));
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
