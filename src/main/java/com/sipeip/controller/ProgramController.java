package com.sipeip.controller;


import com.sipeip.infrastructure.input.adapter.rest.ProgramsApi;
import com.sipeip.infrastructure.input.adapter.rest.models.ProgramPagedResponse;
import com.sipeip.infrastructure.input.adapter.rest.models.ProgramRequest;
import com.sipeip.infrastructure.input.adapter.rest.models.ProgramResultResponse;
import com.sipeip.service.ProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProgramController implements ProgramsApi {
    private final ProgramService programService;

    @Override
    public ResponseEntity<ProgramResultResponse> createProgram(ProgramRequest programRequest) {
        return new ResponseEntity<>(programService.createProgram(programRequest), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deactivateProgram(Integer id) {
        programService.deleteProgramById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<ProgramPagedResponse> getPagedPrograms(Integer page, Integer size) {
        return new ResponseEntity<>(programService.getPagedPrograms(page, size), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ProgramPagedResponse> searchPrograms(Integer page, Integer size, String name, String responsible, String type) {
        return new ResponseEntity<>(programService.searchPrograms(page, size, name, responsible, type), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ProgramResultResponse> updateProgram(Integer id, ProgramRequest programRequest) {
        return new ResponseEntity<>(programService.updateProject(id, programRequest), HttpStatus.CREATED);
    }
}
