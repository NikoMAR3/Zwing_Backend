package eci.edu.zwing.modules.sound.infrastructure.persistence.repository;

import eci.edu.zwing.modules.sound.application.port.out.SoundRepositoryPort;
import eci.edu.zwing.modules.sound.domain.model.SoundCategory;
import eci.edu.zwing.modules.sound.domain.model.SoundPreset;
import eci.edu.zwing.modules.sound.infrastructure.persistence.postgre.SoundRepository;
import eci.edu.zwing.modules.sound.infrastructure.persistence.repository.mapper.SoundPresetMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class SoundRepositoryAdapter implements SoundRepositoryPort {
    private final SoundRepository jpaRepository;
    private final SoundPresetMapper mapper;

    @Override
    public List<SoundPreset> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<SoundPreset> findByCategory(SoundCategory category) {
        return jpaRepository.findByCategory(category).stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<SoundPreset> findById(UUID soundId) {
        return jpaRepository.findById(soundId).map(mapper::toDomain);
    }

    @Override
    public SoundPreset save(SoundPreset soundPreset) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(soundPreset)));
    }
}