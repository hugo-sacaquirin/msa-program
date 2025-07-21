package com.sipeip.services;

import com.sipeip.domain.entity.Program;
import com.sipeip.domain.entity.ProgramProjects;
import com.sipeip.infrastructure.input.adapter.rest.models.ProgramPagedResponse;
import com.sipeip.infrastructure.input.adapter.rest.models.ProgramRequest;
import com.sipeip.infrastructure.input.adapter.rest.models.ProgramResultResponse;
import com.sipeip.repository.ProgramProjectsRepository;
import com.sipeip.repository.ProgramRepository;
import com.sipeip.service.impl.ProgramServiceImpl;
import com.sipeip.service.mapper.ProgramMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class ProgramServiceImplTest {

    @Mock
    private ProgramRepository programRepository;
    @Mock
    private ProgramProjectsRepository programProjectsRepository;
    @Mock
    private ProgramMapper programMapper;

    @InjectMocks
    private ProgramServiceImpl programService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProgramShouldReturnResultResponseWhenSuccess() {
        // Arrange
        ProgramRequest request = new ProgramRequest();
        request.setName("Test Program");
        request.setDescription("Desc");
        request.setScope("Global");
        request.setStatus("ACTIVE");
        request.setResponsible("John");
        request.setProjectIds(List.of(1, 2));

        Program saved = Program.builder()
                .id(10)
                .name("Test Program")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        when(programRepository.save(any(Program.class))).thenReturn(saved);

        // Act
        ProgramResultResponse result = programService.createProgram(request);

        // Assert
        assertThat(result.getResult()).contains("Project program successfully");
        verify(programRepository).save(any(Program.class));
        verify(programProjectsRepository, times(2)).save(any(ProgramProjects.class));
    }

    @Test
    void createProgramShouldThrowExceptionIfIdIsNull() {
        // Arrange
        ProgramRequest request = new ProgramRequest();
        when(programRepository.save(any(Program.class))).thenReturn(Program.builder().id(null).build());

        // Act & Assert
        assertThatThrownBy(() -> programService.createProgram(request))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Error creating program");
    }

    @Test
    void updateProjectShouldReturnResultResponseWhenSuccess() {
        // Arrange
        Integer id = 5;
        ProgramRequest request = new ProgramRequest();
        request.setName("Updated");
        request.setDescription("Desc");
        request.setScope("Scope");
        request.setStatus("ACTIVE");
        request.setResponsible("Jane");

        Program saved = Program.builder()
                .id(id)
                .name("Updated")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        when(programRepository.save(any(Program.class))).thenReturn(saved);

        // Act
        ProgramResultResponse result = programService.updateProject(id, request);

        // Assert
        assertThat(result.getResult()).contains("Program updating successfully");
        verify(programRepository).save(any(Program.class));
    }

    @Test
    void updateProjectShouldThrowExceptionIfIdIsNull() {
        // Arrange
        Integer id = 7;
        ProgramRequest request = new ProgramRequest();
        when(programRepository.save(any(Program.class))).thenReturn(Program.builder().id(null).build());

        // Act & Assert
        assertThatThrownBy(() -> programService.updateProject(id, request))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Error updating program");
    }

    @Test
    void deleteProgramByIdShouldDeleteSuccessfully() {
        // Arrange
        Integer id = 12;
        doNothing().when(programRepository).deleteById(id);
        when(programRepository.existsById(id)).thenReturn(false);

        // Act
        programService.deleteProgramById(id);

        // Assert
        verify(programRepository).deleteById(id);
        verify(programRepository).existsById(id);
    }

    @Test
    void deleteProgramByIdShouldThrowExceptionIfStillExists() {
        // Arrange
        Integer id = 13;
        doNothing().when(programRepository).deleteById(id);
        when(programRepository.existsById(id)).thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> programService.deleteProgramById(id))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Error deleting entity");
    }

    @Test
    void getPagedProgramsShouldReturnPagedResponse() {
        // Arrange
        ProgramPagedResponse pagedResponse = new ProgramPagedResponse();
        when(programRepository.findAll()).thenReturn(Collections.emptyList());
        when(programMapper.toGoalResponseFromGoal(anyList())).thenReturn(Collections.emptyList());

        // Act
        ProgramPagedResponse result = programService.getPagedPrograms(0, 10);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEmpty();
        verify(programRepository).findAll();
        verify(programMapper).toGoalResponseFromGoal(anyList());
    }

    @Test
    void searchProgramsShouldReturnByNameIfTypeIsZero() {
        // Arrange
        ProgramPagedResponse pagedResponse = new ProgramPagedResponse();
        when(programRepository.findByName("Test")).thenReturn(Collections.emptyList());
        when(programMapper.toGoalResponseFromGoal(anyList())).thenReturn(Collections.emptyList());

        // Act
        ProgramPagedResponse result = programService.searchPrograms(0, 10, "Test", "X", "0");

        // Assert
        assertThat(result.getContent()).isEmpty();
        verify(programRepository).findByName("Test");
    }

    @Test
    void searchProgramsShouldReturnByResponsibleIfTypeIsNotZero() {
        // Arrange
        ProgramPagedResponse pagedResponse = new ProgramPagedResponse();
        when(programRepository.findByResponsible("Resp")).thenReturn(Collections.emptyList());
        when(programMapper.toGoalResponseFromGoal(anyList())).thenReturn(Collections.emptyList());

        // Act
        ProgramPagedResponse result = programService.searchPrograms(0, 10, "A", "Resp", "1");

        // Assert
        assertThat(result.getContent()).isEmpty();
        verify(programRepository).findByResponsible("Resp");
    }
}
