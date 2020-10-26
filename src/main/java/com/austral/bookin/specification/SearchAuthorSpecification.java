package com.austral.bookin.specification;

import com.austral.bookin.entity.Author;
import net.kaczmarzyk.spring.data.jpa.domain.Between;
import net.kaczmarzyk.spring.data.jpa.domain.In;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@And({
        @Spec(path = "fullName", params = "name", spec = Like.class),
        @Spec(path = "nationality", params = "nationality", spec = In.class),
        @Spec(path = "birthday", params = {"bornAfter", "bornBefore"}, spec = Between.class)
})
public interface SearchAuthorSpecification extends Specification<Author> {}