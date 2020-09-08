package com.austral.bookin.specification;

import com.austral.bookin.entity.Book;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@And({
        @Spec(path = "title", spec = Like.class),
        @Spec(path = "genre", spec = Like.class),
        @Spec(path = "language", spec = Like.class),
})
public interface BookSpecification extends Specification<Book> {}