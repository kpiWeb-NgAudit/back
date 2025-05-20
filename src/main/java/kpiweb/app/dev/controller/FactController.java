package kpiweb.app.dev.controller; // Adaptez votre package

import kpiweb.app.dev.dto.FactCreateDTO;
import kpiweb.app.dev.dto.FactResponseDTO;
import kpiweb.app.dev.dto.FactUpdateDTO;
import kpiweb.app.dev.entity.Fact;

import kpiweb.app.dev.service.FactService;
import kpiweb.app.dev.exception.ResourceNotFoundException;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/facts")
@CrossOrigin(origins = "http://localhost:5175")
public class FactController {

    private final FactService factService;

    @Autowired
    public FactController(FactService factService) {
        this.factService = factService;
    }

    // --- Méthode de mapping Entité vers DTO
    private FactResponseDTO convertToResponseDTO(Fact fact) {
        if (fact == null) {
            return null;
        }
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

        if (fact.getFactPartitiontype() != null) {
            dto.setFactPartitiontype(fact.getFactPartitiontype().name());
        }

        dto.setFactTimestamp(fact.getFactTimestamp()); // byte[] sera probablement sérialisé en Base64 String
        return dto;
    }
    // --- Fin de la méthode de mapping ---


    // Créer un nouveau Fact
    // POST /api/facts
    @PostMapping
    public ResponseEntity<FactResponseDTO> createFact(@Valid @RequestBody FactCreateDTO factCreateDTO) {
        Fact createdFactEntity = factService.createFact(factCreateDTO); // Le service prend le DTO
        FactResponseDTO responseDTO = convertToResponseDTO(createdFactEntity);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseDTO.getFactIdPk())
                .toUri();

        return ResponseEntity.created(location).body(responseDTO);
    }

    // Lire un Fact par ID
    // GET /api/facts/{id}
    @GetMapping("/{id}")
    public ResponseEntity<FactResponseDTO> getFactById(@PathVariable(value = "id") Integer factId) {
        Fact factEntity = factService.getFactById(factId)
                .orElseThrow(() -> new ResourceNotFoundException("Fact", "id", factId));
        return ResponseEntity.ok(convertToResponseDTO(factEntity));
    }

    // Lire tous les Facts
    // GET /api/facts
    @GetMapping
    public ResponseEntity<List<FactResponseDTO>> getAllFacts() {
        List<Fact> factEntities = factService.getAllFacts();
        if (factEntities.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<FactResponseDTO> responseDTOs = factEntities.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }

    // Mettre à jour un Fact
    // PUT /api/facts/{id}
    @PutMapping("/{id}")
    public ResponseEntity<FactResponseDTO> updateFact(@PathVariable(value = "id") Integer factId,
                                                      @Valid @RequestBody FactUpdateDTO factUpdateDTO) {
        Fact updatedFactEntity = factService.updateFact(factId, factUpdateDTO); // Le service prend le DTO
        return ResponseEntity.ok(convertToResponseDTO(updatedFactEntity));
    }

    // Supprimer un Fact
    // DELETE /api/facts/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFact(@PathVariable(value = "id") Integer factId) {
        factService.deleteFact(factId); // Le service gère ResourceNotFoundException
        return ResponseEntity.noContent().build();
    }
}