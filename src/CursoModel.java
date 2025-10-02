
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashSet;

public class CursoModel {
    public static void create(Curso curso, Connection conn) throws SQLException {
        PreparedStatement st;
            st = conn.prepareStatement("INSERT INTO cursos (id_curso, nome, tipo, turno) VALUES (?,?,?,?)");
            st.setInt(1, curso.getId());
            st.setString(2, curso.getNome());
            st.setString(3, curso.getTipo());
            st.setString(4, curso.getTurno());
            st.execute();
            st.close();
    }
    
    static LinkedHashSet listAll(Connection conn) throws SQLException {
        Statement st;
        LinkedHashSet list = new LinkedHashSet();
            st = conn.createStatement();
            String sql = "SELECT id_curso, nome, tipo, turno FROM cursos ORDER BY id_curso";
            
            ResultSet result = st.executeQuery(sql);
            
            while(result.next()) {
                list.add(new Curso(
                        result.getInt(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4)
                ));
            }
        return list;
    }
    
    static void remove(int id, Connection conn) throws SQLException {
        String sql = "DELETE FROM cursos WHERE id_curso = ?";
        PreparedStatement st = conn.prepareStatement(sql);
        st.setInt(1, id);
        st.execute();
        st.close();
    }

    static void update(Curso curso, Connection con) throws SQLException {
        PreparedStatement st;
        st = con.prepareStatement("UPDATE cursos SET nome=?, tipo=?, turno=? WHERE id_curso=?");
        st.setString(1, curso.getNome());
        st.setString(2, curso.getTipo());
        st.setString(3, curso.getTurno());
        st.setInt(4, curso.getId());
        st.execute();
        st.close();          
    }
}
