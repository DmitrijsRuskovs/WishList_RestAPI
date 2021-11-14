package com.dmitrijs.wishlist.Repository;


import com.dmitrijs.wishlist.Model.User;
import com.dmitrijs.wishlist.Model.Wish;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Long>,
        JpaSpecificationExecutor<User> {
}
