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

       List<Reserva> reservas = reservaDaoJDBC.listaReservas();
       for(Reserva reserva : reservas){
        System.out.println(reserva);
       }

    }
}
