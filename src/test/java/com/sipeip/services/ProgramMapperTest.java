package com.sipeip.services;

import com.sipeip.domain.dto.ProgramProjectView;
import com.sipeip.domain.entity.Program;
import com.sipeip.infrastructure.input.adapter.rest.models.ProgramResponse;
import com.sipeip.infrastructure.input.adapter.rest.models.ProjectSummary;
import com.sipeip.repository.ProgramProjectsRepository;
import com.sipeip.service.mapper.ProgramMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ProgramMapperTest {

    @Mock
    private ProgramProjectsRepository programProjectsRepository;

    @InjectMocks
    private ProgramMapper programMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void toGoalResponseShouldReturnNullWhenInputIsNull() {
        // Act
        ProgramResponse result = programMapper.toGoalResponse(null);

        // Assert
        assertThat(result).isNull();
    }

    @Test
    void toGoalResponseShouldReturnResponseWithProjects() {
        // Arrange
        Program program = Program.builder()
                .id(1)
                .name("Program 1")
                .description("Test program")
                .scope("National")
                .status("ACTIVE")
                .responsible("Jane")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        ProgramProjectView projectView = mock(ProgramProjectView.class);
        when(projectView.getProjectId()).thenReturn(100);
        when(projectView.getProjectName()).thenReturn("Project Alpha");
        when(projectView.getProjectType()).thenReturn("Investment");

        when(programProjectsRepository.findProgramWithProjectsByProgramId(1))
                .thenReturn(List.of(projectView));

        // Act
        ProgramResponse result = programMapper.toGoalResponse(program);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getName()).isEqualTo("Program 1");
        assertThat(result.getProjects()).hasSize(1);
        ProjectSummary summary = result.getProjects().get(0);
        assertThat(summary.getId()).isEqualTo(100);
        assertThat(summary.getName()).isEqualTo("Project Alpha");
        assertThat(summary.getProjectType()).isEqualTo("Investment");
        verify(programProjectsRepository).findProgramWithProjectsByProgramId(1);
    }

    @Test
    void toGoalResponseShouldReturnResponseWithEmptyProjectsIfNoneFound() {
        // Arrange
        Program program = Program.builder()
                .id(2)
                .name("Program 2")
                .build();

        when(programProjectsRepository.findProgramWithProjectsByProgramId(2))
                .thenReturn(Collections.emptyList());

        // Act
        ProgramResponse result = programMapper.toGoalResponse(program);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(2);
        assertThat(result.getProjects()).isEmpty();
    }

    @Test
    void toGoalResponseFromGoalShouldMapListCorrectly() {
        // Arrange
        Program p1 = Program.builder().id(1).name("P1").build();
        Program p2 = Program.builder().id(2).name("P2").build();

        when(programProjectsRepository.findProgramWithProjectsByProgramId(anyInt()))
                .thenReturn(Collections.emptyList());

        // Act
        List<ProgramResponse> responses = programMapper.toGoalResponseFromGoal(List.of(p1, p2));

        // Assert
        assertThat(responses).hasSize(2);
        assertThat(responses.get(0).getId()).isEqualTo(1);
        assertThat(responses.get(1).getId()).isEqualTo(2);
    }
}
