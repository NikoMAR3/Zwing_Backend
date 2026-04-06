package eci.edu.zwing.modules.auth.application.port.out;

public interface TokenBlacklistPort {
    void blacklistToken(String token, long durationSeconds);
    boolean isTokenBlacklisted(String token);
}
