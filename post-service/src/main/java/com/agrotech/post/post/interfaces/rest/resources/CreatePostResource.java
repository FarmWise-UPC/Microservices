package com.agrotech.post.post.interfaces.rest.resources;

public record CreatePostResource(Long advisorId, String title, String description, String image) {
}
