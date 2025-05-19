package kpiweb.app.dev.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for exposing only the necessary Customer data in the API.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDTO {
    private String cubeIdPk;
    private Integer cubeNumber;
    private String cubeName;
    private String custGeocode;
    private String custTown;
    private String custCountry;
    private String custCubetype;
    private String custLanguage;
    private LocalDateTime cubeLastupdate;
    private LocalDateTime cubeLastprocess;

    // Operational settings (non-sensitive)
    private String custRefreshfrq;
    private String custCharseparator;
    private Integer custLimitrdlfilter;

    // Display fields
    private Boolean custShowfiscmeasureandset;
    private Boolean custShowpctdifferencebase100;
}