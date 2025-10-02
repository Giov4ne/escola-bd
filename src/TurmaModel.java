// TurmaModel.java
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.LinkedHashSet;

public class TurmaModel {

    public static void create(Turma turma, Connection conn) throws SQLException {
        String sql = "INSERT INTO Turmas (id_turma, id_disciplina, codigo, sala, id_professor) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, turma.getId());
            st.setInt(2, turma.getIdDisciplina());
            st.setString(3, turma.getCodigo());
            st.setString(4, turma.getSala());

            if (turma.getIdProfessor() != null) {
                st.setInt(5, turma.getIdProfessor());
            } else {
                st.setNull(5, Types.INTEGER);
            }

            st.execute();
        }
    }

    static LinkedHashSet<Turma> listAll(Connection conn) throws SQLException {
        String sql = "SELECT id_turma, id_professor, id_disciplina, codigo, sala FROM Turmas ORDER BY id_turma";
        LinkedHashSet<Turma> list = new LinkedHashSet<>();
        
        try (Statement st = conn.createStatement();
             ResultSet result = st.executeQuery(sql)) {
            
            while(result.next()) {
                int idProfessorInt = result.getInt(2);
                Integer idProfessor = result.wasNull() ? null : idProfessorInt;

                list.add(new Turma(
                    result.getInt(1),       // id_turma
                    result.getInt(3),       // id_disciplina
                    result.getString(4),    // codigo
                    result.getString(5),     // sala
                    idProfessor            // id_professor
                ));
            }
        }
        return list;
    }

    static void remove(int id, Connection conn) throws SQLException {
        String sql = "DELETE FROM Turmas WHERE id_turma = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            st.execute();
        }
    }
    
    static void update(Turma turma, Connection conn) throws SQLException {
        String sql = "UPDATE Turmas SET id_disciplina = ?, codigo = ?, sala = ?, id_professor = ? WHERE id_turma = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, turma.getIdDisciplina());
            st.setString(2, turma.getCodigo());
            st.setString(3, turma.getSala());

            if (turma.getIdProfessor() != null) {
                st.setInt(4, turma.getIdProfessor());
            } else {
                st.setNull(4, Types.INTEGER);
            }
            
            st.setInt(5, turma.getId());
            st.execute();
        }
    }
    
    static LinkedHashSet<String> listAllWithDetails(Connection conn) throws SQLException {
        String sql = "SELECT t.id_turma, d.nome AS nome_disciplina, t.codigo, t.sala, p.nome AS nome_professor " +
                     "FROM Turmas t " +
                     "JOIN Disciplinas d ON t.id_disciplina = d.id_disciplina " +
                     "LEFT JOIN Professores p ON t.id_professor = p.id_professor " +
                     "ORDER BY t.id_turma";
        
        LinkedHashSet<String> listaFormatada = new LinkedHashSet<>();
        
        try (Statement st = conn.createStatement();
             ResultSet result = st.executeQuery(sql)) {
            
            while(result.next()) {
                int id = result.getInt("id_turma");
                String nomeDisciplina = result.getString("nome_disciplina");
                String codigo = result.getString("codigo");
                String sala = result.getString("sala");
                String nomeProfessor = result.getString("nome_professor"); // Pode ser null

                String professorDisplay = (nomeProfessor != null && !nomeProfessor.isEmpty()) ? nomeProfessor : "Não informado";

                String output = "id: " + id +
                                ", disciplina: " + nomeDisciplina +
                                ", codigo: " + codigo +
                                ", sala: " + sala +
                                ", professor: " + professorDisplay;
                
                listaFormatada.add(output);
            }
        }
        return listaFormatada;
    }
}