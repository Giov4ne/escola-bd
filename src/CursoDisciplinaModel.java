
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashSet;

public class CursoDisciplinaModel {
    public static void assign(int idCurso, int idDisciplina, int carga, Boolean obrigatoriedade, Connection conn) throws SQLException {
        PreparedStatement st;
            st = conn.prepareStatement("INSERT INTO cursos_disciplinas (id_curso, id_disciplina, carga_horaria_disciplina, obrigatoriedade_disciplina) VALUES (?,?,?,?)");
            st.setInt(1, idCurso);
            st.setInt(2, idDisciplina);
            st.setInt(3, carga);
            st.setBoolean(4, obrigatoriedade);
            st.execute();
            st.close();
    }
    
    public static void unassign(int idCurso, int idDisciplina, Connection conn) throws SQLException {
        String sql = "DELETE FROM cursos_disciplinas WHERE id_curso=? AND id_disciplina=?";
        PreparedStatement st = conn.prepareStatement(sql);
        st.setInt(1, idCurso);
        st.setInt(2, idDisciplina);
        st.execute();
        st.close();
    }
}
