package com.dmitrijs.wishlist.Controller;

import com.dmitrijs.wishlist.Exceptions.BadResourceException;
import com.dmitrijs.wishlist.Exceptions.ResourceAlreadyExistsException;
import com.dmitrijs.wishlist.Exceptions.ResourceNotFoundException;
import com.dmitrijs.wishlist.Model.User;
import com.dmitrijs.wishlist.Model.UsersRequest;
import com.dmitrijs.wishlist.Model.Wish;
import com.dmitrijs.wishlist.Service.UserService;
import com.dmitrijs.wishlist.Service.WishService;
import com.dmitrijs.wishlist.Validator.Valid;
import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Properties;
//import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class WishController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final int ROW_PER_PAGE = 5;

    @Autowired
    private WishService _wishService;

    @GetMapping(value = "/wish", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Wish>> findAll(
            @RequestParam(value="page", defaultValue="1") int pageNumber,
            @RequestParam(required=false) String matter) {
        if (StringUtils.isEmpty(matter)) {
            return ResponseEntity.ok(_wishService.findAll(pageNumber, ROW_PER_PAGE));
        }
        else {
            return ResponseEntity.ok(_wishService.findAllByMatter(matter, pageNumber, ROW_PER_PAGE));
        }
    }

    @GetMapping(value = "/wish/{wishId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Wish> findWishById(@PathVariable Long wishId) {
        try {
            Wish foundWish = _wishService.findById(wishId);
            return ResponseEntity.ok(foundWish);  // return 200, with json body
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // return 404, with null body
        }
    }

    @PostMapping(value = "/wish")
    public ResponseEntity<Wish> addWish(@RequestBody Wish wish)
            throws URISyntaxException {
        try {
            Wish newWish = _wishService.save(wish);
            return ResponseEntity.created(new URI("/api/wish/" + newWish.getId()))
                    .body(wish);
        } catch (ResourceAlreadyExistsException ex) {
            // log exception first, then return Conflict (409)
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (BadResourceException ex) {
            // log exception first, then return Bad Request (400)
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping(value = "/wish/{wishId}")
    public ResponseEntity<Wish> updateWish(@Valid @RequestBody Wish wish,
                                                 @PathVariable long wishId) {
        try {
            wish.setId(wishId);
            _wishService.update(wish);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            // log exception first, then return Not Found (404)
            logger.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        } catch (BadResourceException ex) {
            // log exception first, then return Bad Request (400)
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping(path="/wish/{wishId}")
    public ResponseEntity<Void> deleteWishById(@PathVariable Long wishId) {
        try {
            _wishService.deleteById(wishId);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

}
