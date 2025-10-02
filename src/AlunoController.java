
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.time.format.DateTimeParseException;

public class AlunoController {
    public void cadastrarAluno(Connection conexao) throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("Insira os seguintes dados para cadastrar um novo aluno: ");
        
        System.out.print("Nome: ");
        String nome = input.nextLine();
        
        System.out.print("Telefone: ");
        String telefone = input.nextLine();
        
        System.out.print("Endereco: ");
        String endereco = input.nextLine();
        
        System.out.print("Cpf: ");
        String cpf = input.nextLine();
        
        Date dtNascimento = null;
        while (dtNascimento == null) {
            System.out.print("Data de nascimento (AAAA-MM-DD): ");
            String dataStr = input.nextLine();
            try {
                dtNascimento = Date.valueOf(LocalDate.parse(dataStr));
            } catch (DateTimeParseException | IllegalArgumentException e) {
                System.err.println("Formato de data inválido! Por favor, use AAAA-MM-DD.");
            }
        }
        
        
        Aluno novoAluno = new Aluno(getProximoIdAluno(conexao), nome, telefone, cpf, endereco, dtNascimento);
        
        AlunoModel.create(novoAluno, conexao);
        System.out.println("Aluno criado com sucesso!!");
    }
    
    public int getProximoIdAluno(Connection conn) throws SQLException {
        String sql = "SELECT MAX(id_aluno) FROM alunos";
        
        int maxId = 0;

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                maxId = rs.getInt(1);
            }
        }
        
        return maxId + 1;
    }

    public void listarAlunos(Connection conexao) throws SQLException {
        LinkedHashSet all = AlunoModel.listAll(conexao);
        Iterator<Aluno> it = all.iterator();
        while(it.hasNext()) {
            System.out.println(it.next().toString());
        }
    }

    public void removerAluno(Connection conexao) throws SQLException {
        Scanner input = new Scanner(System.in);
        listarAlunos(conexao);
        System.out.println("Informe o id do aluno a remover: ");
        int n = input.nextInt();
        AlunoModel.remove(n, conexao);  
        System.out.println("Aluno removido com sucesso!!");
    }

    public void alterarAluno(Connection conexao) throws SQLException {
        Scanner input = new Scanner(System.in);
        
        listarAlunos(conexao);
        
        System.out.println("Informe o id do aluno a alterar: ");
        int id = input.nextInt();
        input.nextLine();
        
        System.out.print("Nome: ");
        String nome = input.nextLine();
        
        System.out.print("Telefone: ");
        String telefone = input.nextLine();
        
        System.out.print("Endereco: ");
        String endereco = input.nextLine();
        
        System.out.print("Cpf: ");
        String cpf = input.nextLine();
        
        Date dtNascimento = null;
        while (dtNascimento == null) {
            System.out.print("Data de nascimento (AAAA-MM-DD): ");
            String dataStr = input.nextLine();
            try {
                dtNascimento = Date.valueOf(LocalDate.parse(dataStr));
            } catch (DateTimeParseException | IllegalArgumentException e) {
                System.err.println("Formato de data inválido! Por favor, use AAAA-MM-DD.");
            }
        }
        
        
        Aluno aluno = new Aluno(id, nome, telefone, cpf, endereco, dtNascimento);
        
        AlunoModel.update(aluno, conexao);
        System.out.println("Aluno atualizado com sucesso!!");
    }
    
    public void atribuirResponsavel(Connection conexao) throws SQLException {
        Scanner input = new Scanner(System.in);
        
        listarAlunos(conexao);
        
        System.out.println("Informe o id do aluno: ");
        int idAluno = input.nextInt();
        input.nextLine();
        
        new ProfessorController().listarProfessores(conexao);
        ProfessorModel.listAll(conexao);
        
        System.out.print("Informe o id do responsavel a atribuir: ");
        int idResponsavel = input.nextInt();
        input.nextLine();
        
        AlunoModel.assignResponsavel(idAluno, idResponsavel, conexao);
    }
    
    public void desatribuirResponsavel(Connection conexao) throws SQLException {
        Scanner input = new Scanner(System.in);
        
        listarAlunos(conexao);
        
        System.out.println("Informe o id do aluno: ");
        int idAluno = input.nextInt();
        input.nextLine();
        
        new ProfessorController().listarProfessores(conexao);
        
        AlunoModel.unassignResponsavel(idAluno, conexao);
    }
}
