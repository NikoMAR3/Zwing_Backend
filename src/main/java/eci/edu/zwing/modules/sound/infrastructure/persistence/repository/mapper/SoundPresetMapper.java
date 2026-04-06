package eci.edu.zwing.modules.sound.infrastructure.persistence.repository.mapper;


import eci.edu.zwing.modules.sound.domain.model.SoundPreset;
import eci.edu.zwing.modules.sound.infrastructure.persistence.entity.SoundPresetEntity;
import org.springframework.stereotype.Component;

@Component
public class SoundPresetMapper {
    public SoundPreset toDomain(SoundPresetEntity entity) {
        return new SoundPreset(
                entity.getSoundId(),
                entity.getName(),
                entity.getCategory(),
                entity.getBlobUrl(),
                entity.getDescription()
        );
    }

    public SoundPresetEntity toEntity(SoundPreset domain) {
        SoundPresetEntity entity = new SoundPresetEntity();
        entity.setSoundId(domain.getSoundId());
        entity.setName(domain.getName());
        entity.setCategory(domain.getCategory());
        entity.setBlobUrl(domain.getBlobUrl());
        entity.setDescription(domain.getDescription());
        return entity;
    }
}