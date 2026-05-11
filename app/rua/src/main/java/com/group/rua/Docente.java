package com.group.rua;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class Docente extends Usuario {

    /**
     *
     * @param idBloque
     */
    public String generarQR(Long idBloque) {
        // TODO - implement Docente.generarQR
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param idBloque
     */
    public void anularBloque(Long idBloque) {
        // TODO - implement Docente.anularBloque
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param correo
     */
    public void registrarAsistenciaManual(String correo) {
        // TODO - implement Docente.registrarAsistenciaManual
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param idClase
     * @param dia
     * @param horaInicio
     * @param horaFin
     */
    public void agregarBloque(Long idClase, DayOfWeek dia, LocalDateTime horaInicio, LocalDateTime horaFin) {
        // TODO - implement Docente.agregarBloque
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param idBloque
     * @param nuevoDia
     * @param nuevaHora
     */
    public void clonarBloque(Long idBloque, DayOfWeek nuevoDia, LocalDateTime nuevaHora) {
        // TODO - implement Docente.clonarBloque
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param idBloque
     * @param nuevoDia
     * @param nuevaHora
     */
    public void moverBloque(Long idBloque, DayOfWeek nuevoDia, LocalDateTime nuevaHora) {
        // TODO - implement Docente.moverBloque
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param idBloque
     */
    public void removerBloque(Long idBloque) {
        // TODO - implement Docente.removerBloque
        throw new UnsupportedOperationException();
    }
}
