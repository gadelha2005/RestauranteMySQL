package entities;
import java.sql.Connection;
import java.util.List;

import db.Conexao;
import modelDaoImpl.ClienteDaoJDBC;
import entities.Cliente;

public class Main {
    public static void main(String[] args) {
       
        Connection connection = Conexao.conectar();

        ClienteDaoJDBC clienteDaoJDBC = new ClienteDaoJDBC(connection);
       

        System.out.println("\nLista de Clientes");
        List<Cliente> clientes = clienteDaoJDBC.listarClientes();
        for(Cliente c : clientes){
            System.out.println(c);
        }

        

        System.out.println("\nBuscando o cliente pelo nome ou email: ");
        clienteDaoJDBC.findByNameOrEmail("pedro");
        
        

       

       
        

    }
}
