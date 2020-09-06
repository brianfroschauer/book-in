package com.austral.bookin.specification;

import com.austral.bookin.entity.Review;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@And({
        @Spec(path = "stars", spec = Equal.class),
})
public interface ReviewSpecification extends Specification<Review> {}