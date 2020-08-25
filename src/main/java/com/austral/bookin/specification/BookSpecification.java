package com.austral.bookin.specification;

import com.austral.bookin.entity.Book;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@And({
        @Spec(path = "title", spec = Equal.class),
        @Spec(path = "genre", spec = Equal.class),
        @Spec(path = "language", spec = Equal.class),
})
public interface BookSpecification extends Specification<Book> {}