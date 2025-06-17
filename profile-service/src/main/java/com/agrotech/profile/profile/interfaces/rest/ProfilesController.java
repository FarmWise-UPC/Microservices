package com.agrotech.profile.profile.interfaces.rest;

import com.agrotech.profile.profile.domain.model.aggregates.Profile;
import com.agrotech.profile.profile.domain.model.commands.DeleteProfileCommand;
import com.agrotech.profile.profile.domain.model.queries.GetAllAdvisorProfilesQuery;
import com.agrotech.profile.profile.domain.model.queries.GetAllProfilesQuery;
import com.agrotech.profile.profile.domain.model.queries.GetProfileByIdQuery;
import com.agrotech.profile.profile.domain.model.queries.GetProfileByUserIdQuery;
import com.agrotech.profile.profile.domain.services.ProfileCommandService;
import com.agrotech.profile.profile.domain.services.ProfileQueryService;
import com.agrotech.profile.profile.interfaces.rest.resources.CreateProfileResource;
import com.agrotech.profile.profile.interfaces.rest.resources.ProfileResource;
import com.agrotech.profile.profile.interfaces.rest.resources.UpdateProfileResource;
import com.agrotech.profile.profile.interfaces.rest.transform.CreateProfileCommandFromResourceAssembler;
import com.agrotech.profile.profile.interfaces.rest.transform.ProfileResourceFromEntityAssembler;
import com.agrotech.profile.profile.interfaces.rest.transform.UpdateProfileCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

//@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
@RestController
@RequestMapping(value="api/v1/profiles", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Profiles", description = "Profile Management Endpoints")
public class ProfilesController {
    private final ProfileCommandService profileCommandService;
    private final ProfileQueryService profileQueryService;

    public ProfilesController(ProfileCommandService profileCommandService, ProfileQueryService profileQueryService) {
        this.profileCommandService = profileCommandService;
        this.profileQueryService = profileQueryService;
    }

    @GetMapping
    public ResponseEntity<List<ProfileResource>> getAllProfiles() {
        var getAllProfilesQuery = new GetAllProfilesQuery();
        var profiles = profileQueryService.handle(getAllProfilesQuery);
        var profileResources = profiles.stream().map(ProfileResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(profileResources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileResource> getProfileById(@PathVariable Long id) {
        var getProfileByIdQuery = new GetProfileByIdQuery(id);
        var profile = profileQueryService.handle(getProfileByIdQuery);
        if (profile.isEmpty()) return ResponseEntity.notFound().build();
        var profileResource = ProfileResourceFromEntityAssembler.toResourceFromEntity(profile.get());
        return ResponseEntity.ok(profileResource);
    }

    @GetMapping("/{userId}/user")
    public ResponseEntity<ProfileResource> getProfileByUserId(@PathVariable Long userId) {
        var getProfileByUserIdQuery = new GetProfileByUserIdQuery(userId);
        var profile = profileQueryService.handle(getProfileByUserIdQuery);
        if (profile.isEmpty()) return ResponseEntity.notFound().build();
        var profileResource = ProfileResourceFromEntityAssembler.toResourceFromEntity(profile.get());
        return ResponseEntity.ok(profileResource);
    }

    @GetMapping("/advisors")
    public ResponseEntity<List<ProfileResource>> getAdvisors() {
        var GetAllAdvisorProfilesQuery = new GetAllAdvisorProfilesQuery();
        var profiles = profileQueryService.handle(GetAllAdvisorProfilesQuery);
        var profileResources = profiles.stream().map(ProfileResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(profileResources);
    }

    @PostMapping
    public ResponseEntity<ProfileResource> createProfile(@RequestBody CreateProfileResource createProfileResource,
                                                         @RequestHeader("Authorization") String token) {
        var createProfileCommand = CreateProfileCommandFromResourceAssembler.toCommandFromResource(createProfileResource);
        Long profileId = profileCommandService.handle(createProfileCommand, token);
        var profile = profileQueryService.handle(new GetProfileByIdQuery(profileId));
        if (profile.isEmpty()) return ResponseEntity.badRequest().build();
        var profileResource = ProfileResourceFromEntityAssembler.toResourceFromEntity(profile.get());
        return new ResponseEntity<>(profileResource, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfileResource> updateProfile(@PathVariable Long id,@RequestBody UpdateProfileResource updateProfileResource) {
        var updateProfileCommand = UpdateProfileCommandFromResourceAssembler.toCommandFromResource(id, updateProfileResource);
        Optional<Profile> profile = profileCommandService.handle(updateProfileCommand);
        if (profile.isEmpty()) return ResponseEntity.notFound().build();
        var profileResource = ProfileResourceFromEntityAssembler.toResourceFromEntity(profile.get());
        return ResponseEntity.ok(profileResource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProfile(@PathVariable Long id) {
        var deleteProfileCommand = new DeleteProfileCommand(id);
        profileCommandService.handle(deleteProfileCommand);
        return ResponseEntity.ok("Profile with id " + id + " deleted successfully");
    }
}
