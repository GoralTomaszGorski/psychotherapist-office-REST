package pl.goral.psychotherapistofficerest.mapper;

import org.springframework.stereotype.Component;
import pl.goral.psychotherapistofficerest.dto.CalenderSlotDto;
import pl.goral.psychotherapistofficerest.dto.TherapyDto;
import pl.goral.psychotherapistofficerest.dto.request.AppointmentRequestDto;
import pl.goral.psychotherapistofficerest.dto.response.AppointmentResponseDto;
import pl.goral.psychotherapistofficerest.dto.response.PatientResponseDto;
import pl.goral.psychotherapistofficerest.model.Appointment;
import pl.goral.psychotherapistofficerest.model.CalenderSlot;
import pl.goral.psychotherapistofficerest.model.Patient;
import pl.goral.psychotherapistofficerest.model.Therapy;

@Component

public class AppointmentDtoMapper {

    public Appointment toEntity(
            AppointmentRequestDto dto,
            Patient patient,
            Therapy therapy,
            CalenderSlot calenderSlot
    ) {
        if (dto == null) return null;
        return Appointment.builder()
                .id(dto.getId())
                .patient(patient)
                .therapy(therapy)
                .calenderSlot(calenderSlot)
                .status(dto.getStatus())
                .build();
    }


    public static AppointmentResponseDto toDto(Appointment appointment) {
        return AppointmentResponseDto.builder()
                .id(appointment.getId())
                .patientId(appointment.getPatient().getId())
                .patientName(appointment.getPatient().getName())
                .patientSurname(appointment.getPatient().getSurname())
                .therapyId(appointment.getTherapy().getId())
                .therapyKind(appointment.getTherapy().getKindOfTherapy())
                .calenderId(appointment.getCalenderSlot().getId())
                .time(appointment.getCalenderSlot().getTime())
                .date(appointment.getCalenderSlot().getDate())
                .status(appointment.getStatus())
                .build();
    }


}
