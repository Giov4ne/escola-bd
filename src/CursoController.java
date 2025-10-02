
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Scanner;


public class CursoController {
    public void cadastrarCurso(Connection conexao) throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("Insira os seguintes dados para cadastrar um novo curso: ");
        
        System.out.print("Nome: ");
        String nome = input.nextLine();
        
        System.out.print("Tipo: ");
        String tipo = input.nextLine();
        
        System.out.print("Turno: ");
        String turno = input.nextLine();
        
        
        Curso novoCurso = new Curso(getProximoIdCurso(conexao), nome, tipo, turno);
        
        CursoModel.create(novoCurso, conexao);
        System.out.println("Curso criado com sucesso!!");
    }
    
    public int getProximoIdCurso(Connection conn) throws SQLException {
        String sql = "SELECT MAX(id_curso) FROM cursos";
        
        int maxId = 0;

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                maxId = rs.getInt(1);
            }
        }
        
        return maxId + 1;
    }

    public void listarCursos(Connection conexao) throws SQLException {
        LinkedHashSet all = CursoModel.listAll(conexao);
        Iterator<Curso> it = all.iterator();
        while(it.hasNext()) {
            System.out.println(it.next().toString());
        }
    }

    public void removerCurso(Connection conexao) throws SQLException {
        Scanner input = new Scanner(System.in);
        listarCursos(conexao);
        System.out.println("Informe o id do curso a remover: ");
        int n = input.nextInt();
        CursoModel.remove(n, conexao);  
        System.out.println("Curso removido com sucesso!!");
    }

    public void alterarCurso(Connection conexao) throws SQLException {
        Scanner input = new Scanner(System.in);
        
        listarCursos(conexao);
        
        System.out.println("Informe o id do curso a alterar: ");
        int id = input.nextInt();
        input.nextLine();
        
        System.out.print("Nome: ");
        String nome = input.nextLine();
        
        System.out.print("Tipo: ");
        String tipo = input.nextLine();
        
        System.out.print("Turno: ");
        String turno = input.nextLine();
        
        
        Curso curso = new Curso(id, nome, tipo, turno);
        
        CursoModel.update(curso, conexao);
        System.out.println("Curso atualizado com sucesso!!");
    }
    
    public void listarDisciplinasExcetoDoCurso(int idCurso, Connection conexao) throws SQLException {
        LinkedHashSet all = DisciplinaModel.listAllExceptFromCourse(idCurso, conexao);
        Iterator<Disciplina> it = all.iterator();
        while(it.hasNext()) {
            System.out.println(it.next().toString());
        }
    }
    
    public void listarDisciplinasDoCurso(int idCurso, Connection conexao) throws SQLException {
        LinkedHashSet all = DisciplinaModel.listAllFromCourse(idCurso, conexao);
        Iterator<Disciplina> it = all.iterator();
        while(it.hasNext()) {
            System.out.println(it.next().toString());
        }
    }
    
    public void atribuirDisciplina(Connection conexao) throws SQLException {
        Scanner input = new Scanner(System.in);
        
        listarCursos(conexao);
        
        System.out.println("Informe o id do curso: ");
        int idCurso = input.nextInt();
        input.nextLine();
        
        listarDisciplinasExcetoDoCurso(idCurso, conexao);
        
        System.out.print("Informe o id da disciplina a atribuir: ");
        int idDisciplina = input.nextInt();
        input.nextLine();
        
        System.out.print("Informe a carga horaria da disciplina para este curso: ");
        int carga = input.nextInt();
        input.nextLine();
        
        System.out.print("Disciplina obrigatoria (1-Sim; 2-Nao): ");
        int numObrigatoriedade = input.nextInt();
        Boolean obrigatoriedade = numObrigatoriedade == 1;
        input.nextLine();
        
        CursoDisciplinaModel.assign(idCurso, idDisciplina, carga, obrigatoriedade, conexao);
    }
    
    public void desatribuirDisciplina(Connection conexao) throws SQLException {
        Scanner input = new Scanner(System.in);
        
        listarCursos(conexao);
        
        System.out.println("Informe o id do curso: ");
        int idCurso = input.nextInt();
        input.nextLine();
        
        listarDisciplinasDoCurso(idCurso, conexao);
        
        System.out.print("Informe o id da disciplina a desatribuir: ");
        int idDisciplina = input.nextInt();
        input.nextLine();
        
        CursoDisciplinaModel.unassign(idCurso, idDisciplina, conexao);
    }
}
