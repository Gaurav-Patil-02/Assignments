package com.booking.BookingBackendPortal.controller;

import com.booking.BookingBackendPortal.entity.Resource;
import com.booking.BookingBackendPortal.dto.ResourceRequest;
import com.booking.BookingBackendPortal.service.ResourceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/resources")
public class ResourceController {
    @Autowired
    private ResourceService resourceService;

    @GetMapping
    public ResponseEntity<Page<Resource>> getAllResources(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Resource> resources = resourceService.getAllResources(pageable);
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> getResourceById(@PathVariable Long id) {
        Optional<Resource> resource = resourceService.getResourceById(id);
        return resource.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Resource> createResource(@Valid @RequestBody ResourceRequest resourceRequest) {
        Resource resource = new Resource();
        resource.setName(resourceRequest.getName());
        resource.setType(resourceRequest.getType());
        resource.setDescription(resourceRequest.getDescription());
        resource.setCapacity(resourceRequest.getCapacity());

        Resource createdResource = resourceService.createResource(resource);
        return ResponseEntity.ok(createdResource);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Resource> updateResource(@PathVariable Long id, @Valid @RequestBody ResourceRequest resourceRequest) {
        Resource resourceDetails = new Resource();
        resourceDetails.setName(resourceRequest.getName());
        resourceDetails.setType(resourceRequest.getType());
        resourceDetails.setDescription(resourceRequest.getDescription());
        resourceDetails.setCapacity(resourceRequest.getCapacity());
        resourceDetails.setActive(true);

        Resource updatedResource = resourceService.updateResource(id, resourceDetails);
        return ResponseEntity.ok(updatedResource);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteResource(@PathVariable Long id) {
        resourceService.deleteResource(id);
        return ResponseEntity.ok().build();
    }
}