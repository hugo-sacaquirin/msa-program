package com.sipeip.service;

import com.sipeip.infrastructure.input.adapter.rest.models.ProgramPagedResponse;
import com.sipeip.infrastructure.input.adapter.rest.models.ProgramRequest;
import com.sipeip.infrastructure.input.adapter.rest.models.ProgramResultResponse;

public interface ProgramService {
    ProgramResultResponse createProgram(ProgramRequest request);

    ProgramResultResponse updateProject(Integer id, ProgramRequest request);

    void deleteProgramById(Integer id);

    ProgramPagedResponse getPagedPrograms(Integer page, Integer size);

    ProgramPagedResponse searchPrograms(Integer page, Integer size, String name, String code, String type);
}
