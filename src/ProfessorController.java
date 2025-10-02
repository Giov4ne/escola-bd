
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

public class ProfessorController {
    public void cadastrarProfessor(Connection conexao) throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("Insira os seguintes dados para cadastrar um novo professor: ");
        
        System.out.print("Nome: ");
        String nome = input.nextLine();
        
        System.out.print("Telefone: ");
        String telefone = input.nextLine();
        
        System.out.print("Endereco: ");
        String endereco = input.nextLine();
        
        System.out.print("Cpf: ");
        String cpf = input.nextLine();
        
        System.out.print("Especialidade: ");
        String especialidade = input.nextLine();
        
        Professor novoProf = new Professor(getProximoIdProfessor(conexao), nome, telefone, cpf, endereco, especialidade);
        
        ProfessorModel.create(novoProf, conexao);
        System.out.println("Professor criado com sucesso!!");
    }
    
    public int getProximoIdProfessor(Connection conn) throws SQLException {
        String sql = "SELECT MAX(id_professor) FROM professores";
        
        int maxId = 0;

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                maxId = rs.getInt(1);
            }
        }
        
        return maxId + 1;
    }
    
    public void listarProfessores(Connection conexao) throws SQLException {
        LinkedHashSet all = ProfessorModel.listAll(conexao);
        Iterator<Professor> it = all.iterator();
        while(it.hasNext()) {
            System.out.println(it.next().toString());
        }
    }
    
    public void removerProfessor(Connection conexao) throws SQLException {
        Scanner input = new Scanner(System.in);
        listarProfessores(conexao);
        System.out.println("Informe o id do professor a remover: ");
        int n = input.nextInt();
        ProfessorModel.remove(n, conexao);  
        System.out.println("Professor removido com sucesso!!");
    }
    
    public void alterarProfessor(Connection conexao) throws SQLException {
        Scanner input = new Scanner(System.in);
        
        listarProfessores(conexao);
        
        System.out.println("Informe o id do professor a alterar: ");
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
        
        System.out.print("Especialidade: ");
        String especialidade = input.nextLine();
   
        Professor prof = new Professor(id, nome, telefone, cpf, endereco, especialidade);
        
        ProfessorModel.update(prof, conexao);
        System.out.println("Professor atualizado com sucesso!!");
    }
}
