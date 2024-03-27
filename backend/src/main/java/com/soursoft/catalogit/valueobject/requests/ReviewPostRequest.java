package com.soursoft.catalogit.valueobject.requests;

public record ReviewPostRequest(Long userId, Long movieId, Integer rating, String reviewBody) {
}
