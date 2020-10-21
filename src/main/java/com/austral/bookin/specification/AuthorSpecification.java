package com.austral.bookin.specification;

import com.austral.bookin.entity.Author;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@Spec(path = "fullName", params = "name", spec = Like.class)
public interface AuthorSpecification extends Specification<Author> {}