package com.dmitrijs.wishlist.specification;

import com.dmitrijs.wishlist.Model.Wish;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class WishSpecification implements Specification<Wish> {

    private Wish filter;

    public WishSpecification(Wish filter) {
        super();
        this.filter = filter;
    }

    @Override
    public Predicate toPredicate(Root<Wish> root, CriteriaQuery<?> cq,
                                 CriteriaBuilder cb) {

        Predicate p = cb.disjunction();

        if (filter.getMatter() != null) {
            p.getExpressions().add(cb.like(root.get("name"), "%" + filter.getMatter() + "%"));
        }

        return p;
    }
}
