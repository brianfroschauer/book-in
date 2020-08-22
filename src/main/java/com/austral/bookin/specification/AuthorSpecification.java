package com.austral.bookin.specification;

import com.austral.bookin.entity.Author;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@And({
        @Spec(path = "firstName", spec = Equal.class),
        @Spec(path = "lastName", spec = Equal.class),
})

public interface AuthorSpecification extends Specification<Author> {}