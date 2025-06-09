package com.agrotech.post.post.infrastructure.outbound.user.dtos;

import java.math.BigDecimal;

public record AdvisorView(Long id, Long userId, BigDecimal rating) {
}