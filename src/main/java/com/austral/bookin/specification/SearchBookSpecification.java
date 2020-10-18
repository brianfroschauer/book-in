package com.austral.bookin.specification;

import com.austral.bookin.entity.Book;
import net.kaczmarzyk.spring.data.jpa.domain.Between;
import net.kaczmarzyk.spring.data.jpa.domain.In;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@And({
        @Spec(path = "title", params = "title", spec = Like.class),
        @Spec(path = "genre", params = "genre", spec = In.class),
        @Spec(path = "language", params = "language", spec = In.class),
        @Spec(path = "date", params = {"publishedAfter", "publishedBefore"}, spec = Between.class),
        @Spec(path = "stars", params = {"lowRange", "topRange"}, spec = Between.class)
})
public interface SearchBookSpecification extends Specification<Book> {}