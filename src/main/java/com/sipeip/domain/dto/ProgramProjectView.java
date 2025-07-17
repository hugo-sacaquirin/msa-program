package com.sipeip.domain.dto;

public interface ProgramProjectView {
    // Program
    Integer getProgramId();

    String getProgramName();

    String getProgramDescription();

    String getProgramScope();

    String getProgramStatus();

    String getProgramResponsible();

    java.time.LocalDateTime getCreatedAt();

    java.time.LocalDateTime getUpdatedAt();

    // Project
    Integer getProjectId();

    String getProjectName();

    String getProjectCode();

    String getProjectType();

    String getExecutionPeriod();

    java.math.BigDecimal getEstimatedBudget();

    String getGeographicLocation();

    String getProjectStatus();
}
