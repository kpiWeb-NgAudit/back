package kpiweb.app.dev.controller;

import jakarta.validation.Valid;
import kpiweb.app.dev.entity.Fact;
import kpiweb.app.dev.exception.ResourceNotFoundException;
import kpiweb.app.dev.service.FactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facts") // Base path for all endpoints in this controller
public class FactController {

    private final FactService factService;

    @Autowired
    public FactController(FactService factService) {
        this.factService = factService;
    }

    // Create a new Fact
    // POST /api/facts
    @PostMapping
    public ResponseEntity<Fact> createFact(@Valid @RequestBody Fact fact) {
        Fact createdFact = factService.createFact(fact);
        return new ResponseEntity<>(createdFact, HttpStatus.CREATED);
    }

    // Get a single Fact by ID
    // GET /api/facts/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Fact> getFactById(@PathVariable(value = "id") Integer factId) {
        Fact fact = factService.getFactById(factId)
                .orElseThrow(() -> new ResourceNotFoundException("Fact", "id", factId));
        return ResponseEntity.ok().body(fact);
    }

    // Get all Facts
    // GET /api/facts
    @GetMapping
    public ResponseEntity<List<Fact>> getAllFacts() {
        List<Fact> facts = factService.getAllFacts();
        if (facts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(facts);
    }

    // Update a Fact
    // PUT /api/facts/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Fact> updateFact(@PathVariable(value = "id") Integer factId,
                                           @Valid @RequestBody Fact factDetails) {
        Fact updatedFact = factService.updateFact(factId, factDetails);
        return ResponseEntity.ok(updatedFact);
    }

    // Delete a Fact
    // DELETE /api/facts/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFact(@PathVariable(value = "id") Integer factId) {
        factService.deleteFact(factId); // Service method handles ResourceNotFoundException
        return ResponseEntity.noContent().build(); // Or ResponseEntity.ok().build() if preferred
    }
}
