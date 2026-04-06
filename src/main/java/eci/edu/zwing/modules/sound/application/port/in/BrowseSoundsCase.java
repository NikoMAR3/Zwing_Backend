package eci.edu.zwing.modules.sound.application.port.in;



import eci.edu.zwing.modules.sound.domain.model.SoundCategory;
import eci.edu.zwing.modules.sound.domain.model.SoundPreset;

import java.util.List;
import java.util.UUID;

public interface BrowseSoundsCase {
    List<SoundPreset> getAllSounds();
    List<SoundPreset> getSoundsByCategory(SoundCategory category);
    SoundPreset getSoundById(UUID soundId);
}