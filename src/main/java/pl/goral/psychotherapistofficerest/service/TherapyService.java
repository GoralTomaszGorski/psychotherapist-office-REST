package pl.goral.psychotherapistofficerest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.goral.psychotherapistofficerest.dto.TherapyDto;
import pl.goral.psychotherapistofficerest.mapper.TherapyMapper;
import pl.goral.psychotherapistofficerest.model.Therapy;
import pl.goral.psychotherapistofficerest.repository.TherapyRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TherapyService {

    private final TherapyRepository therapyRepository;
    private final TherapyMapper therapyMapper;

    public TherapyDto addTherapy(TherapyDto dto) {
        Therapy therapy = therapyMapper.toEntity(dto);
        return therapyMapper.toDto(therapyRepository.save(therapy));
    }

    public TherapyDto updateTherapy(String therapyName, TherapyDto dto) {
        Therapy therapy = therapyRepository.findByKindOfTherapy(therapyName)
                .orElseThrow(() -> new RuntimeException("Therapy not found"));
        therapy.setKindOfTherapy(dto.getKindOfTherapy());
        therapy.setDescription(dto.getDescription());
        therapy.setPrice(dto.getPrice());
        return therapyMapper.toDto(therapyRepository.save(therapy));
    }

    public List<TherapyDto> getAllTherapies() {
        return therapyRepository.findAll().stream()
                .map(therapyMapper::toDto)
                .collect(Collectors.toList());
    }

    public TherapyDto getTherapyByName(String therapyName) {
        Therapy therapy = therapyRepository.findByKindOfTherapy(therapyName)
                .orElseThrow(() -> new RuntimeException("Therapy not found"));
        return therapyMapper.toDto(therapy);
    }

    public TherapyDto findTherapyById(Long id) {
        Therapy therapy = therapyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Therapy not found"));
                return therapyMapper.toDto(therapy);
    }

    public void deleteTherapyById(Long id) {
        if (!therapyRepository.existsById(id)) {
            throw new RuntimeException("Therapy not found");
        }
        therapyRepository.deleteById(id);
    }

    public Therapy getTherapyById(Long id){
        return therapyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Therapy not found"));
    }
}