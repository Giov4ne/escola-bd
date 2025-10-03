
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Scanner;

public class HistoricoController {

    public void cadastrarHistorico(Connection conexao) throws SQLException {
        Scanner input = new Scanner(System.in);
        
        System.out.println("Inscricoes de Alunos em Turmas que ainda nao possuem Historico:");
        Boolean encontrou = listarInscricoesSemHistorico(conexao);
        
        if (encontrou) {
            System.out.print("\nInforme o ID da Inscricao (Aluno-Turma) para criar o historico: ");
            int idAlunoTurma = Integer.parseInt(input.nextLine());

            Historico novoHistorico = new Historico(idAlunoTurma);
            HistoricoModel.create(novoHistorico, conexao);

            System.out.println("Historico cadastrado com sucesso!");
        }
    }
    
    public void alterarHistorico(Connection conexao) throws SQLException {
        Scanner input = new Scanner(System.in);
        
        listarHistoricos(conexao);
        
        System.out.print("\nInforme o ID do Historico a ser alterado: ");
        int idAlunoTurma = Integer.parseInt(input.nextLine());

        System.out.print("Novo numero de faltas: ");
        int numeroFaltas = Integer.parseInt(input.nextLine());

        System.out.print("Nova situacao: ");
        String situacao = input.nextLine();

        Historico historicoAtualizado = new Historico(idAlunoTurma, numeroFaltas, situacao);
        HistoricoModel.update(historicoAtualizado, conexao);
        
        System.out.println("Historico atualizado com sucesso!");
    }

    public void listarHistoricos(Connection conexao) throws SQLException {
        LinkedHashSet<String> historicos = HistoricoModel.listAllWithDetails(conexao);
        
        if (historicos.isEmpty()) {
            System.out.println("Nenhum histórico cadastrado.");
        } else {
            for (String h : historicos) {
                System.out.println(h);
            }
        }
    }

    public void removerHistorico(Connection conexao) throws SQLException {
        Scanner input = new Scanner(System.in);
        
        listarHistoricos(conexao);
        
        System.out.print("\nInforme o ID do Histórico a ser removido: ");
        int idAlunoTurma = input.nextInt();
        
        HistoricoModel.remove(idAlunoTurma, conexao);
        System.out.println("Histórico removido com sucesso!");
    }

    private Boolean listarInscricoesSemHistorico(Connection conexao) throws SQLException {
        String sql = "SELECT atur.id_aluno_turma, a.nome AS nome_aluno, d.nome AS nome_disciplina " +
                     "FROM alunos_turmas atur " +
                     "LEFT JOIN historicos h ON atur.id_aluno_turma = h.id_aluno_turma " +
                     "JOIN Alunos a ON atur.id_aluno = a.id_aluno " +
                     "JOIN Turmas t ON atur.id_turma = t.id_turma " +
                     "JOIN Disciplinas d ON t.id_disciplina = d.id_disciplina " +
                     "WHERE h.id_aluno_turma IS NULL";

        boolean encontrou = false;

        try (PreparedStatement st = conexao.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            
            while (rs.next()) {
                System.out.printf("ID: %d, Aluno: %s, Disciplina: %s%n",
                        rs.getInt("id_aluno_turma"),
                        rs.getString("nome_aluno"),
                        rs.getString("nome_disciplina"));
                encontrou = true;
            }
            if (!encontrou) {
                System.out.println("Todas as inscrições já possuem um histórico.");
            }
        } finally {
            return encontrou;
        }
    }
}