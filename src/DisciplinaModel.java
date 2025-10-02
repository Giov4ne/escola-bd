
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashSet;

public class DisciplinaModel {
    public static void create(Disciplina disciplina, Connection conn) throws SQLException {
        PreparedStatement st;
            st = conn.prepareStatement("INSERT INTO disciplinas (id_disciplina, nome, ementa) VALUES (?,?,?)");
            st.setInt(1, disciplina.getId());
            st.setString(2, disciplina.getNome());
            st.setString(3, disciplina.getEmenta());
            st.execute();
            st.close();
    }
    
    static LinkedHashSet listAll(Connection conn) throws SQLException {
        Statement st;
        LinkedHashSet list = new LinkedHashSet();
            st = conn.createStatement();
            String sql = "SELECT id_disciplina, nome, ementa FROM disciplinas ORDER BY id_disciplina";
            
            ResultSet result = st.executeQuery(sql);
            
            while(result.next()) {
                list.add(new Disciplina(
                        result.getInt(1),
                        result.getString(2),
                        result.getString(3)
                ));
            }
        return list;
    }
    
    static LinkedHashSet listAllExceptFromCourse(int idCurso, Connection conn) throws SQLException {
        Statement st;
        LinkedHashSet list = new LinkedHashSet();
            st = conn.createStatement();
            String sql = "select d.id_disciplina, d.nome, d.ementa from cursos c\n" +
                        "inner join cursos_disciplinas cd on c.id_curso = cd.id_curso\n" +
                        "inner join disciplinas d on cd.id_disciplina = d.id_disciplina\n" +
                        "except\n" +
                        "select d.id_disciplina, d.nome, d.ementa from cursos c\n" +
                        "inner join cursos_disciplinas cd on c.id_curso = cd.id_curso\n" +
                        "inner join disciplinas d on cd.id_disciplina = d.id_disciplina\n" +
                        "where c.id_curso = " + idCurso + " order by d.id_disciplina";
            
            ResultSet result = st.executeQuery(sql);
            
            while(result.next()) {
                list.add(new Disciplina(
                        result.getInt(1),
                        result.getString(2),
                        result.getString(3)
                ));
            }
        return list;
    }
    
    static LinkedHashSet listAllFromCourse(int idCurso, Connection conn) throws SQLException {
        Statement st;
        LinkedHashSet list = new LinkedHashSet();
            st = conn.createStatement();
            String sql = "select d.id_disciplina, d.nome, d.ementa from cursos c\n" +
                        "inner join cursos_disciplinas cd on c.id_curso = cd.id_curso\n" +
                        "inner join disciplinas d on cd.id_disciplina = d.id_disciplina\n" +
                        "where c.id_curso = " + idCurso + " order by d.id_disciplina";
            
            ResultSet result = st.executeQuery(sql);
            
            while(result.next()) {
                list.add(new Disciplina(
                        result.getInt(1),
                        result.getString(2),
                        result.getString(3)
                ));
            }
        return list;
    }
    
    static void remove(int id, Connection conn) throws SQLException {
        String sql = "DELETE FROM disciplinas WHERE id_disciplina = ?";
        PreparedStatement st = conn.prepareStatement(sql);
        st.setInt(1, id);
        st.execute();
        st.close();
    }

    static void update(Disciplina disciplina, Connection con) throws SQLException {
        PreparedStatement st;
        st = con.prepareStatement("UPDATE disciplinas SET nome=?, ementa=? WHERE id_disciplina=?");
        st.setString(1, disciplina.getNome());
        st.setString(2, disciplina.getEmenta());
        st.setInt(3, disciplina.getId());
        st.execute();
        st.close();          
    }
}
