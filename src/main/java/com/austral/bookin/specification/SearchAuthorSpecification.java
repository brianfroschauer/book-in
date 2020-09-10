package com.austral.bookin.specification;

import com.austral.bookin.entity.Author;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@Or({
        @Spec(path = "firstName", params = "key", spec = Like.class),
        @Spec(path = "lastName", params = "key", spec = Like.class)})

public interface SearchAuthorSpecification extends Specification<Author> {}