package com.example.react.controllers;

import com.example.react.models.Employee;
import com.example.react.repositories.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(EmployeeController.PATH)
//@Api(
//        value = "/" + EmployeeController.PATH,
//        description = "Get/Add/Edit/Delete Employee record"
//)
public class EmployeeController {

    //
    public static final String PATH = "/api/employees";
    private final Logger log = LoggerFactory.getLogger(getClass());
    //
    private final EmployeeRepository repository;

    @Autowired
    public EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }

    // Get all objects
    @GetMapping("")
    public Iterable<Employee> getAll() {
        return repository.findAll();
    }

    // Create a new object
    @PostMapping("")
    public Employee create(@Valid @RequestBody Employee dao) {
        return repository.save(dao);
    }

    // Get a single object
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getByPk(@PathVariable(value = "id") Long id) {
        Employee dao = repository.findOne(id);
        if (dao == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(dao);
    }

    // Update a single object
    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody Employee newData) {
        Employee dao = repository.findOne(id);
        if (dao == null) {
            return ResponseEntity.notFound().build();
        }
        //dao.copyDataFrom(newData);
        //
        Employee updated = repository.save(dao);
        return ResponseEntity.ok(updated);
    }

    // Delete a single object
    @DeleteMapping("/{id}")
    public ResponseEntity<Employee> delete(
            @PathVariable(value = "id") Long id) {
        Employee dao = repository.findOne(id);
        if (dao == null) {
            return ResponseEntity.notFound().build();
        }
        repository.delete(dao);
        return ResponseEntity.ok().build();
    }
}
