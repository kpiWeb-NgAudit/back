package kpiweb.app.dev.dto;

import kpiweb.app.dev.entity.Fact;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FactResponseDTO { //(pour GET /api/facts et GET /api/facts/{id}, et la réponse de POST/PUT)
    private Integer factIdPk;
    private String factTname;

    private String factType;
    private Integer factdbextrIdPk;
    private String factProccube;
    private String factShortcubename;
    private String factShortpresname;
    private Integer factWorkorder;
    private String customerCubeIdPk; // Juste l'ID du client lié

    private String factFactdatafiletype;
    private String factFactdatafilename;
    private Boolean factFactdatafilecheckunicity;

    private String factZonespe;
    private LocalDateTime factLastupdate;
    private String factComments;
    private String factPartitiontype;
    private byte[] factTimestamp; // Souvent envoyé en Base64 String par Jackson





    public static FactResponseDTO fromEntity(Fact fact) {
        FactResponseDTO dto = new FactResponseDTO();
        dto.setFactIdPk(fact.getFactIdPk());
        dto.setFactTname(fact.getFactTname());
        if (fact.getFactType() != null) {
            dto.setFactType(fact.getFactType().name());
        }
        dto.setFactdbextrIdPk(fact.getFactdbextrIdPk());
        dto.setFactProccube(fact.getFactProccube());
        dto.setFactShortcubename(fact.getFactShortcubename());
        dto.setFactShortpresname(fact.getFactShortpresname());
        dto.setFactWorkorder(fact.getFactWorkorder());
        if (fact.getCustomer() != null) {
            dto.setCustomerCubeIdPk(fact.getCustomer().getCubeIdPk());
        }
        if (fact.getFactFactdatafiletype() != null) {
            dto.setFactFactdatafiletype(fact.getFactFactdatafiletype().name());
        }
        dto.setFactFactdatafilename(fact.getFactFactdatafilename());
        dto.setFactFactdatafilecheckunicity(fact.getFactFactdatafilecheckunicity());
        if (fact.getFactZonespe() != null) {
            dto.setFactZonespe(fact.getFactZonespe().name());
        }
        dto.setFactLastupdate(fact.getFactLastupdate());
        dto.setFactComments(fact.getFactComments());
        dto.setFactPartitiontype(fact.getFactPartitiontype().name());
        dto.setFactTimestamp(fact.getFactTimestamp());
        return dto;
    }
}
