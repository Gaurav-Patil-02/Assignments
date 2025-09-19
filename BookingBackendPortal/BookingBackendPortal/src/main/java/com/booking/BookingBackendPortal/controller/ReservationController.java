package com.booking.BookingBackendPortal.controller;

import com.booking.BookingBackendPortal.dto.ReservationRequest;
import com.booking.BookingBackendPortal.entity.Reservation;
import com.booking.BookingBackendPortal.entity.ReservationStatus;
import com.booking.BookingBackendPortal.entity.User;
import com.booking.BookingBackendPortal.service.ReservationService;
import com.booking.BookingBackendPortal.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/reservations")
public class ReservationController 
{
    @Autowired
    private ReservationService reservationService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Page<Reservation>> getAllReservations(
            @RequestParam(required = false) ReservationStatus status,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        User currentUser = userService.getCurrentUserOrThrow();
        Page<Reservation> reservations = reservationService.getAllReservations(
                status, minPrice, maxPrice, pageable, currentUser);

        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        User currentUser = userService.getCurrentUserOrThrow();
        Optional<Reservation> reservation = reservationService.getReservationById(id, currentUser);
        return reservation.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Reservation> createReservation(@Valid @RequestBody ReservationRequest reservationRequest) {
        User currentUser = userService.getCurrentUserOrThrow();
        Reservation reservation = reservationService.createReservation(reservationRequest, currentUser);
        return ResponseEntity.ok(reservation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Long id,
                                                         @Valid @RequestBody ReservationRequest reservationRequest) {
        User currentUser = userService.getCurrentUserOrThrow();
        Reservation reservation = reservationService.updateReservation(id, reservationRequest, currentUser);
        return ResponseEntity.ok(reservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable Long id) {
        User currentUser = userService.getCurrentUserOrThrow();
        reservationService.deleteReservation(id, currentUser);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Reservation> updateReservationStatus(@PathVariable Long id,
                                                               @RequestParam ReservationStatus status) {
        User currentUser = userService.getCurrentUserOrThrow();
        Reservation reservation = reservationService.updateReservationStatus(id, status, currentUser);
        return ResponseEntity.ok(reservation);
    }
}