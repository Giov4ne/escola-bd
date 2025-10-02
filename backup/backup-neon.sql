CREATE TABLE responsaveis (
    id_responsavel INT PRIMARY KEY,
    nome VARCHAR(50),
    telefone VARCHAR(11) UNIQUE,
    cpf VARCHAR(11) UNIQUE
);

CREATE TABLE professores (
    id_professor INT PRIMARY KEY,
    nome VARCHAR(50),
    telefone VARCHAR(11) UNIQUE,
    endereco VARCHAR(255),
    cpf VARCHAR(11) UNIQUE,
    especialidade VARCHAR(50)
);

CREATE TABLE disciplinas (
    id_disciplina INT PRIMARY KEY,
    nome VARCHAR(50),
    ementa VARCHAR(255)
);

CREATE TABLE cursos (
    id_curso INT PRIMARY KEY,
    nome VARCHAR(50),
    tipo VARCHAR(50),
    turno VARCHAR(15)
);

CREATE TABLE cursos_disciplinas (
    id_curso INT,
    id_disciplina INT,
    carga_horaria_disciplina INT,
    obrigatoriedade_disciplina BOOLEAN,
    PRIMARY KEY (id_curso, id_disciplina),
    FOREIGN KEY (id_curso) REFERENCES cursos(id_curso),
    FOREIGN KEY (id_disciplina) REFERENCES disciplinas(id_disciplina)
);

CREATE TABLE Alunos (
    id_aluno INT PRIMARY KEY,
    nome VARCHAR(50),
    telefone VARCHAR(11) UNIQUE,
    endereco VARCHAR(255),
    cpf VARCHAR(11) UNIQUE,
    dt_nascimento DATE,
    id_responsavel INT,
    FOREIGN KEY (id_responsavel) REFERENCES responsaveis(id_responsavel)
);

CREATE TABLE Turmas (
    id_turma INT PRIMARY KEY,
    id_professor INT,
    id_disciplina INT,
    codigo VARCHAR(9) UNIQUE,
    sala VARCHAR(5),
    FOREIGN KEY (id_professor) REFERENCES professores(id_professor),
    FOREIGN KEY (id_disciplina) REFERENCES disciplinas(id_disciplina)
);

CREATE TABLE alunos_cursos (
    matricula VARCHAR(8) PRIMARY KEY,
    id_aluno INT,
    id_curso INT,
    periodo VARCHAR(15),
    dt_matricula DATE,
    FOREIGN KEY (id_aluno) REFERENCES alunos(id_aluno),
    FOREIGN KEY (id_curso) REFERENCES cursos(id_curso)
);

CREATE TABLE alunos_turmas (
    id_aluno_turma INT PRIMARY KEY,
    id_aluno INT,
    id_turma INT,
    dt_inscricao DATE,
    FOREIGN KEY (id_aluno) REFERENCES alunos(id_aluno),
    FOREIGN KEY (id_turma) REFERENCES turmas(id_turma)
);

CREATE TABLE historicos (
    id_aluno_turma INT PRIMARY KEY,
    numero_faltas INT,
    situacao VARCHAR(20),
    FOREIGN KEY (id_aluno_turma) REFERENCES alunos_turmas(id_aluno_turma)
);

CREATE TABLE avaliacoes (
    id_avaliacao INT PRIMARY KEY,
    descricao VARCHAR(100),
    data DATE,
    nota DECIMAL(4, 2) CHECK (nota >= 0 AND nota <= 10),
    id_aluno_turma INT,
    FOREIGN KEY (id_aluno_turma) REFERENCES historicos(id_aluno_turma)
);






INSERT INTO Responsaveis (id_responsavel, nome, telefone, cpf) VALUES
(1, 'Marcos Oliveira', '47912345678', '11122233344'),
(2, 'Julia Pereira', '47987654321', '55566677788'),
(3, 'Ricardo Almeida', '47999887766', '99988877766');

INSERT INTO Professores (id_professor, nome, telefone, endereco, cpf, especialidade) VALUES
(1, 'Carlos Andrade', '47911223344', 'Rua das Flores, 123', '12345678901', 'Matemática'),
(2, 'Ana Souza', '47955667788', 'Avenida Principal, 456', '23456789012', 'Desenvolvimento de Sistemas'),
(3, 'Beatriz Costa', '47988776655', 'Rua da Lapa, 789', '34567890123', 'Administração');

INSERT INTO Cursos (id_curso, nome, tipo, turno) VALUES
(1, 'Ensino Médio', 'Regular', 'Matutino'),
(2, 'Técnico em Informática', 'Técnico', 'Noturno'),
(3, 'Graduação em Administração', 'Superior', 'Noturno');

INSERT INTO Disciplinas (id_disciplina, nome, ementa) VALUES
(1, 'Matemática', 'Estudo de funções, geometria e estatística.'),
(2, 'Língua Portuguesa', 'Estudo da gramática, literatura e redação.'),
(3, 'Programação Orientada a Objetos', 'Introdução aos conceitos de classes, objetos e herança.'),
(4, 'Gestão de Projetos', 'Planejamento, execução e controle de projetos corporativos.');

INSERT INTO Alunos (id_aluno, nome, telefone, endereco, cpf, dt_nascimento, id_responsavel) VALUES
(1, 'Fernando Henrique', '47910102020', 'Rua dos Girassóis, 10', '10120230344', '2008-05-10', 1),
(2, 'Larissa Manoela', '47930304040', 'Avenida Brasil, 200', '50560670788', '2007-11-22', 2),
(3, 'Lucas Martins', '47950506060', 'Rua do Bosque, 30', '90980870766', '2002-02-15', NULL),
(4, 'Mariana Ferreira', '47970708080', 'Avenida das Nações, 450', '40430320211', '2001-09-30', NULL);

INSERT INTO Cursos_Disciplinas (id_curso, id_disciplina, carga_horaria_disciplina, obrigatoriedade_disciplina) VALUES
(1, 1, 90, TRUE), -- Ensino Médio - Matemática
(1, 2, 90, TRUE), -- Ensino Médio - Língua Portuguesa
(2, 3, 120, TRUE), -- Técnico em Informática - Programação Orientada a Objetos
(3, 4, 80, TRUE); -- Graduação em Administração - Gestão de Projetos

INSERT INTO Turmas (id_turma, id_professor, id_disciplina, codigo, sala) VALUES
(1, 1, 1, 'EM1-MAT', 'A101'), -- Turma de Matemática para o Ensino Médio
(2, 2, 3, 'TEC-POO', 'B203'), -- Turma de Programação para o Técnico
(3, 3, 4, 'ADM-GP', 'C305'); -- Turma de Gestão de Projetos para Administração

INSERT INTO Alunos_Cursos (matricula, id_aluno, id_curso, periodo, dt_matricula) VALUES
('20250001', 1, 1, '2° ano', '2024-01-15'),
('20250002', 2, 1, '3° ano', '2023-01-20'),
('20250003', 3, 2, 'Módulo 3', '2024-07-25'),
('20250004', 4, 3, '5° semestre', '2023-07-18');

INSERT INTO Alunos_Turmas (id_aluno_turma, id_aluno, id_turma, dt_inscricao) VALUES
(1, 1, 1, '2025-02-01'), -- Aluno Fernando na turma de Matemática
(2, 2, 1, '2025-02-01'), -- Aluna Larissa na turma de Matemática
(3, 3, 2, '2025-08-01'), -- Aluno Lucas na turma de Programação
(4, 4, 3, '2025-08-01'); -- Aluna Mariana na turma de Gestão de Projetos

INSERT INTO Historicos (id_aluno_turma, numero_faltas, situacao) VALUES
(1, 4, 'Cursando'),
(2, 2, 'Cursando'),
(3, 1, 'Aprovado'),
(4, 5, 'Reprovado');

INSERT INTO Avaliacoes (id_avaliacao, descricao, data, nota, id_aluno_turma) VALUES
(1, 'Prova 1', '2025-04-10', 8.50, 1),
(2, 'Prova 1', '2025-04-10', 9.00, 2),
(3, 'Trabalho Final', '2025-06-20', 7.50, 3),
(4, 'Prova Final', '2025-11-30', 4.00, 4);

