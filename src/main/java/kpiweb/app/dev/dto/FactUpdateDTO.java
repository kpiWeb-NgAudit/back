package kpiweb.app.dev.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import kpiweb.app.dev.entity.FactFactDataFileTypeEnum;
import kpiweb.app.dev.entity.FactPartitionTypeEnum;
import kpiweb.app.dev.entity.FactTypeEnum;
import kpiweb.app.dev.entity.FactZoneSpeEnum;
import lombok.Data;

@Data
public class FactUpdateDTO {
    @NotBlank(message = "Le nom technique du fait (factTname) ne peut pas être vide.")
    @Size(max = 20, message = "factTname ne doit pas dépasser 20 caractères.")
    private String factTname;

    @NotNull(message = "Le type du fait (factType) ne peut pas être nul.")
    private FactTypeEnum factType;

    private Integer factdbextrIdPk;

    @NotBlank
    @Size(max = 6)
    @Pattern(regexp = "FPROCY", message = "factProccube doit être 'FPROCY'.")
    private String factProccube;

    @NotBlank
    @Size(max = 20)
    private String factShortcubename;

    @NotBlank
    @Size(max = 30)
    private String factShortpresname;

    @NotNull
    private Integer factWorkorder;

    @NotBlank(message = "L'ID du client (customerCubeIdPk) doit être fourni.")
    @Size(max = 15)
    private String customerCubeIdPk;

    private FactFactDataFileTypeEnum factFactdatafiletype;

    @Size(max = 50)
    private String factFactdatafilename;

    private Boolean factFactdatafilecheckunicity;

    @NotNull(message = "La zone SPE (factZonespe) ne peut pas être nulle.")
    private FactZoneSpeEnum factZonespe;
    @Size(max = 2147483647)
    private String factComments;

    @NotNull(message = "Le type de partition (factPartitiontype) ne peut être nul.")
    private FactPartitionTypeEnum factPartitiontype;
}
