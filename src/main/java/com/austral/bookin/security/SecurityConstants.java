package com.austral.bookin.security;

public class SecurityConstants {

    public static final String SECRET = "kRaaUVEdNpUz7CiVUB0uj8dU";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/signup";
    public static final String BOOK_SEARCH_URL = "/books/**";
    public static final String AUTHOR_SEARCH_URL = "/authors/**";
    public static final String REVIEW_SEARCH_URL = "/reviews/**";
    public static final String USER_SEARCH_URL = "/users/**";
}