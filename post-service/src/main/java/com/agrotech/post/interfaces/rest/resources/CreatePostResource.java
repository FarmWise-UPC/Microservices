package com.agrotech.post.interfaces.rest.resources;

public record CreatePostResource(Long advisorId, String title, String description, String image) {
}
