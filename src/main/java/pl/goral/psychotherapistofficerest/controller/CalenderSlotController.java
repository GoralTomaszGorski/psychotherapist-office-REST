package pl.goral.psychotherapistofficerest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.goral.psychotherapistofficerest.dto.CalenderSlotDto;
import pl.goral.psychotherapistofficerest.service.CalenderSlotService;

@Tag(name = "CalenderSlots", description = "Endpoints for managing calendar slots (day/time/availability)")
@RestController
@RequestMapping("/api/calender-slots")
@RequiredArgsConstructor
public class CalenderSlotController {

    private final CalenderSlotService calenderSlotService;

    @GetMapping("/{id}")
    @Operation(summary = "Get slot by ID", description = "Returns a calendar slot by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Slot found and returned"),
            @ApiResponse(responseCode = "404", description = "Slot not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<CalenderSlotDto> getSlotById(@PathVariable Long id) {
        return ResponseEntity.ok(calenderSlotService.getSlot(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update slot day/time/availability", description = "Updates slot's day, time and availability by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Slot updated successfully"),
            @ApiResponse(responseCode = "404", description = "Slot not found"),
            @ApiResponse(responseCode = "400", description = "Invalid data provided"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<CalenderSlotDto> updateSlot(
            @PathVariable Long id,
            @RequestBody CalenderSlotDto slotDto
    ) {
        CalenderSlotDto updated = calenderSlotService.setSlot(id, slotDto.getDayOf(), slotDto.getTime(), slotDto.isFree());
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete slot by ID", description = "Deletes a calendar slot by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Slot deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Slot not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteSlot(@PathVariable Long id) {
        calenderSlotService.deleteSlot(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    @Operation(summary = "Delete all slots", description = "Deletes all calendar slots")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "All slots deleted successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteAllSlots() {
        calenderSlotService.deleteAllSlots();
        return ResponseEntity.noContent().build();
    }
}