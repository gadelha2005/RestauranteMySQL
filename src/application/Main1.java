package application;

import java.sql.Connection;
import java.util.List;

import db.Conexao;
import modelDaoImpl.ClienteDaoJDBC;
import entities.Cliente;

public class Main1 {
    public static void main(String[] args) {
       
        Connection connection = Conexao.conectar();

        ClienteDaoJDBC clienteDaoJDBC = new ClienteDaoJDBC(connection);
        
        Cliente novoCliente = new Cliente("Joao","joao@gmail.com", "91508409");
        clienteDaoJDBC.adicionarCliente(novoCliente);
        System.out.println("CLiente inserido com sucesso");

        System.out.println("\nLista de Clientes");
        List<Cliente> clientes = clienteDaoJDBC.listarClientes();
        for(Cliente c : clientes){
            System.out.println(c);
        }

        System.out.println("\nBuscando o cliente pelo id");
        Cliente cliente = clienteDaoJDBC.findClienteById(17);
        if(cliente != null){
            // Atualizar os dados do cliente
            cliente.setNome("Novo Nome");
            cliente.setEmail("novoemail@example.com");
            cliente.setTelefone("123456789");

            clienteDaoJDBC.atualizarCliente(cliente);
            System.out.println(cliente);
        }

        System.out.println("\nBuscando o cliente pelo nome ou email: ");
        clienteDaoJDBC.findCLienteByNameOrEmail("pedro");
        

        System.out.println("\nDeletando o cliente pelo id");
        int idParaRemover = 10;
        clienteDaoJDBC.removeClientById(idParaRemover);
        System.out.println("Cliente com o id = " + idParaRemover + " removido com sucesso!");

       
        

    }
}
