package com.agrotech.post.interfaces.rest.resources;

public record PostResource(Long id, Long advisorId, String title, String description, String image) {
}
