package eci.edu.zwing.modules.channelrack.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Channel {
    private String channelId;
    private String name;
    private final Step[] steps = new Step[16];
    private String sampleId;
    private Float volume;
    private boolean mute;

    void activateStep(int position){
        steps[position].activate();
    }
    void deactivateStep(int position){
        steps[position].deactivate();
    }

    public Step[] getSteps() {
        return steps;
    }
}
