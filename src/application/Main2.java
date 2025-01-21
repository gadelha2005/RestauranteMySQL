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

        Cliente cliente = new Cliente("Tiago", "tiago@gmail.com", "91508304");
        ClienteDaoJDBC clienteDaoJDBC = new ClienteDaoJDBC(connection);
       

        Reserva reserva = new Reserva(cliente, 7, LocalDate.of(2025, 1, 22), LocalTime.of(19, 30));
        reservaDaoJDBC.atualizarReserva(reserva);
        System.out.println("Reserva modificada com sucesso!");

    }
}
