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


public class ClienteDaoJDBC  {

    private Connection connection;

    public ClienteDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    public void adicionarCliente(Cliente cliente){
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(
                "INSERT INTO cliente " +
                "(Nome,Email, Telefone) " +
                "VALUES (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);

            st.setString(1, cliente.getNome());
            st.setString(2, cliente.getEmail());
            st.setString(3, cliente.getTelefone());

            int rowsAffected = st.executeUpdate();

            if(rowsAffected > 0){
                ResultSet rs = st.getGeneratedKeys();
                if(rs.next()){
                    int id = rs.getInt(1);
                    cliente.setId(id);
                }
                rs.close();
            }
            else{
                throw new DbException("Unexpected error! No rows affected");
            }
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        } 
        finally {
            try {
                if (st != null) {
                    st.close();
                }
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    public Cliente findClienteById(int id){
        PreparedStatement st = null;
        ResultSet rs = null;

        try{
            st = connection.prepareStatement("SELECT * FROM cliente WHERE id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();

            if(rs.next()){
                Cliente cliente = instatietCliente(rs); 
                return cliente;
            }
            return null;
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
    }

    public List<Cliente> findCLienteByNameOrEmail(String query) {
        PreparedStatement st = null;
        ResultSet rs = null;
    
        List<Cliente> list = new ArrayList<>();
    
        try {
            st = connection.prepareStatement(
                "SELECT * from cliente WHERE Nome LIKE ? OR Email LIKE ?");
            st.setString(1, "%" + query + "%");
            st.setString(2, "%" + query + "%");
            rs = st.executeQuery();
    
            while (rs.next()) {
                Cliente cliente = instatietCliente(rs);
                System.out.println(
                    "Cliente encontrado! Nome = " + cliente.getNome() + " , Id = " + cliente.getId() + 
                    " , Email = " + cliente.getEmail() + " , Telefone = " + cliente.getTelefone()); // Adicionando para depuração
                list.add(cliente);
            }
            return list;
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
    

    public List<Cliente> listarClientes(){
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Cliente> list = new ArrayList<>();

        try{
            st = connection.prepareStatement("SELECT * FROM cliente");
            rs = st.executeQuery();

            while(rs.next()){
                Cliente cliente = new Cliente(
                    rs.getInt("id"), rs.getString("Nome"), rs.getString("Email"), rs.getString("Telefone"));
                list.add(cliente);
            }
            return list;
        }
        catch(SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
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

    public void removeClientById(int id){
        PreparedStatement st = null;

        try{
            st = connection.prepareStatement("DELETE FROM cliente WHERE id = ?");

            st.setInt(1, id);

            int rowsAffected = st.executeUpdate();
            if(rowsAffected  == 0){
                throw new DbException("Cliente com ID " + id + " não existe");
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

    public void atualizarCliente(Cliente cliente){
        PreparedStatement st = null;

        try{

            st = connection.prepareStatement(
                "UPDATE cliente " +
                "SET nome = ?, Email = ?, Telefone = ? " +
                "WHERE id = ?");

            st.setString(1, cliente.getNome());
            st.setString(2, cliente.getEmail());
            st.setString(3,cliente.getTelefone());
            st.setInt(4, cliente.getId());

            int rowsAffected = st.executeUpdate();

            if(rowsAffected == 0){
                throw new DbException("Cliente com ID " + cliente.getId() + " não encontrado para atualização");
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

    private Cliente instatietCliente(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setId(rs.getInt("Id"));
        cliente.setNome(rs.getString("Nome"));
        cliente.setEmail(rs.getString("Email"));
        cliente.setTelefone(rs.getString("Telefone"));
        return cliente;
    }
    
}
