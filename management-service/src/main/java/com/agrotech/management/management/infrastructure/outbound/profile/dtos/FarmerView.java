package com.agrotech.management.management.infrastructure.outbound.profile.dtos;

import lombok.Getter;

public record FarmerView(
        @Getter
        Long id,
        Long userId
) {

}
