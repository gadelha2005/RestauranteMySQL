package entities;

import java.time.LocalDate;

public class Reserva {
    private Cliente cliente;
    private int mesaId;
    private LocalDate dataReserva;

    public Reserva(){

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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
        result = prime * result + mesaId;
        result = prime * result + ((dataReserva == null) ? 0 : dataReserva.hashCode());
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
        return true;
    }

    @Override
    public String toString() {
        return "Reserva [cliente=" + cliente + ", mesaId=" + mesaId + ", dataReserva=" + dataReserva + "]";
    }

    
}
