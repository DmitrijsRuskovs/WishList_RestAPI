package com.dmitrijs.wishlist.Controller;

import com.dmitrijs.wishlist.Model.User;
import com.dmitrijs.wishlist.Model.UsersRequest;
import com.dmitrijs.wishlist.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService _userService;

    @PostMapping(value = "/users")
    public ResponseEntity<String> addUsers(@RequestBody UsersRequest usersRequest) {
        String response = "";
        for (User user:usersRequest.users) {
            response += user.getName() + ",";
            _userService.save(user);
        }
        response =response.substring(0,response.length() -2);
        return ResponseEntity.ok(response);
    }
}
