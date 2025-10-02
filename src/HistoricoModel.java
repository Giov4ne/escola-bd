//
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashSet;

public class HistoricoModel {

    public static void create(Historico historico, Connection conn) throws SQLException {
        String sql = "INSERT INTO historicos (id_aluno_turma, numero_faltas, situacao) VALUES (?, ?, ?)";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, historico.getIdAlunoTurma());
            st.setInt(2, historico.getNumFaltas());
            st.setString(3, historico.getSituacao());
            st.execute();
        }
    }

    public static void update(Historico historico, Connection conn) throws SQLException {
        String sql = "UPDATE historicos SET numero_faltas = ?, situacao = ? WHERE id_aluno_turma = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, historico.getNumFaltas());
            st.setString(2, historico.getSituacao());
            st.setInt(3, historico.getIdAlunoTurma());
            st.execute();
        }
    }

    public static void remove(int idAlunoTurma, Connection conn) throws SQLException {
        String sql = "DELETE FROM historicos WHERE id_aluno_turma = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, idAlunoTurma);
            st.execute();
        }
    }

    public static LinkedHashSet<String> listAllWithDetails(Connection conn) throws SQLException {
        String sql = "SELECT h.id_aluno_turma, a.nome AS nome_aluno, d.nome AS nome_disciplina, " +
                     "h.numero_faltas, h.situacao " +
                     "FROM historicos h " +
                     "JOIN alunos_turmas atur ON h.id_aluno_turma = atur.id_aluno_turma " +
                     "JOIN Alunos a ON atur.id_aluno = a.id_aluno " +
                     "JOIN Turmas t ON atur.id_turma = t.id_turma " +
                     "JOIN Disciplinas d ON t.id_disciplina = d.id_disciplina " +
                     "ORDER BY h.id_aluno_turma";
        
        LinkedHashSet<String> listaFormatada = new LinkedHashSet<>();
        
        try (Statement st = conn.createStatement();
             ResultSet result = st.executeQuery(sql)) {

            while(result.next()) {
                String output = String.format("ID: %d, Aluno: %s, Disciplina: %s, Faltas: %d, Situação: %s",
                        result.getInt("id_aluno_turma"),
                        result.getString("nome_aluno"),
                        result.getString("nome_disciplina"),
                        result.getInt("numero_faltas"),
                        result.getString("situacao")
                );
                listaFormatada.add(output);
            }
        }
        return listaFormatada;
    }
}