import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RelatoriosController {

    public void gerarRelatorioMediaNotas(Connection conexao) throws SQLException {
        String sql = "SELECT " +
                     "    a.nome AS nome_aluno, " +
                     "    d.nome AS nome_disciplina, " +
                     "    AVG(av.nota) AS media_notas " +
                     "FROM " +
                     "    Alunos a " +
                     "JOIN " +
                     "    alunos_turmas atur ON a.id_aluno = atur.id_aluno " +
                     "JOIN " +
                     "    historicos h ON atur.id_aluno_turma = h.id_aluno_turma " +
                     "JOIN " +
                     "    avaliacoes av ON h.id_aluno_turma = av.id_aluno_turma " +
                     "JOIN " +
                     "    turmas t ON atur.id_turma = t.id_turma " +
                     "JOIN " +
                     "    disciplinas d ON t.id_disciplina = d.id_disciplina " +
                     "GROUP BY " +
                     "    a.nome, d.nome " +
                     "ORDER BY " +
                     "    a.nome, d.nome";

        System.out.println("\n--- Relatório de Média de Notas por Aluno ---");
        System.out.println("-------------------------------------------------------------------------");
        // Formata o cabeçalho da tabela
        System.out.printf("%-20s | %-30s | %s%n", "ALUNO", "DISCIPLINA", "MÉDIA");
        System.out.println("-------------------------------------------------------------------------");

        try (PreparedStatement st = conexao.prepareStatement(sql);
             ResultSet result = st.executeQuery()) {
            
            if (!result.isBeforeFirst()) {
                System.out.println("Nenhum dado encontrado para gerar o relatório.");
            } else {
                while(result.next()) {
                    String nomeAluno = result.getString("nome_aluno");
                    String nomeDisciplina = result.getString("nome_disciplina");
                    double media = result.getDouble("media_notas");
                    
                    // Formata cada linha do relatório para alinhamento
                    System.out.printf("%-20s | %-30s | %.2f%n", nomeAluno, nomeDisciplina, media);
                }
            }
        }
    }
}