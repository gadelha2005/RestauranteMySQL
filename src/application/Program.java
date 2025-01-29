package application;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import db.Conexao;
import db.DbException;
import entities.Cliente;
import entities.Reserva;
import modelDaoImpl.ClienteDaoJDBC;
import modelDaoImpl.ReservaDaoJDBC;

public class Program {
    public static void main(String[] args) {
        Connection connection = Conexao.conectar();
        Scanner sc = new Scanner(System.in);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        ClienteDaoJDBC clienteDaoJDBC = new ClienteDaoJDBC(connection);
        ReservaDaoJDBC reservaDaoJDBC = new ReservaDaoJDBC(connection);

        while(true){
            System.out.println("\n===== SISTEMA DO RESTAURANTE ====");
            System.out.println("1. Adicionar Cliente");
            System.out.println("2. Atualizar Cliente");
            System.out.println("3. Buscar Cliente");
            System.out.println("4. Remover Cliente");
            System.out.println("5. Listar Clientes");
            System.out.println("6. Adicionar Reserva");
            System.out.println("7. Atualizar Reserva");
            System.out.println("8. Buscar Reserva ");
            System.out.println("9. Remover Reserva");
            System.out.println("10 Listar Reservas");
            System.out.println("11 Sair do Sistema");
            System.out.print("Escolha uma opção: ");
            int opcao = sc.nextInt();

            System.out.println();

            try{
                switch(opcao){
                    case 1:
                        System.out.println("\n Adicionar Cliente");
                        sc.nextLine();
                        System.out.print("Nome do Cliente: ");
                        String nomeCliente = sc.nextLine();
                        System.out.print("Email: ");
                        String email = sc.nextLine();
                        System.out.print("Telefone: ");
                        String telefone = sc.nextLine();

                        Cliente cliente = new Cliente(nomeCliente, email, telefone);
                        clienteDaoJDBC.adicionarCliente(cliente);
                        System.out.println("Cliente adicionado com sucesso!");
                        break;

                    case 2:
                        System.out.println("\nAtualizar Dados do cliente");

                        System.out.print("Digite o ID do cliente: ");
                        int id = sc.nextInt();

                        Cliente clienteParaAtualizar = clienteDaoJDBC.findClienteById(id);

                        if(clienteParaAtualizar != null){
                            System.out.println("Cliente encontrado: " + clienteParaAtualizar);

                            sc.nextLine();

                            System.out.print("Novo nome (Pressione Enter para manter o mesmo): ");
                            String novoNome = sc.nextLine();
                            if(!novoNome.isEmpty()){
                                clienteParaAtualizar.setNome(novoNome);
                            }

                            System.out.print("Novo Email (Pressione Enter para manter o mesmo): ");
                            String novoEmail = sc.nextLine();
                            if(!novoEmail.isEmpty()){
                                clienteParaAtualizar.setEmail(novoEmail);
                            }

                            System.out.print("Novo Telefone (Pressione Enter para manter o mesmo): ");
                            String novoTelefone = sc.nextLine();
                            if(!novoEmail.isEmpty()){
                                clienteParaAtualizar.setTelefone(novoTelefone);
                            }

                            clienteDaoJDBC.atualizarCliente(clienteParaAtualizar);
                            System.out.println("Dados do Cliente atualizados com sucesso!");
                            System.out.println(clienteParaAtualizar);
                        }
                        else{
                            System.out.println("Cliente não encontrado no nosso banco de dados");
                        }
                        break;

                        case 3:
                            System.out.println("\nBuscar Clientes");

                            System.out.print("Digite o Id do cliente: ");
                            int idParaBuscarCliente = sc.nextInt();
                            Cliente clienteBuscado = clienteDaoJDBC.findClienteById(idParaBuscarCliente);
                            System.out.println("Cliente encontrado!");
                            System.out.println(clienteBuscado);
                            break;
                        
                        case 4:
                            System.out.println("\nRemover Cliente");

                            System.out.print("Digite o Id do Cliente: ");
                            int idParaRemoverCliente = sc.nextInt();
                            clienteDaoJDBC.removeClientById(idParaRemoverCliente);
                            System.out.println("Cliente Removido com sucesso");
                            break;

                        case 5:
                            System.out.println("\nLista de Clientes");

                            List<Cliente> clientes = clienteDaoJDBC.listarClientes();
                            for(Cliente c : clientes){
                                System.out.println(c);
                            }
                            break;

                        case 6:
                            System.out.println("\nAdicionar Reserva");

                            System.out.print("Digite o Id do Cliente: ");
                            int idParaReservar = sc.nextInt();
                            Cliente clienteReserva = clienteDaoJDBC.findClienteById(idParaReservar);
                            System.out.print("Digite o Id da mesa: ");
                            int idMesa = sc.nextInt();
                            sc.nextLine();
                            System.out.print("Digite o dia da Reserva(dd/MM/yyyy): ");
                            String dataReservaStr = sc.nextLine();
                            LocalDate dataReserva = LocalDate.parse(dataReservaStr, dateFormatter);
                            System.out.print("Digite o horario da Reserva(HH:mm): ");
                            String horaReservaStr = sc.nextLine();
                            LocalTime horaReserva = LocalTime.parse(horaReservaStr, timeFormatter);

                            Reserva reserva = new Reserva(clienteReserva, idMesa, dataReserva, horaReserva);
                            reservaDaoJDBC.adicionarReserva(reserva);
                            System.out.println("Reserva do cliente " + clienteReserva.getNome() + " está feita!");
                            break;

                        case 7:
                            System.out.println("\nAtualizar a Reserva");

                            System.out.print("Digite o Id da mesa (Pressione Enter para manter o mesmo): ");
                            int mesaIdParaAtualizar = sc.nextInt();
                            sc.nextLine(); // Consumir a nova linha
                            Reserva reservaParaAtualizar = reservaDaoJDBC.findByMesaId(mesaIdParaAtualizar);

                            if (reservaParaAtualizar != null) {
                                System.out.println("Reserva encontrada: " + reservaParaAtualizar);

                                // Atualizar Id da mesa
                                System.out.print("Digite o novo Id da mesa (Pressione Enter para manter o mesmo): ");
                                String novoIdStr = sc.nextLine();
                                if (!novoIdStr.isEmpty()) {
                                    int novoId = Integer.parseInt(novoIdStr);

                                    // Verificar se a mesa já está reservada
                                    if (!reservaDaoJDBC.isMesaReservada(novoId)) {  // Aqui você usa o método isMesaReservada
                                        reservaParaAtualizar.setMesaId(novoId);
                                    } else {
                                        System.out.println("Erro: A mesa " + novoId + " já está reservada. Escolha uma mesa diferente.");
                                        return; // Retorna para o menu sem atualizar a reserva
                                    }
                                }

                                
                                System.out.print("Digite o novo dia da reserva (dd/MM/yyyy) ou pressione Enter para manter o mesmo: ");
                                String novaDataReservaStr = sc.nextLine();
                                if (!novaDataReservaStr.isEmpty()) {
                                    LocalDate novaDataReserva = LocalDate.parse(novaDataReservaStr, dateFormatter);
                                    reservaParaAtualizar.setDataReserva(novaDataReserva);
                                }

                                
                                System.out.print("Digite o novo horário da reserva (HH:mm) ou pressione Enter para manter o mesmo: ");
                                String novoHorarioReservaStr = sc.nextLine();
                                if (!novoHorarioReservaStr.isEmpty()) {
                                    LocalTime novoHorarioReserva = LocalTime.parse(novoHorarioReservaStr, timeFormatter);
                                    reservaParaAtualizar.setHoraReserva(novoHorarioReserva);
                                }

                                reservaDaoJDBC.atualizarReserva(reservaParaAtualizar);
                                System.out.println("Reserva Atualizada: " + reservaParaAtualizar);
                            } else {
                                System.out.println("Reserva não encontrada!");
                            }
                            break;
                        case 8:
                            System.out.println("\nBuscar Reserva");

                            System.out.print("Digite o Id da mesa: ");
                            int idBuscarReserva = sc.nextInt();
                            Reserva reservaBusca = reservaDaoJDBC.findByMesaId(idBuscarReserva);
                            System.out.println("Reserva encontrada! " + reservaBusca);

                            break;
                        case 9:
                            System.out.println("\nRemover Reserva");
                            
                            System.out.print("Digite o Id da mesa ");
                            int idParaRemoverReserva = sc.nextInt();
                            reservaDaoJDBC.removerReserva(idParaRemoverReserva);
                            System.out.println("Reserva cancelada com sucesso!");
                            break;

                        case 10:
                            System.out.println("\nListar Clientes");

                            List<Reserva> reservas = reservaDaoJDBC.listaReservas();
                            for(Reserva r : reservas){
                                System.out.println(r);
                            }
                            break;
                        case 11:
                            System.out.println("Saindo do Sistema...");
                            sc.close();
                            System.exit(0);
                            break;
                }   
            }
            catch(DbException e){
                throw new DbException(e.getMessage());
            }

        }
    }
}
