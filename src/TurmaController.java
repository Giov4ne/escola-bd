// TurmaController.java
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Scanner;

public class TurmaController {

    public void cadastrarTurma(Connection conexao) throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("Insira os seguintes dados para cadastrar uma nova turma:");

        System.out.print("Código da Turma (Ex: TEC-POO): ");
        String codigo = input.nextLine();

        System.out.print("Sala (Ex: B203): ");
        String sala = input.nextLine();

        new DisciplinaController().listarDisciplinas(conexao);
        
        System.out.print("ID da Disciplina: ");
        int idDisciplina = Integer.parseInt(input.nextLine());
        
        System.out.print("Deseja atribuir um professor agora? (s/n): ");
        String opcao = input.nextLine();
        
        Integer idProfessor = null;
        if (opcao.equalsIgnoreCase("s")) {
            new ProfessorController().listarProfessores(conexao);
            System.out.print("ID do Professor: ");
            idProfessor = Integer.parseInt(input.nextLine());
        }
        
        Turma novaTurma = new Turma(getProximoIdTurma(conexao), idDisciplina, codigo, sala, idProfessor);
        
        TurmaModel.create(novaTurma, conexao);
        System.out.println("Turma cadastrada com sucesso!!");
    }

    public int getProximoIdTurma(Connection conn) throws SQLException {
        String sql = "SELECT MAX(id_turma) FROM Turmas";
        int maxId = 0;
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                maxId = rs.getInt(1);
            }
        }
        return maxId + 1;
    }

    public void listarTurmas(Connection conexao) throws SQLException {
        LinkedHashSet<String> turmasFormatadas = TurmaModel.listAllWithDetails(conexao);
        
        for (String turmaInfo : turmasFormatadas) {
            System.out.println(turmaInfo);
        }
    }

    public void removerTurma(Connection conexao) throws SQLException {
        Scanner input = new Scanner(System.in);
        listarTurmas(conexao);
        System.out.print("Informe o ID da turma a ser removida: ");
        int id = input.nextInt();
        TurmaModel.remove(id, conexao);
        System.out.println("Turma removida com sucesso!!");
    }

    public void alterarTurma(Connection conexao) throws SQLException {
        Scanner input = new Scanner(System.in);
        listarTurmas(conexao);
        
        System.out.print("Informe o ID da turma a ser alterada: ");
        int id = Integer.parseInt(input.nextLine());

        System.out.println("Insira os novos dados (deixe em branco para não alterar um campo de texto):");

        System.out.print("Novo Código da Turma: ");
        String codigo = input.nextLine();

        System.out.print("Nova Sala: ");
        String sala = input.nextLine();

        System.out.print("Novo ID da Disciplina: ");
        int idDisciplina = Integer.parseInt(input.nextLine());

        System.out.print("Novo ID do Professor (ou deixe em branco para remover): ");
        String idProfessorStr = input.nextLine();
        Integer idProfessor = idProfessorStr.isEmpty() ? null : Integer.parseInt(idProfessorStr);
        
        Turma turmaAtualizada = new Turma(id, idDisciplina, codigo, sala, idProfessor);
        
        TurmaModel.update(turmaAtualizada, conexao);
        System.out.println("Turma atualizada com sucesso!!");
    }
}