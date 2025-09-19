package com.booking.BookingBackendPortal.service;

import com.booking.BookingBackendPortal.entity.Resource;
import com.booking.BookingBackendPortal.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ResourceService {
    @Autowired
    private ResourceRepository resourceRepository;

    public Page<Resource> getAllResources(Pageable pageable) {
        return resourceRepository.findByActiveTrue(pageable);
    }

    public Optional<Resource> getResourceById(Long id) {
        return resourceRepository.findById(id);
    }

    public Resource createResource(Resource resource) {
        return resourceRepository.save(resource);
    }

    public Resource updateResource(Long id, Resource resourceDetails) {
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resource not found with id: " + id));

        resource.setName(resourceDetails.getName());
        resource.setType(resourceDetails.getType());
        resource.setDescription(resourceDetails.getDescription());
        resource.setCapacity(resourceDetails.getCapacity());
        resource.setActive(resourceDetails.getActive());

        return resourceRepository.save(resource);
    }

    public void deleteResource(Long id) {
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resource not found with id: " + id));

        resource.setActive(false);
        resourceRepository.save(resource);
    }
}