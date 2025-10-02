// MatriculaController.java
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class MatriculaController {

    // Funcionalidade 13
    public void matricularAlunoCurso(Connection conexao) throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("\n--- Matricular Aluno em Curso ---");

        // Passo 1: Listar e selecionar o aluno
        System.out.println("Alunos disponíveis:");
        // (Assume-se que você tem um AlunoController com o método listarAlunos)
        new AlunoController().listarAlunos(conexao); 
        System.out.print("\nDigite o ID do aluno que deseja matricular: ");
        int idAluno = Integer.parseInt(input.nextLine());

        // Passo 2: Listar e selecionar o curso
        System.out.println("\nCursos disponíveis:");
        // (Assume-se que você tem um CursoController com o método listarCursos)
        new CursoController().listarCursos(conexao);
        System.out.print("\nDigite o ID do curso: ");
        int idCurso = Integer.parseInt(input.nextLine());
        
        System.out.print("Digite o período/semestre do aluno (Ex: 3° semestre): ");
        String periodo = input.nextLine();

        // Passo 3: Gerar matrícula e data
        String novaMatricula = AlunosCursosModel.gerarProximaMatricula(conexao);
        LocalDate dataMatricula = LocalDate.now();

        // Passo 4: Efetuar a matrícula
        AlunosCursosModel.create(novaMatricula, idAluno, idCurso, periodo, dataMatricula, conexao);

        System.out.println("\nMatrícula " + novaMatricula + " realizada com sucesso!");
    }

    // Funcionalidade 14
    public void desmatricularAlunoCurso(Connection conexao) throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("\n--- Desmatricular Aluno de Curso ---");

        // Passo 1: Listar matrículas existentes
        System.out.println("Matrículas ativas:");
        AlunosCursosModel.listAllWithDetails(conexao).forEach(System.out::println);

        // Passo 2: Solicitar a matrícula a ser removida
        System.out.print("\nDigite o número da matrícula que deseja remover: ");
        String matricula = input.nextLine();

        // Passo 3: Efetuar a remoção
        AlunosCursosModel.remove(matricula, conexao);
        System.out.println("\nMatrícula removida com sucesso!");
    }

    // Funcionalidade 15
    public void matricularAlunoTurma(Connection conexao) throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("\n--- Matricular Aluno em Turma ---");

        // Passo 1: Listar e selecionar o aluno
        System.out.println("Alunos disponíveis:");
        new AlunoController().listarAlunos(conexao);
        System.out.print("\nDigite o ID do aluno: ");
        int idAluno = Integer.parseInt(input.nextLine());

        // Passo 2: Listar e selecionar a turma
        System.out.println("\nTurmas disponíveis:");
        new TurmaController().listarTurmas(conexao); // Usa o método já existente e aprimorado
        System.out.print("\nDigite o ID da turma: ");
        int idTurma = Integer.parseInt(input.nextLine());

        // Passo 3: Gerar ID e data
        int proximoId = AlunosTurmasModel.getProximoId(conexao);
        LocalDate dataInscricao = LocalDate.now();

        // Passo 4: Efetuar a matrícula na turma
        AlunosTurmasModel.create(proximoId, idAluno, idTurma, dataInscricao, conexao);
        System.out.println("\nAluno inscrito na turma com sucesso! (ID da inscrição: " + proximoId + ")");
    }

    // Funcionalidade 16
    public void desmatricularAlunoTurma(Connection conexao) throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("\n--- Desmatricular Aluno de Turma ---");

        // Passo 1: Listar inscrições existentes
        System.out.println("Inscrições de alunos em turmas:");
        AlunosTurmasModel.listAllWithDetails(conexao).forEach(System.out::println);

        // Passo 2: Solicitar o ID da inscrição a ser removida
        System.out.print("\nDigite o ID da inscrição que deseja remover: ");
        int idAlunoTurma = Integer.parseInt(input.nextLine());

        // Passo 3: Efetuar a remoção em cascata (avaliações, históricos, e a própria inscrição)
        AlunosTurmasModel.removeCascading(idAlunoTurma, conexao);
        System.out.println("\nInscrição e todos os dados associados (histórico, avaliações) foram removidos com sucesso!");
    }
}