package modelDaoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



import db.DbException;
import entities.Cliente;
import entities.Reserva;

public class ReservaDaoJDBC {
    
    private Connection connection;

    public ReservaDaoJDBC(Connection connection){
        this.connection = connection;
    }

    public void adicionarReserva(Reserva reserva) {
        if (isMesaReservada(reserva.getMesaId())) {
            throw new DbException("Erro: A mesa " + reserva.getMesaId() + " já está reservada.");
        }
    
        PreparedStatement st = null;
    
        try {
            st = connection.prepareStatement(
                "INSERT INTO reserva (cliente_id, mesa_id, data_reserva, hora_reserva) " +
                "VALUES (?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);
    
            st.setInt(1, reserva.getCliente().getId());
            st.setInt(2, reserva.getMesaId());
            st.setDate(3, java.sql.Date.valueOf(reserva.getDataReserva()));
            st.setTime(4, java.sql.Time.valueOf(reserva.getHoraReserva())); // Supondo que horaReserva seja um LocalTime
    
            int rowsAffected = st.executeUpdate();
    
            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    reserva.setMesaId(id);;
                }
                rs.close();
            } else {
                throw new DbException("Erro ao adicionar a reserva.");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    public void removerReserva(int mesaId){
        PreparedStatement st = null;

        try{
            st = connection.prepareStatement(
                "DELETE FROM reserva WHERE mesa_id = ?");

            st.setInt(1, mesaId);

            int rowsAffected = st.executeUpdate();

            if(rowsAffected == 0){
                throw new DbException("Reserva com o id " + mesaId + " não existe!");
            }

        }
        catch(SQLException e){
            throw new DbException(e.getMessage());
        }
        finally{
            try{
                if(st != null){
                    st.close();
                }
            }
            catch(SQLException e){
                throw new DbException(e.getMessage());
            }
        }
    }
    

    public Reserva findByMesaId(int mesaId) {
        PreparedStatement st = null;
        ResultSet rs = null;
    
        try {
           
            st = connection.prepareStatement(
                "SELECT cliente.id,cliente.nome, cliente.email, cliente.telefone, reserva.mesa_id, reserva.data_reserva, reserva.hora_reserva " +
                "FROM reserva " +
                "JOIN cliente ON reserva.cliente_id = cliente.id " +
                "WHERE reserva.mesa_id = ?");
            
            st.setInt(1, mesaId);
            rs = st.executeQuery();
    
           
            if (rs.next()) {
                Cliente cliente = instatieteCliente(rs);
                Reserva reserva = instantiateReserva(rs, cliente);
                
                return reserva;
            }
    
            return null; 
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    public void atualizarReserva(Reserva reserva){
        PreparedStatement st = null;

        try{
            st = connection.prepareStatement(
                "UPDATE reserva " +
                "SET cliente_id = ?, data_reserva = ?, hora_reserva = ? " +
                "WHERE mesa_id = ?");
            
            st.setInt(1, reserva.getCliente().getId());
            st.setDate(2, java.sql.Date.valueOf(reserva.getDataReserva()));
            st.setTime(3, java.sql.Time.valueOf(reserva.getHoraReserva()));
            st.setInt(4, reserva.getMesaId());

            int rowsAffected = st.executeUpdate();

            if(rowsAffected == 0){
                throw new DbException("Erro Inesperado!");
            }
        }
        catch(SQLException e){
            throw new DbException(e.getMessage());
        }
        finally{
            try{
                if(st != null){
                    st.close();
                }
            }
            catch(SQLException e){
                throw new DbException(e.getMessage());
            }
        }
    }

    
    public List<Reserva> listaReservas(){
        PreparedStatement st = null;
        ResultSet rs = null;

        List<Reserva> reservas = new ArrayList<>();

        try{
            st = connection.prepareStatement(
                "SELECT cliente.id, cliente.nome, cliente.email, cliente.telefone, reserva.mesa_id, reserva.data_reserva, reserva.hora_reserva " +
                "FROM reserva " +
                "JOIN cliente ON reserva.cliente_id = cliente.id");

            rs = st.executeQuery();

            while(rs.next()){
                Cliente cliente = instatieteCliente(rs);
                Reserva reserva = instantiateReserva(rs, cliente);

                reservas.add(reserva);
            }
        }
        catch(SQLException e){
            throw new DbException(e.getMessage());
        }
        finally{
            try{
                if(rs != null){
                    rs.close();
                }
                if(st != null){
                    st.close();
                }
            }
            catch(SQLException e){
                throw new DbException(e.getMessage());
            }
        }
        return reservas;
    }


    private Cliente instatieteCliente(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setId(rs.getInt("id"));
        cliente.setNome(rs.getString("Nome"));
        cliente.setEmail(rs.getString("Email"));
        cliente.setTelefone(rs.getString("Telefone"));
        return cliente;
    }

    private Reserva instantiateReserva(ResultSet rs, Cliente cliente) throws SQLException {
        Reserva reserva = new Reserva();
        reserva.setCliente(cliente);
        reserva.setMesaId(rs.getInt("mesa_id"));
        reserva.setDataReserva(rs.getDate("data_reserva").toLocalDate());
        reserva.setHoraReserva(rs.getTime("hora_reserva").toLocalTime());
        return reserva;
    }

    public boolean isMesaReservada(int mesaId) {
        PreparedStatement st = null;
        ResultSet rs = null;
    
        try {
            st = connection.prepareStatement(
                "SELECT * FROM reserva WHERE mesa_id = ?");
            st.setInt(1, mesaId);
    
            rs = st.executeQuery();
            
           
            return rs.next();  
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

}
