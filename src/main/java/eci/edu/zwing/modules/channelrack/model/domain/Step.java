package eci.edu.zwing.modules.channelrack.model.domain;

public class Step {
    private boolean active = false;
    private float velocity;

    public void activate(){active = true;}
    public void deactivate(){active = false;}
}
