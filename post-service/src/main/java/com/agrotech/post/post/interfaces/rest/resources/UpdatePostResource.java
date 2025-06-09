package com.agrotech.post.post.interfaces.rest.resources;

public record UpdatePostResource(String title,
                                 String description,
                                 String image, Long advisorId) {
}
