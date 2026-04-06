package eci.edu.zwing.modules.sound.infrastructure.persistence.entity;

import eci.edu.zwing.modules.sound.domain.model.SoundCategory;
import jakarta.persistence.*;
import lombok.Data;
import jakarta.persistence.Id;


import java.util.UUID;

@Entity
@Data
@Table(name = "sound_presets")
public class SoundPresetEntity {

    @Id
    private UUID soundId;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SoundCategory category;

    @Column(nullable = false)
    private String blobUrl;

    private String description;
}