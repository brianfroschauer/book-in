package com.austral.bookin.specification;

import com.austral.bookin.entity.Author;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@And({
        @Spec(path = "firstName", spec = Like.class),
        @Spec(path = "lastName", spec = Like.class),
})
public interface AuthorSpecification extends Specification<Author> {}