package pl.goral.psychotherapistofficerest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.goral.psychotherapistofficerest.dto.response.AppointmentResponseDto;
import pl.goral.psychotherapistofficerest.dto.request.AppointmentRequestDto;
import pl.goral.psychotherapistofficerest.service.AppointmentService;
import pl.goral.psychotherapistofficerest.model.Appointment;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/appointments")
@Tag(name = "Appointments", description = "Endpoints for managing appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    @Operation(summary = "Create a new appointment", description = "Creates a new appointment with selected patient, therapy, calendar slot and status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Appointment created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid data provided"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<AppointmentResponseDto> createAppointment(@RequestBody AppointmentRequestDto request) {
        Appointment appointment = appointmentService.createAppointment(
                request.getPatientId(),
                request.getTherapyId(),
                request.getCalenderSlotId(),
                request.getStatus()
        );
        AppointmentResponseDto response = AppointmentResponseDto.builder()
                .id(appointment.getId())
                .patientId(appointment.getPatient().getId())
                .patientName(appointment.getPatient().getName())
                .patientSurname(appointment.getPatient().getSurname())
                .therapyId(appointment.getTherapy().getId())
                .therapyKind(appointment.getTherapy().getKindOfTherapy())
                .calenderId(appointment.getCalenderSlot().getId())
                .date(appointment.getCalenderSlot().getDate())
                .time(appointment.getCalenderSlot().getTime())
                .status(appointment.getStatus())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Get all appointments", description = "Returns a list of all appointments")
    public ResponseEntity<List<AppointmentResponseDto>> getAllAppointments() {
        return ResponseEntity.ok(appointmentService.findAllAppointments());
    }

    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get appointments by patient", description = "Returns appointments for a specific patient")
    public ResponseEntity<List<AppointmentResponseDto>> getByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(appointmentService.findByPatientId(patientId));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get appointments by status", description = "Returns appointments filtered by status")
    public ResponseEntity<List<AppointmentResponseDto>> getByStatus(@PathVariable String status) {
        return ResponseEntity.ok(appointmentService.findByStatus(status));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete appointment", description = "Deletes an appointment by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Appointment deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Appointment not found")
    })
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }
}