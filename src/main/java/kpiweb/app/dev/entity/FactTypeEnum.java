package kpiweb.app.dev.entity;

public enum FactTypeEnum {
    FTSTD,      // Standard Fact Table
    FTNOTIME,   // Fact Table without Time Dimension link (special processing)
    FTBUDOBJ,   // Budget/Objective Fact Table
    FTWRBACK    // Write-Back enabled Fact Table
}
