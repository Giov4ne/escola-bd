
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;


public class Principal {
    public static void main(String[] args) throws SQLException {
        Scanner input = new Scanner(System.in);
        ConexaoNeon c = new ConexaoNeon();
        Connection conexao = c.getConnection();
        int op = 0;
        do{
            op = menuPrincipal(input);
            try {
                switch (op) {
                        case 1:
                            submenu(conexao, input, "Alunos");
                            break;
                        case 2:
                            submenu(conexao, input, "Responsaveis");
                            break;
                        case 3:
                            submenu(conexao, input, "Disciplinas");
                            break;
                        case 4:
                            submenu(conexao, input, "Cursos");
                            break;
                        case 5:
                            submenu(conexao, input, "Professores");
                            break;
                        case 6:
                            submenu(conexao, input, "Turmas");
                            break;
                        case 7:
                            submenu(conexao, input, "Historicos");
                            break;
                        case 8:
                            submenu(conexao, input, "Avaliacoes");
                            break;
                        case 9:
                            new CursoController().atribuirDisciplina(conexao);
                            break;
                        case 10:
                            new CursoController().desatribuirDisciplina(conexao);
                            break;
                        case 11:
                            new AlunoController().atribuirResponsavel(conexao);
                            break;
                        case 12:
                            new AlunoController().desatribuirResponsavel(conexao);
                            break;
                        case 13:
                            new MatriculaController().matricularAlunoCurso(conexao);
                            break;
                        case 14:
                            new MatriculaController().desmatricularAlunoCurso(conexao);
                            break;
                        case 15:
                            new MatriculaController().matricularAlunoTurma(conexao);
                            break;
                        case 16:
                            new MatriculaController().desmatricularAlunoTurma(conexao);
                            break;
                        case 17:
                            relatoriosMenu(conexao, input);
                            break;
                        default:
                            System.out.println("Saindo do sistema...");
                            break;
                    }
//                }
            } catch(SQLException ex) {
                System.out.println(ex.getMessage());
            }
        } while(op > 0 && op < 18);  
        conexao.close();
    }    
    
    private static int menuPrincipal(Scanner input) {
        System.out.println("\n--- MENU PRINCIPAL ---");
        System.out.println("Informe o numero da opcao que deseja executar: ");
        System.out.println("1 - Gerenciar Alunos");
        System.out.println("2 - Gerenciar Responsaveis");
        System.out.println("3 - Gerenciar Disciplinas");
        System.out.println("4 - Gerenciar Cursos");
        System.out.println("5 - Gerenciar Professores");
        System.out.println("6 - Gerenciar Turmas");
        System.out.println("7 - Gerenciar Historicos");
        System.out.println("8 - Gerenciar Avaliacoes");
        System.out.println("9 - Atribuir Disciplina a um Curso");
        System.out.println("10 - Desatribuir Disciplina de um Curso");
        System.out.println("11 - Atribuir Responsavel a um Aluno");
        System.out.println("12 - Desatribuir Responsavel de um Aluno");
        System.out.println("13 - Matricular Aluno em um Curso");
        System.out.println("14 - Desmatricular Aluno em um Curso");
        System.out.println("15 - Matricular Aluno em uma Turma");
        System.out.println("16 - Desmatricular Aluno em uma Turma");
        System.out.println("17 - Relatorios");
        System.out.println("Digite qualquer outro valor para sair");
        System.out.print("Sua opcao: ");
        
        return input.nextInt();
    }
    
    private static void submenu(Connection conexao, Scanner input, String nomeEntidade) throws SQLException {
        int opSubmenu = 0;
        do {
            System.out.printf("\n--- Gerenciar %s ---\n", nomeEntidade);
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Listar");
            System.out.println("3 - Atualizar");
            System.out.println("4 - Excluir");
            System.out.println("5 - Voltar ao Menu Principal");
            System.out.print("Sua opcao: ");
            opSubmenu = input.nextInt();

            switch (opSubmenu) {
                case 1: // CADASTRAR
                    switch (nomeEntidade) {
                        case "Alunos":
                            new AlunoController().cadastrarAluno(conexao);
                            break;
                        case "Responsaveis": 
                            new ResponsavelController().cadastrarResponsavel(conexao);
                            break;
                        case "Disciplinas":
                            new DisciplinaController().cadastrarDisciplina(conexao);
                            break;
                        case "Cursos": 
                            new CursoController().cadastrarCurso(conexao);
                            break;
                        case "Professores":
                            new ProfessorController().cadastrarProfessor(conexao);
                            break;
                        case "Turmas": 
                            new TurmaController().cadastrarTurma(conexao);
                            break;
                        case "Historicos":
                            new HistoricoController().cadastrarHistorico(conexao);
                            break;
                        case "Avaliacoes": 
                            new AvaliacaoController().cadastrarAvaliacao(conexao);
                            break;
                    }
                    break;
                case 2: // LISTAR
                     switch (nomeEntidade) {
                        case "Alunos":
                            new AlunoController().listarAlunos(conexao);
                            break;
                        case "Responsaveis": 
                            new ResponsavelController().listarResponsaveis(conexao);
                            break;
                        case "Disciplinas":
                            new DisciplinaController().listarDisciplinas(conexao);
                            break;
                        case "Cursos": 
                            new CursoController().listarCursos(conexao);
                            break;
                        case "Professores":
                            new ProfessorController().listarProfessores(conexao);
                            break;
                        case "Turmas": 
                            new TurmaController().listarTurmas(conexao);
                            break;
                        case "Historicos":
                            new HistoricoController().listarHistoricos(conexao);
                            break;
                        case "Avaliacoes": 
                            new AvaliacaoController().listarAvaliacoes(conexao);
                            break;
                    }
                    break;
                case 3: // ATUALIZAR
                     switch (nomeEntidade) {
                        case "Alunos":
                            new AlunoController().alterarAluno(conexao);
                            break;
                        case "Responsaveis": 
                            new ResponsavelController().alterarResponsavel(conexao);
                            break;
                        case "Disciplinas":
                            new DisciplinaController().alterarDisciplina(conexao);
                            break;
                        case "Cursos": 
                            new CursoController().alterarCurso(conexao);
                            break;
                        case "Professores":
                            new ProfessorController().alterarProfessor(conexao);
                            break;
                        case "Turmas": 
                            new TurmaController().alterarTurma(conexao);
                            break;       
                        case "Historicos":
                            new HistoricoController().alterarHistorico(conexao);
                            break;
                        case "Avaliacoes": 
                            new AvaliacaoController().alterarAvaliacao(conexao);
                            break;
                    }
                    break;
                case 4: // EXCLUIR
                     switch (nomeEntidade) {
                        case "Alunos":
                            new AlunoController().removerAluno(conexao);
                            break;
                        case "Responsaveis": 
                            new ResponsavelController().removerResponsavel(conexao);
                            break;
                        case "Disciplinas":
                            new DisciplinaController().removerDisciplina(conexao);
                            break;
                        case "Cursos": 
                            new CursoController().removerCurso(conexao);
                            break;
                        case "Professores":
                            new ProfessorController().removerProfessor(conexao);
                            break;
                        case "Turmas": 
                            new TurmaController().removerTurma(conexao);
                            break;
                        case "Historicos":
                            new HistoricoController().removerHistorico(conexao);
                            break;
                        case "Avaliacoes": 
                            new AvaliacaoController().removerAvaliacao(conexao);
                            break;
                    }
                    break;
                case 5:
                    System.out.println("Retornando ao menu principal...");
                    break;
                default:
                    System.out.println("Opcao invalida!");
                    break;
            }
        } while (opSubmenu != 5);
    }
    
    private static void relatoriosMenu(Connection conexao, Scanner input) throws SQLException {
        int opSubmenu = 0;
        Relatorios relatoriosController = new Relatorios();
        
        do {
            System.out.println("\n--- MENU DE RELATORIOS ---");
            System.out.println("1 - Media de notas por aluno em uma disciplina");
            System.out.println("2 - Disciplinas por curso");
            System.out.println("3 - Quantidade de alunos por curso");
            System.out.println("4 - Voltar ao Menu Principal");
            System.out.print("Sua opcao: ");
            opSubmenu = input.nextInt();

            switch (opSubmenu) {
                case 1:
                    relatoriosController.mediaNotasPorAlunoDisciplina(conexao);
                    break;
                case 2:
                    relatoriosController.disciplinasPorCurso(conexao);
                    break;
                case 3:
                    relatoriosController.quantidadeAlunosPorCurso(conexao);
                    break;
                case 4:
                    System.out.println("Retornando ao menu principal...");
                    break;
                default:
                    System.out.println("Opcao invalida!");
                    break;
            }
        } while (opSubmenu != 4);
    }
}
