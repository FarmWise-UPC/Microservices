package com.agrotech.profile.profile.domain.model.commands;

import java.math.BigDecimal;

public record UpdateAdvisorCommand(Long id, BigDecimal rating) {
}
