package com.sipeip.repository;

import com.sipeip.domain.dto.ProgramProjectView;
import com.sipeip.domain.entity.ProgramProjects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgramProjectsRepository extends JpaRepository<ProgramProjects, Integer> {
    @Query(value = """
            SELECT
                pr.id_programa AS programId,
                pr.nombre AS programName,
                pr.descripcion AS programDescription,
                pr.alcance AS programScope,
                pr.estado AS programStatus,
                pr.responsable AS programResponsible,
                pr.fecha_creacion AS createdAt,
                pr.fecha_actualizacion AS updatedAt,
                p.id_proyecto AS projectId,
                p.nombre AS projectName,
                p.codigo AS projectCode,
                p.tipo_proyecto AS projectType,
                p.periodo_ejecucion AS executionPeriod,
                p.presupuesto_estimado AS estimatedBudget,
                p.ubicacion_geografica AS geographicLocation,
                p.estado AS projectStatus
            FROM programas pr
            JOIN programa_proyectos pp ON pr.id_programa = pp.id_programa
            JOIN proyectos p ON pp.id_proyecto = p.id_proyecto
            WHERE pr.id_programa = :programId
            ORDER BY p.nombre ASC
            """, nativeQuery = true)
    List<ProgramProjectView> findProgramWithProjectsByProgramId(@Param("programId") Integer programId);

}