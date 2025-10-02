
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashSet;

public class AvaliacaoModel {

    public static void create(Avaliacao avaliacao, Connection conn) throws SQLException {
        String sql = "INSERT INTO avaliacoes (id_avaliacao, descricao, data, nota, id_aluno_turma) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, avaliacao.getId());
            st.setString(2, avaliacao.getDescricao());
            st.setDate(3, Date.valueOf(avaliacao.getData())); // Converte String para java.sql.Date
            st.setDouble(4, avaliacao.getNota());
            st.setInt(5, avaliacao.getIdAlunoTurma());
            st.execute();
        }
    }

    public static void update(Avaliacao avaliacao, Connection conn) throws SQLException {
        String sql = "UPDATE avaliacoes SET descricao = ?, data = ?, nota = ?, id_aluno_turma = ? WHERE id_avaliacao = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, avaliacao.getDescricao());
            st.setDate(2, Date.valueOf(avaliacao.getData()));
            st.setDouble(3, avaliacao.getNota());
            st.setInt(4, avaliacao.getIdAlunoTurma());
            st.setInt(5, avaliacao.getId());
            st.execute();
        }
    }

    public static void remove(int idAvaliacao, Connection conn) throws SQLException {
        String sql = "DELETE FROM avaliacoes WHERE id_avaliacao = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, idAvaliacao);
            st.execute();
        }
    }

    public static LinkedHashSet<String> listAllWithDetails(Connection conn) throws SQLException {
        String sql = "SELECT av.id_avaliacao, av.descricao, av.data, av.nota, " +
                     "a.nome AS nome_aluno, d.nome AS nome_disciplina " +
                     "FROM avaliacoes av " +
                     "JOIN historicos h ON av.id_aluno_turma = h.id_aluno_turma " +
                     "JOIN alunos_turmas atur ON h.id_aluno_turma = atur.id_aluno_turma " +
                     "JOIN Alunos a ON atur.id_aluno = a.id_aluno " +
                     "JOIN Turmas t ON atur.id_turma = t.id_turma " +
                     "JOIN Disciplinas d ON t.id_disciplina = d.id_disciplina " +
                     "ORDER BY av.id_avaliacao";

        LinkedHashSet<String> listaFormatada = new LinkedHashSet<>();
        try (Statement st = conn.createStatement();
             ResultSet result = st.executeQuery(sql)) {

            while (result.next()) {
                String output = String.format(
                    "ID: %d, Aluno: %s, Disciplina: %s, Descrição: %s, Data: %s, Nota: %.2f",
                    result.getInt("id_avaliacao"),
                    result.getString("nome_aluno"),
                    result.getString("nome_disciplina"),
                    result.getString("descricao"),
                    result.getDate("data").toLocalDate().toString(), // Converte Date para String
                    result.getDouble("nota")
                );
                listaFormatada.add(output);
            }
        }
        return listaFormatada;
    }
}