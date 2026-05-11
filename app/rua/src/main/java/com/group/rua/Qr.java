package com.group.rua;
import com.group.rua.enums.EstadoQR;
import java.time.LocalDateTime;
import java.time.Duration;

public class Qr {
    private Long idQR;
    private String token;
    private LocalDateTime fechaGeneracion;
    private Duration tiempoValidez;
    private EstadoQR estado;

    public boolean esValido() {
        // TODO - implement Qr.esValido
        throw new UnsupportedOperationException();
    }

    public void invalidar() {
        // TODO - implement Qr.invalidar
        throw new UnsupportedOperationException();
    }

    public void exponerQR() {
        // TODO - implement Qr.exponerQR
        throw new UnsupportedOperationException();
    }
}
