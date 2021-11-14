package com.dmitrijs.wishlist.Exceptions;

public class WishNotFoundException extends RuntimeException{
    public WishNotFoundException(String message){
        super(message);
    }

    public WishNotFoundException(String message, Throwable cause){
        super(message, cause);
    }
}
