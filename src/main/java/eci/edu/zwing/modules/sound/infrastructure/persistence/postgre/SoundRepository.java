package eci.edu.zwing.modules.sound.infrastructure.persistence.postgre;


import eci.edu.zwing.modules.sound.domain.model.SoundCategory;
import eci.edu.zwing.modules.sound.infrastructure.persistence.entity.SoundPresetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SoundRepository extends JpaRepository<SoundPresetEntity, UUID> {
    List<SoundPresetEntity> findByCategory(SoundCategory category);
}