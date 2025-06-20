package com.agrotech.iamservice.application.internal.eventhandlers;


import com.agrotech.iamservice.iam.application.internal.eventhandlers.ApplicationReadyEventHandler;
import com.agrotech.iamservice.iam.domain.model.commands.SeedRolesCommand;
import com.agrotech.iamservice.iam.domain.services.RoleCommandService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ConfigurableApplicationContext;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ApplicationReadyEventHandlerTest {

    @Test
    public void test_on_application_ready_calls_role_command_service() {
        // Arrange
        RoleCommandService mockRoleCommandService = Mockito.mock(RoleCommandService.class);
        ApplicationReadyEventHandler handler = new ApplicationReadyEventHandler(mockRoleCommandService);
        ApplicationReadyEvent mockEvent = Mockito.mock(ApplicationReadyEvent.class);
        ConfigurableApplicationContext mockContext = Mockito.mock(ConfigurableApplicationContext.class);

        Mockito.when(mockEvent.getApplicationContext()).thenReturn(mockContext);
        Mockito.when(mockContext.getId()).thenReturn("test-application");

        // Act
        handler.on(mockEvent);

        // Assert
        Mockito.verify(mockRoleCommandService).handle(Mockito.any(SeedRolesCommand.class));
    }

    @Test
    public void test_on_application_ready_handles_exception_from_role_command_service() {
        // Arrange
        RoleCommandService mockRoleCommandService = Mockito.mock(RoleCommandService.class);
        ApplicationReadyEventHandler handler = new ApplicationReadyEventHandler(mockRoleCommandService);
        ApplicationReadyEvent mockEvent = Mockito.mock(ApplicationReadyEvent.class);
        ConfigurableApplicationContext mockContext = Mockito.mock(ConfigurableApplicationContext.class);

        Mockito.when(mockEvent.getApplicationContext()).thenReturn(mockContext);
        Mockito.when(mockContext.getId()).thenReturn("test-application");
        Mockito.doThrow(new RuntimeException("Test exception")).when(mockRoleCommandService).handle(Mockito.any(SeedRolesCommand.class));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> handler.on(mockEvent));
        Mockito.verify(mockRoleCommandService).handle(Mockito.any(SeedRolesCommand.class));
    }
}