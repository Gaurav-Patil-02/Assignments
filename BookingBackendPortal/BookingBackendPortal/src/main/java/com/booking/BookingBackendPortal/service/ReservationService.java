package com.booking.BookingBackendPortal.service;

import com.booking.BookingBackendPortal.dto.ReservationRequest;
import com.booking.BookingBackendPortal.entity.*;
import com.booking.BookingBackendPortal.repository.ReservationRepository;
import com.booking.BookingBackendPortal.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private UserService userService;

    public Page<Reservation> getAllReservations(ReservationStatus status, BigDecimal minPrice,
                                                BigDecimal maxPrice, Pageable pageable, User currentUser) {
        if (currentUser.getRoles().contains(Role.ROLE_ADMIN)) {
            return reservationRepository.findByFilters(status, minPrice, maxPrice, pageable);
        } else {
            return reservationRepository.findByUserAndFilters(currentUser, status, minPrice, maxPrice, pageable);
        }
    }

    public Optional<Reservation> getReservationById(Long id, User currentUser) {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        if (reservation.isPresent() &&
                !currentUser.getRoles().contains(Role.ROLE_ADMIN) &&
                !reservation.get().getUser().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("Access denied");
        }
        return reservation;
    }

    public Reservation createReservation(ReservationRequest request, User currentUser) {
        Resource resource = resourceRepository.findById(request.getResourceId())
                .orElseThrow(() -> new RuntimeException("Resource not found with id: " + request.getResourceId()));

        // Check for overlapping reservations (optional feature)
        if (hasOverlappingReservations(resource.getId(), request.getStartTime(), request.getEndTime())) {
            throw new RuntimeException("Resource is already booked for the selected time period");
        }

        Reservation reservation = new Reservation();
        reservation.setResource(resource);
        reservation.setUser(currentUser);
        reservation.setStatus(ReservationStatus.PENDING);
        reservation.setPrice(request.getPrice());
        reservation.setStartTime(request.getStartTime());
        reservation.setEndTime(request.getEndTime());

        return reservationRepository.save(reservation);
    }

    public Reservation updateReservation(Long id, ReservationRequest request, User currentUser) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + id));

        if (!currentUser.getRoles().contains(Role.ROLE_ADMIN) &&
                !reservation.getUser().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("Access denied");
        }

        Resource resource = resourceRepository.findById(request.getResourceId())
                .orElseThrow(() -> new RuntimeException("Resource not found with id: " + request.getResourceId()));

        // Check for overlapping reservations excluding current reservation
        if (hasOverlappingReservations(resource.getId(), request.getStartTime(), request.getEndTime(), id)) {
            throw new RuntimeException("Resource is already booked for the selected time period");
        }

        reservation.setResource(resource);
        reservation.setPrice(request.getPrice());
        reservation.setStartTime(request.getStartTime());
        reservation.setEndTime(request.getEndTime());

        return reservationRepository.save(reservation);
    }

    public void deleteReservation(Long id, User currentUser) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + id));

        if (!currentUser.getRoles().contains(Role.ROLE_ADMIN) &&
                !reservation.getUser().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("Access denied");
        }

        reservationRepository.delete(reservation);
    }

    public Reservation updateReservationStatus(Long id, ReservationStatus status, User currentUser) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + id));

        if (!currentUser.getRoles().contains(Role.ROLE_ADMIN)) {
            throw new AccessDeniedException("Only admins can update reservation status");
        }

        reservation.setStatus(status);
        return reservationRepository.save(reservation);
    }

    private boolean hasOverlappingReservations(Long resourceId, Instant startTime, Instant endTime) {
        return hasOverlappingReservations(resourceId, startTime, endTime, null);
    }

    private boolean hasOverlappingReservations(Long resourceId, Instant startTime, Instant endTime, Long excludeReservationId) {
        List<Reservation> overlapping = reservationRepository.findByResourceIdAndStatusAndStartTimeLessThanAndEndTimeGreaterThan(
                resourceId, ReservationStatus.CONFIRMED, endTime, startTime);

        if (excludeReservationId != null) {
            overlapping.removeIf(r -> r.getId().equals(excludeReservationId));
        }

        return !overlapping.isEmpty();
    }
}