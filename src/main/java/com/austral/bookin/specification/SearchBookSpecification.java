package com.austral.bookin.specification;

import com.austral.bookin.entity.Book;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@Spec(path = "title", params = "key", spec = Like.class)
public interface SearchBookSpecification extends Specification<Book> {}