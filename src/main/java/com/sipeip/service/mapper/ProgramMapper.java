package com.sipeip.service.mapper;


import com.sipeip.domain.dto.ProgramProjectView;
import com.sipeip.domain.entity.Program;
import com.sipeip.infrastructure.input.adapter.rest.models.ProgramResponse;
import com.sipeip.infrastructure.input.adapter.rest.models.ProjectSummary;
import com.sipeip.repository.ProgramProjectsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProgramMapper {
    private final ProgramProjectsRepository programProjectsRepository;

    public ProgramResponse toGoalResponse(Program project) {
        if (project == null) {
            return null;
        }
        ProgramResponse goalResponse = new ProgramResponse();
        goalResponse.setId(project.getId());
        goalResponse.setName(project.getName());
        goalResponse.setDescription(project.getDescription());
        goalResponse.setScope(project.getScope());
        goalResponse.setStatus(project.getStatus());
        goalResponse.setResponsible(project.getResponsible());
        goalResponse.setCreatedAt(String.valueOf(project.getCreatedAt()));
        goalResponse.setUpdatedAt(String.valueOf(project.getUpdatedAt()));
        List<ProjectSummary> goalResponseList = new ArrayList<>();
        for (ProgramProjectView projectGoalView : programProjectsRepository.findProgramWithProjectsByProgramId(goalResponse.getId())) {
            ProjectSummary response = new ProjectSummary();
            response.setId(projectGoalView.getProjectId());
            response.setName(projectGoalView.getProjectName());
            response.setProjectType(projectGoalView.getProjectType());
            goalResponseList.add(response);
        }
        goalResponse.setProjects(goalResponseList);
        return goalResponse;
    }

    public List<ProgramResponse> toGoalResponseFromGoal(List<Program> entitiesList) {
        return entitiesList.stream()
                .map(this::toGoalResponse)
                .toList();
    }
}