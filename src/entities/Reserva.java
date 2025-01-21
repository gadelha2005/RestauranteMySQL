package entities;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reserva {
    private Cliente cliente;
    private int mesaId;
    private LocalDate dataReserva;
    private LocalTime horaReserva;

    public Reserva() {
    }

    public Reserva(Cliente cliente, int mesaId, LocalDate dataReserva, LocalTime horaReserva) {
        this.cliente = cliente;
        this.mesaId = mesaId;
        this.dataReserva = dataReserva;
        this.horaReserva = horaReserva;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public int getMesaId() {
        return mesaId;
    }

    public void setMesaId(int mesaId) {
        this.mesaId = mesaId;
    }

    public LocalDate getDataReserva() {
        return dataReserva;
    }

    public void setDataReserva(LocalDate dataReserva) {
        this.dataReserva = dataReserva;
    }

    public LocalTime getHoraReserva() {
        return horaReserva;
    }

    public void setHoraReserva(LocalTime horaReserva) {
        this.horaReserva = horaReserva;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
        result = prime * result + mesaId;
        result = prime * result + ((dataReserva == null) ? 0 : dataReserva.hashCode());
        result = prime * result + ((horaReserva == null) ? 0 : horaReserva.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Reserva other = (Reserva) obj;
        if (cliente == null) {
            if (other.cliente != null)
                return false;
        } else if (!cliente.equals(other.cliente))
            return false;
        if (mesaId != other.mesaId)
            return false;
        if (dataReserva == null) {
            if (other.dataReserva != null)
                return false;
        } else if (!dataReserva.equals(other.dataReserva))
            return false;
        if (horaReserva == null) {
            if (other.horaReserva != null)
                return false;
        } else if (!horaReserva.equals(other.horaReserva))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Reserva [cliente=" + cliente + ", mesaId=" + mesaId + ", dataReserva=" + dataReserva + ", horaReserva=" + horaReserva + "]";
    }
}
