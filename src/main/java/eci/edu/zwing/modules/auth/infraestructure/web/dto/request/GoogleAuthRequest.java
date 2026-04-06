package eci.edu.zwing.modules.auth.infraestructure.web.dto.request;

import lombok.Data;

@Data
public class GoogleAuthRequest {
    private String idToken;
}