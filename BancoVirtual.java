import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// classe para representar um Cliente
class Cliente {
    private String nome;
    private String cpf;

    public Cliente(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }
}

// classe para representar uma conta bancária
class Conta {
    private static int contadorId = 1; // Gerador de IDs para contas
    private int idConta;
    private Cliente cliente;
    private double saldo;

    public Conta(Cliente cliente) {
        this.idConta = contadorId++;
        this.cliente = cliente;
        this.saldo = 0.0;
    }

    public int getIdConta() {
        return idConta;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public double getSaldo() {
        return saldo;
    }

    public void depositar(double valor) {
        if (valor > 0) {
            saldo += valor;
            System.out.println("Depósito de R$ " + valor + " realizado com sucesso!");
        } else {
            System.out.println("Valor inválido para depósito.");
        }
    }

    public void sacar(double valor) {
        if (valor > 0 && saldo >= valor) {
            saldo -= valor;
            System.out.println("Saque de R$ " + valor + " realizado com sucesso!");
        } else {
            System.out.println("Saldo insuficiente ou valor inválido.");
        }
    }
}

// classe principal do sistema bancário
public class BancoVirtual {
    private static List<Cliente> clientes = new ArrayList<>();
    private static List<Conta> contas = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            exibirMenu();
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer

            switch (opcao) {
                case 1 -> criarCliente(scanner);
                case 2 -> criarConta(scanner);
                case 3 -> realizarDeposito(scanner);
                case 4 -> realizarSaque(scanner);
                case 5 -> consultarSaldo(scanner);
                case 6 -> {
                    System.out.println("Saindo do sistema...");
                    System.exit(0);
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void exibirMenu() {
        System.out.println("""
                ===========================
                Bem-vindo ao Banco Virtual!
                Escolha uma opção:
                1. Criar Cliente
                2. Abrir Conta
                3. Realizar Depósito
                4. Realizar Saque
                5. Consultar Saldo
                6. Sair
                ============================
                """);
    }

    private static void criarCliente(Scanner scanner) {
        System.out.print("Digite o nome do cliente: ");
        String nome = scanner.nextLine();

        System.out.print("Digite o CPF: ");
        String cpf = scanner.nextLine();

        // verificar se o cliente já existe
        for (Cliente cliente : clientes) {
            if (cliente.getCpf().equals(cpf)) {
                System.out.println("Cliente com esse CPF já existe.");
                return;
            }
        }

        clientes.add(new Cliente(nome, cpf));
        System.out.println("Cliente criado com sucesso!");
    }

    private static void criarConta(Scanner scanner) {
        System.out.print("Digite o CPF do cliente: ");
        String cpf = scanner.nextLine();

        // procurar cliente pelo CPF
        for (Cliente cliente : clientes) {
            if (cliente.getCpf().equals(cpf)) {
                contas.add(new Conta(cliente));
                System.out.println("Conta criada com sucesso para o cliente: " + cliente.getNome());
                return;
            }
        }

        System.out.println("Cliente não encontrado.");
    }

    private static void realizarDeposito(Scanner scanner) {
        System.out.print("Digite o número da conta: ");
        int idConta = scanner.nextInt();

        System.out.print("Digite o valor do depósito: ");
        double valor = scanner.nextDouble();

        // Procurar conta pelo ID
        for (Conta conta : contas) {
            if (conta.getIdConta() == idConta) {
                conta.depositar(valor);
                return;
            }
        }

        System.out.println("Conta não encontrada.");
    }

    private static void realizarSaque(Scanner scanner) {
        System.out.print("Digite o número da conta: ");
        int idConta = scanner.nextInt();

        System.out.print("Digite o valor do saque: ");
        double valor = scanner.nextDouble();

        // Procurar conta pelo ID
        for (Conta conta : contas) {
            if (conta.getIdConta() == idConta) {
                conta.sacar(valor);
                return;
            }
        }

        System.out.println("Conta não encontrada.");
    }

    private static void consultarSaldo(Scanner scanner) {
        System.out.print("Digite o número da conta: ");
        int idConta = scanner.nextInt();

        // Procurar conta pelo ID
        for (Conta conta : contas) {
            if (conta.getIdConta() == idConta) {
                System.out.printf("Saldo atual da conta %d: R$ %.2f%n", idConta, conta.getSaldo());
                return;
            }
        }

        System.out.println("Conta não encontrada.");
    }
}
