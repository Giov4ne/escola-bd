
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeParseException;
import java.util.LinkedHashSet;
import java.util.Scanner;

public class AvaliacaoController {

    public void cadastrarAvaliacao(Connection conexao) throws SQLException {
        Scanner input = new Scanner(System.in);

        System.out.println("Selecione o aluno e a disciplina para lancar a avaliacao:");
        listarHistoricosDisponiveis(conexao);

        System.out.print("\nInforme o ID (Aluno-Turma) para o qual deseja registrar a avaliacao: ");
        int idAlunoTurma = Integer.parseInt(input.nextLine());

        System.out.print("Descricao da avaliacao (Ex: Prova 1): ");
        String descricao = input.nextLine();

        System.out.print("Data da avaliacao (formato AAAA-MM-DD): ");
        String data = input.nextLine();
        
        System.out.print("Nota (0 a 10): ");
        double nota = Double.parseDouble(input.nextLine());


        try {
            java.time.LocalDate.parse(data);
            if (nota < 0 || nota > 10) {
                System.out.println("Erro: A nota deve estar entre 0 e 10.");
                return;
            }
        } catch (DateTimeParseException e) {
            System.out.println("Erro: Formato de data inválido. Use AAAA-MM-DD.");
            return;
        }

        int proximoId = getProximoIdAvaliacao(conexao);
        Avaliacao novaAvaliacao = new Avaliacao(proximoId, descricao, data, nota, idAlunoTurma);
        AvaliacaoModel.create(novaAvaliacao, conexao);
        
        System.out.println("Avaliacao cadastrada com sucesso!");
    }

    public void alterarAvaliacao(Connection conexao) throws SQLException {
        Scanner input = new Scanner(System.in);
        listarAvaliacoes(conexao);

        System.out.print("\nInforme o ID da avaliacao que deseja alterar: ");
        int idAvaliacao = Integer.parseInt(input.nextLine());

        System.out.print("Nova descricao: ");
        String descricao = input.nextLine();
        System.out.print("Nova data (AAAA-MM-DD): ");
        String data = input.nextLine();
        System.out.print("Nova nota: ");
        double nota = Double.parseDouble(input.nextLine());
        System.out.print("Novo ID Aluno-Turma (deixe em branco para não alterar): ");
        String idAlunoTurmaStr = input.nextLine();

        int idAlunoTurma = Integer.parseInt(idAlunoTurmaStr);

        Avaliacao avaliacaoAtualizada = new Avaliacao(idAvaliacao, descricao, data, nota, idAlunoTurma);
        AvaliacaoModel.update(avaliacaoAtualizada, conexao);
        
        System.out.println("Avaliacao atualizada com sucesso!");
    }
    
    public void removerAvaliacao(Connection conexao) throws SQLException {
        Scanner input = new Scanner(System.in);
        listarAvaliacoes(conexao);
        
        System.out.print("\nInforme o ID da avaliacao a ser removida: ");
        int idAvaliacao = input.nextInt();
        
        AvaliacaoModel.remove(idAvaliacao, conexao);
        System.out.println("Avaliacao removida com sucesso!");
    }

    public void listarAvaliacoes(Connection conexao) throws SQLException {
        LinkedHashSet<String> avaliacoes = AvaliacaoModel.listAllWithDetails(conexao);
        
        if (avaliacoes.isEmpty()) {
            System.out.println("Nenhuma avaliacao cadastrada.");
        } else {
            for (String av : avaliacoes) {
                System.out.println(av);
            }
        }
    }

    private int getProximoIdAvaliacao(Connection conn) throws SQLException {
        String sql = "SELECT MAX(id_avaliacao) FROM avaliacoes";
        int maxId = 0;
        try (PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            if (rs.next()) {
                maxId = rs.getInt(1);
            }
        }
        return maxId + 1;
    }

    private void listarHistoricosDisponiveis(Connection conexao) throws SQLException {
        String sql = "SELECT h.id_aluno_turma, a.nome AS nome_aluno, d.nome AS nome_disciplina " +
                     "FROM historicos h " +
                     "JOIN alunos_turmas atur ON h.id_aluno_turma = atur.id_aluno_turma " +
                     "JOIN Alunos a ON atur.id_aluno = a.id_aluno " +
                     "JOIN Turmas t ON atur.id_turma = t.id_turma " +
                     "JOIN Disciplinas d ON t.id_disciplina = d.id_disciplina " +
                     "ORDER BY a.nome, d.nome";

        try (PreparedStatement st = conexao.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                System.out.printf("ID: %d, Aluno: %s, Disciplina: %s%n",
                        rs.getInt("id_aluno_turma"),
                        rs.getString("nome_aluno"),
                        rs.getString("nome_disciplina"));
            }
        }
    }
}