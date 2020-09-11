package com.austral.bookin.specification;

import com.austral.bookin.entity.Book;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@Spec(path = "title", spec = Equal.class)
public interface BookSpecification extends Specification<Book> {}