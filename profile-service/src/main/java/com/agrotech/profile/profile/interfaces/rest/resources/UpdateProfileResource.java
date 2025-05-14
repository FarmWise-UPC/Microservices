package com.agrotech.profile.profile.interfaces.rest.resources;

import java.time.LocalDate;

public record UpdateProfileResource(String firstName,
                                    String lastName,
                                    String city,
                                    String country,
                                    LocalDate birthDate,
                                    String description,
                                    String photo,
                                    String occupation,
                                    Integer experience){}
