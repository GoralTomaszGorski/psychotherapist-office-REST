package pl.goral.psychotherapistofficerest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.goral.psychotherapistofficerest.dto.TherapyDto;
import pl.goral.psychotherapistofficerest.service.TherapyService;

import java.util.List;

@Tag(name = "Therapies", description = "Endpoints for managing therapies")
@RestController
@RequestMapping("/api/therapies")
@RequiredArgsConstructor
public class TherapyController {

    private final TherapyService therapyService;

    @PostMapping
    @Operation(summary = "Add a new therapy", description = "Creates a new therapy with specified details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Therapy created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid data provided"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<TherapyDto> addTherapy(@RequestBody TherapyDto therapyDto) {
        return ResponseEntity.ok(therapyService.addTherapy(therapyDto));
    }

    @PutMapping("/name/{therapyName}")
    @Operation(summary = "Update therapy by name", description = "Updates an existing therapy identified by its name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Therapy updated successfully"),
            @ApiResponse(responseCode = "404", description = "Therapy not found"),
            @ApiResponse(responseCode = "400", description = "Invalid data provided"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<TherapyDto> updateTherapyByName(@PathVariable String therapyName, @RequestBody TherapyDto therapyDto) {
        return ResponseEntity.ok(therapyService.updateTherapy(therapyName, therapyDto));
    }

    @GetMapping
    @Operation(summary = "Get all therapies", description = "Returns a list of all therapies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Therapies retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<TherapyDto>> getAllTherapies() {
        return ResponseEntity.ok(therapyService.getAllTherapies());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get therapy by ID", description = "Returns a therapy by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Therapy found and returned"),
            @ApiResponse(responseCode = "404", description = "Therapy not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<TherapyDto> getTherapyById(@PathVariable Long id) {
        return ResponseEntity.ok(therapyService.getTherapyById(id));
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Delete therapy by ID", description = "Deletes a therapy by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Therapy deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Therapy not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
        public ResponseEntity<Void> deleteTherapyById(@PathVariable Long id) {
        therapyService.deleteTherapyById(id);
        return ResponseEntity.noContent().build();
    }
}