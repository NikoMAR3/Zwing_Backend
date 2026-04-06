package eci.edu.zwing.modules.sound.application.service;

import eci.edu.zwing.modules.sound.application.port.in.BrowseSoundsCase;
import eci.edu.zwing.modules.sound.application.port.out.SoundRepositoryPort;
import eci.edu.zwing.modules.sound.domain.model.SoundCategory;
import eci.edu.zwing.modules.sound.domain.model.SoundPreset;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SoundService implements BrowseSoundsCase {
    private final SoundRepositoryPort soundRepository;

    @Override
    public List<SoundPreset> getAllSounds() {
        return soundRepository.findAll();
    }

    @Override
    public List<SoundPreset> getSoundsByCategory(SoundCategory category) {
        return soundRepository.findByCategory(category);
    }

    @Override
    public SoundPreset getSoundById(UUID soundId) {
        return soundRepository.findById(soundId).orElseThrow(() -> new RuntimeException("Sound not found: " + soundId));
    }
}