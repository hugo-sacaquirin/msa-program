package com.sipeip.service.impl;

import com.sipeip.domain.entity.Program;
import com.sipeip.domain.entity.ProgramProjects;
import com.sipeip.infrastructure.input.adapter.rest.models.ProgramPagedResponse;
import com.sipeip.infrastructure.input.adapter.rest.models.ProgramRequest;
import com.sipeip.infrastructure.input.adapter.rest.models.ProgramResultResponse;
import com.sipeip.repository.ProgramProjectsRepository;
import com.sipeip.repository.ProgramRepository;
import com.sipeip.service.ProgramService;
import com.sipeip.service.mapper.ProgramMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static com.sipeip.util.StaticValues.CREATED;

@Service
@RequiredArgsConstructor
public class ProgramServiceImpl implements ProgramService {
    private final ProgramRepository programRepository;
    private final ProgramProjectsRepository programProjectsRepository;
    private final ProgramMapper programMapper;

    @Override
    public ProgramResultResponse createProgram(ProgramRequest request) {
        Program program = programRepository.save(Program.builder()
                .name(request.getName())
                .description(request.getDescription())
                .scope(request.getScope())
                .status(request.getStatus())
                .responsible(request.getResponsible())
                .createdAt(java.time.LocalDateTime.now())
                .updatedAt(java.time.LocalDateTime.now())
                .build());
        if (program.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error creating program");
        }
        for (Integer idGoals : request.getProjectIds()) {
            programProjectsRepository.save(ProgramProjects.builder().programId(program.getId()).projectId(idGoals).build());
        }
        return getGoalResultResponse("Project program successfully");
    }

    @Override
    public ProgramResultResponse updateProject(Integer id, ProgramRequest request) {
        Program program = programRepository.save(Program.builder()
                .name(request.getName())
                .description(request.getDescription())
                .scope(request.getScope())
                .status(request.getStatus())
                .responsible(request.getResponsible())
                .createdAt(java.time.LocalDateTime.now())
                .updatedAt(java.time.LocalDateTime.now())
                .build());
        if (program.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error updating program");
        }
        return getGoalResultResponse("Program updating successfully");
    }

    private static ProgramResultResponse getGoalResultResponse(String message) {
        ProgramResultResponse entityResultResponse = new ProgramResultResponse();
        entityResultResponse.setCode(CREATED);
        entityResultResponse.setResult(message);
        return entityResultResponse;
    }

    @Override
    public void deleteProgramById(Integer id) {
        programRepository.deleteById(id);
        if (programRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error deleting entity");
        }
    }

    @Override
    public ProgramPagedResponse getPagedPrograms(Integer page, Integer size) {
        ProgramPagedResponse entityPagedResponse = new ProgramPagedResponse();
        entityPagedResponse.setContent(programMapper.toGoalResponseFromGoal(programRepository.findAll()));
        return entityPagedResponse;
    }

    @Override
    public ProgramPagedResponse searchPrograms(Integer page, Integer size, String name, String code, String type) {
        ProgramPagedResponse entityPagedResponse = new ProgramPagedResponse();
        if (type.equals("0")) {
            entityPagedResponse.setContent(programMapper.toGoalResponseFromGoal(programRepository.findByName(name)));
        } else {
            entityPagedResponse.setContent(programMapper.toGoalResponseFromGoal(programRepository.findByResponsible(code)));

        }
        return entityPagedResponse;
    }
}
