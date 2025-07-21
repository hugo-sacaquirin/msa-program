package com.sipeip.controller;

import com.sipeip.infrastructure.input.adapter.rest.models.ProgramPagedResponse;
import com.sipeip.infrastructure.input.adapter.rest.models.ProgramRequest;
import com.sipeip.infrastructure.input.adapter.rest.models.ProgramResultResponse;
import com.sipeip.service.ProgramService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProgramControllerTest {

    @Mock
    private ProgramService programService;

    @InjectMocks
    private ProgramController programController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProgramShouldReturnCreatedResponse() {
        // Arrange
        ProgramRequest request = new ProgramRequest();
        ProgramResultResponse response = new ProgramResultResponse();
        when(programService.createProgram(request)).thenReturn(response);

        // Act
        ResponseEntity<ProgramResultResponse> result = programController.createProgram(request);

        // Assert
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isEqualTo(response);
        verify(programService).createProgram(request);
    }

    @Test
    void deactivateProgramShouldReturnNoContent() {
        // Arrange
        Integer id = 10;

        // Act
        ResponseEntity<Void> result = programController.deactivateProgram(id);

        // Assert
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(programService).deleteProgramById(id);
    }

    @Test
    void getPagedProgramsShouldReturnOkWithPagedResponse() {
        // Arrange
        int page = 0, size = 10;
        ProgramPagedResponse pagedResponse = new ProgramPagedResponse();
        when(programService.getPagedPrograms(page, size)).thenReturn(pagedResponse);

        // Act
        ResponseEntity<ProgramPagedResponse> result = programController.getPagedPrograms(page, size);

        // Assert
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(pagedResponse);
        verify(programService).getPagedPrograms(page, size);
    }

    @Test
    void searchProgramsShouldReturnOkWithSearchResults() {
        // Arrange
        int page = 0, size = 10;
        String name = "Test", responsible = "Owner", type = "TypeA";
        ProgramPagedResponse pagedResponse = new ProgramPagedResponse();
        when(programService.searchPrograms(page, size, name, responsible, type)).thenReturn(pagedResponse);

        // Act
        ResponseEntity<ProgramPagedResponse> result = programController.searchPrograms(page, size, name, responsible, type);

        // Assert
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(pagedResponse);
        verify(programService).searchPrograms(page, size, name, responsible, type);
    }

    @Test
    void updateProgramShouldReturnCreatedResponse() {
        // Arrange
        int id = 33;
        ProgramRequest request = new ProgramRequest();
        ProgramResultResponse response = new ProgramResultResponse();
        when(programService.updateProject(id, request)).thenReturn(response);

        // Act
        ResponseEntity<ProgramResultResponse> result = programController.updateProgram(id, request);

        // Assert
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isEqualTo(response);
        verify(programService).updateProject(id, request);
    }
}
