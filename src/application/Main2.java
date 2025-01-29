package application;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import db.Conexao;
import entities.Cliente;
import entities.Reserva;
import modelDaoImpl.ClienteDaoJDBC;
import modelDaoImpl.ReservaDaoJDBC;

public class Main2 {
    public static void main(String[] args) {
        
        Connection connection = Conexao.conectar();
        ReservaDaoJDBC reservaDaoJDBC = new ReservaDaoJDBC(connection);

        Cliente cliente = new Cliente("Tiago", "tiago2@gmail.com", "91508304");
        ClienteDaoJDBC clienteDaoJDBC = new ClienteDaoJDBC(connection);
        clienteDaoJDBC.adicionarCliente(cliente);

        Reserva reserva = new Reserva(cliente, 5, 
        LocalDate.of(2025, 1, 22), LocalTime.of(19, 30));
        
        reservaDaoJDBC.adicionarReserva(reserva);
        System.out.println("Reserva adicionada com sucesso!");
        
        Reserva reserva2 = reservaDaoJDBC.findByMesaId(5);
        System.out.println(reserva2);

        
       List<Reserva> reservas = reservaDaoJDBC.listaReservas();
       for(Reserva r : reservas){
        System.out.println(r);
       }

       reservaDaoJDBC.removerReserva(7);
       System.out.println("Reserva cancelada com sucesso!");

    }
}
