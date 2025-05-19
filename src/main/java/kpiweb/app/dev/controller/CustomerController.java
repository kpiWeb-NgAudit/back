package kpiweb.app.dev.controller;


import kpiweb.app.dev.dto.CustomerDTO;
import kpiweb.app.dev.entity.Customer;
import kpiweb.app.dev.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService service;

    @GetMapping
    public List<CustomerDTO> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public CustomerDTO getOne(@PathVariable String id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO create(@RequestBody @Valid CustomerDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public CustomerDTO update(@PathVariable String id,
                              @RequestBody @Valid CustomerDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        service.delete(id);
    }
}
