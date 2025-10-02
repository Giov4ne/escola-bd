// AlunosCursosModel.java
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Calendar;

public class AlunosCursosModel {

    public static void create(String matricula, int idAluno, int idCurso, String periodo, LocalDate dtMatricula, Connection conn) throws SQLException {
        String sql = "INSERT INTO alunos_cursos (matricula, id_aluno, id_curso, periodo, dt_matricula) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, matricula);
            st.setInt(2, idAluno);
            st.setInt(3, idCurso);
            st.setString(4, periodo);
            st.setDate(5, Date.valueOf(dtMatricula));
            st.execute();
        }
    }

    public static void remove(String matricula, Connection conn) throws SQLException {
        String sql = "DELETE FROM alunos_cursos WHERE matricula = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, matricula);
            st.execute();
        }
    }

    public static LinkedHashSet<String> listAllWithDetails(Connection conn) throws SQLException {
        String sql = "SELECT ac.matricula, a.nome AS nome_aluno, c.nome AS nome_curso, ac.periodo " +
                     "FROM alunos_cursos ac " +
                     "JOIN Alunos a ON ac.id_aluno = a.id_aluno " +
                     "JOIN Cursos c ON ac.id_curso = c.id_curso " +
                     "ORDER BY a.nome";
        
        LinkedHashSet<String> lista = new LinkedHashSet<>();
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                String info = String.format("Matrícula: %s, Aluno: %s, Curso: %s, Período: %s",
                        rs.getString("matricula"),
                        rs.getString("nome_aluno"),
                        rs.getString("nome_curso"),
                        rs.getString("periodo"));
                lista.add(info);
            }
        }
        return lista;
    }

    public static String gerarProximaMatricula(Connection conn) throws SQLException {
        int anoAtual = Calendar.getInstance().get(Calendar.YEAR);
        String prefixo = String.valueOf(anoAtual);
        String sql = "SELECT MAX(CAST(SUBSTRING(matricula, 5) AS INTEGER)) FROM alunos_cursos WHERE matricula LIKE ?";
        
        int proximoNumero = 1;
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, prefixo + "%");
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    proximoNumero = rs.getInt(1) + 1;
                }
            }
        }
        return prefixo + String.format("%04d", proximoNumero); // Ex: 20250001
    }
}