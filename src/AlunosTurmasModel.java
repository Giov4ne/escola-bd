
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.LinkedHashSet;

public class AlunosTurmasModel {

    public static void create(int idAlunoTurma, int idAluno, int idTurma, LocalDate dtInscricao, Connection conn) throws SQLException {
        String sql = "INSERT INTO alunos_turmas (id_aluno_turma, id_aluno, id_turma, dt_inscricao) VALUES (?, ?, ?, ?)";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, idAlunoTurma);
            st.setInt(2, idAluno);
            st.setInt(3, idTurma);
            st.setDate(4, Date.valueOf(dtInscricao));
            st.execute();
        }
    }
    
    public static void removeCascading(int idAlunoTurma, Connection conn) throws SQLException {
        String sqlDeleteAvaliacoes = "DELETE FROM avaliacoes WHERE id_aluno_turma = ?";
        String sqlDeleteHistoricos = "DELETE FROM historicos WHERE id_aluno_turma = ?";
        String sqlDeleteAlunoTurma = "DELETE FROM alunos_turmas WHERE id_aluno_turma = ?";
        
        try {
            conn.setAutoCommit(false);

            try (PreparedStatement stAvaliacoes = conn.prepareStatement(sqlDeleteAvaliacoes)) {
                stAvaliacoes.setInt(1, idAlunoTurma);
                stAvaliacoes.executeUpdate();
            }
            try (PreparedStatement stHistoricos = conn.prepareStatement(sqlDeleteHistoricos)) {
                stHistoricos.setInt(1, idAlunoTurma);
                stHistoricos.executeUpdate();
            }
            try (PreparedStatement stAlunoTurma = conn.prepareStatement(sqlDeleteAlunoTurma)) {
                stAlunoTurma.setInt(1, idAlunoTurma);
                stAlunoTurma.executeUpdate();
            }
            
            conn.commit();

        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public static LinkedHashSet<String> listAllWithDetails(Connection conn) throws SQLException {
        String sql = "SELECT at.id_aluno_turma, a.nome AS nome_aluno, d.nome AS nome_disciplina, t.codigo AS codigo_turma " +
                     "FROM alunos_turmas at " +
                     "JOIN Alunos a ON at.id_aluno = a.id_aluno " +
                     "JOIN Turmas t ON at.id_turma = t.id_turma " +
                     "JOIN Disciplinas d ON t.id_disciplina = d.id_disciplina " +
                     "ORDER BY a.nome";

        LinkedHashSet<String> lista = new LinkedHashSet<>();
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                String info = String.format("ID Inscrição: %d, Aluno: %s, Disciplina: %s, Turma: %s",
                        rs.getInt("id_aluno_turma"),
                        rs.getString("nome_aluno"),
                        rs.getString("nome_disciplina"),
                        rs.getString("codigo_turma"));
                lista.add(info);
            }
        }
        return lista;
    }

    public static int getProximoId(Connection conn) throws SQLException {
        String sql = "SELECT MAX(id_aluno_turma) FROM alunos_turmas";
        int maxId = 0;
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                maxId = rs.getInt(1);
            }
        }
        return maxId + 1;
    }
}