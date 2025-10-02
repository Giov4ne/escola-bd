
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class MatriculaController {

    // Funcionalidade 13
    public void matricularAlunoCurso(Connection conexao) throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("\n--- Matricular Aluno em Curso ---");

        System.out.println("Alunos disponiveis:");

        new AlunoController().listarAlunos(conexao); 
        System.out.print("\nDigite o ID do aluno que deseja matricular: ");
        int idAluno = Integer.parseInt(input.nextLine());

        System.out.println("\nCursos disponiveis:");

        new CursoController().listarCursos(conexao);
        System.out.print("\nDigite o ID do curso: ");
        int idCurso = Integer.parseInt(input.nextLine());
        
        System.out.print("Digite o período/semestre do aluno (Ex: 3° semestre): ");
        String periodo = input.nextLine();

        String novaMatricula = AlunosCursosModel.gerarProximaMatricula(conexao);
        LocalDate dataMatricula = LocalDate.now();

        AlunosCursosModel.create(novaMatricula, idAluno, idCurso, periodo, dataMatricula, conexao);

        System.out.println("\nMatricula realizada com sucesso!");
    }


    public void desmatricularAlunoCurso(Connection conexao) throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("\n--- Desmatricular Aluno de Curso ---");

        System.out.println("Matrículas ativas:");
        AlunosCursosModel.listAllWithDetails(conexao).forEach(System.out::println);

        System.out.print("\nDigite o número da matrícula que deseja remover: ");
        String matricula = input.nextLine();

        AlunosCursosModel.remove(matricula, conexao);
        System.out.println("\nMatrícula removida com sucesso!");
    }

    public void matricularAlunoTurma(Connection conexao) throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("\n--- Matricular Aluno em Turma ---");

        System.out.println("Alunos disponiveis:");
        new AlunoController().listarAlunos(conexao);
        System.out.print("\nDigite o ID do aluno: ");
        int idAluno = Integer.parseInt(input.nextLine());

        System.out.println("\nTurmas disponiveis:");
        new TurmaController().listarTurmas(conexao);
        System.out.print("\nDigite o ID da turma: ");
        int idTurma = Integer.parseInt(input.nextLine());

        int proximoId = AlunosTurmasModel.getProximoId(conexao);
        LocalDate dataInscricao = LocalDate.now();

        AlunosTurmasModel.create(proximoId, idAluno, idTurma, dataInscricao, conexao);
        System.out.println("\nAluno inscrito na turma com sucesso! (ID da inscricao: " + proximoId + ")");
    }

    public void desmatricularAlunoTurma(Connection conexao) throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("\n--- Desmatricular Aluno de Turma ---");

        System.out.println("Inscrições de alunos em turmas:");
        AlunosTurmasModel.listAllWithDetails(conexao).forEach(System.out::println);

        System.out.print("\nDigite o ID da inscricao que deseja remover: ");
        int idAlunoTurma = Integer.parseInt(input.nextLine());

        AlunosTurmasModel.removeCascading(idAlunoTurma, conexao);
        System.out.println("\nInscricao e todos os dados associados foram removidos com sucesso!");
    }
}