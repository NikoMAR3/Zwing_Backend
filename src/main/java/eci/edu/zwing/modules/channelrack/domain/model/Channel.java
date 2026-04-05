package eci.edu.zwing.modules.channelrack.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Channel {
    private String channelId;
    private String name;
    private Step[] steps = new Step[16];
    private String sampleId;
    private Float volume;
    private boolean mute;

    public Channel(String channelId,String name, String sampleId,Float volume, boolean mute){
        this.channelId = channelId;
        this.name = name;
        this.sampleId = sampleId;
        this.volume = volume;
        this.mute = mute;
    }

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
