package com.dmitrijs.wishlist.Repository;

import com.dmitrijs.wishlist.Model.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface WishRepository extends PagingAndSortingRepository<Wish, Long>,
        JpaSpecificationExecutor<Wish> {
}
