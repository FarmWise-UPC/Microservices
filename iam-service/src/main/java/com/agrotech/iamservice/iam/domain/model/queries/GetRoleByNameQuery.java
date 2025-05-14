package com.agrotech.iamservice.iam.domain.model.queries;

import com.agrotech.iamservice.iam.domain.model.valueobjects.Roles;

public record GetRoleByNameQuery(Roles name) {
}