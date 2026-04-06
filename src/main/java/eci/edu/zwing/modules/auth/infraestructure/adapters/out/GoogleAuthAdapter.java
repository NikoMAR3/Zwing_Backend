package eci.edu.zwing.modules.auth.infraestructure.adapters.out;


import eci.edu.zwing.modules.auth.application.port.out.GoogleAuthPort;
import eci.edu.zwing.modules.auth.domain.model.GoogleUserData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class GoogleAuthAdapter implements GoogleAuthPort {
    private final GoogleIdTokenVerifier verifier;

    public GoogleAuthAdapter(@Value("${google.client.id}") String clientId) {
        this.verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Collections.singletonList(clientId))
                .build();
    }

    @Override
    public GoogleUserData verifyToken(String googleToken) {
        try {
            GoogleIdToken idToken = verifier.verify(googleToken);
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();
                return new GoogleUserData(payload.getSubject(), payload.getEmail(), (String) payload.get("name"), (String) payload.get("picture"));
            } else {
                throw new RuntimeException("Invalid Google ID token.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error verifying token", e);
        }
    }
}
