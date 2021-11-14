package com.dmitrijs.wishlist.Service;

import com.dmitrijs.wishlist.Exceptions.BadResourceException;
import com.dmitrijs.wishlist.Exceptions.ResourceAlreadyExistsException;
import com.dmitrijs.wishlist.Model.User;
import com.dmitrijs.wishlist.Model.Wish;
import com.dmitrijs.wishlist.Repository.UserRepository;
import com.dmitrijs.wishlist.Repository.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository _userRepository;

    public User save(User user){

        return _userRepository.save(user);

    }
}
