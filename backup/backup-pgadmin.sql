--
-- PostgreSQL database dump
--

-- Dumped from database version 14.5
-- Dumped by pg_dump version 14.5

-- Started on 2025-10-02 02:00:13

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 210 (class 1259 OID 16385)
-- Name: responsaveis; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.responsaveis (
    id_responsavel integer NOT NULL,
    nome character varying(50),
    telefone character varying(11),
    cpf character varying(11)
);


ALTER TABLE public.responsaveis OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 16390)
-- Name: professores; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.professores (
    id_professor integer NOT NULL,
    nome character varying(50),
    telefone character varying(11),
    endereco character varying(255),
    cpf character varying(11),
    especialidade character varying(50)
);


ALTER TABLE public.professores OWNER TO postgres;

--
-- TOC entry 212 (class 1259 OID 16395)
-- Name: disciplinas; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.disciplinas (
    id_disciplina integer NOT NULL,
    nome character varying(50),
    ementa character varying(255)
);


ALTER TABLE public.disciplinas OWNER TO postgres;

--
-- TOC entry 213 (class 1259 OID 16400)
-- Name: cursos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cursos (
    id_curso integer NOT NULL,
    nome character varying(50),
    tipo character varying(50),
    turno character varying(15)
);


ALTER TABLE public.cursos OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 16405)
-- Name: cursos_disciplinas; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cursos_disciplinas (
    id_curso integer NOT NULL,
    id_disciplina integer NOT NULL,
    carga_horaria_disciplina integer,
    obrigatoriedade_disciplina boolean
);


ALTER TABLE public.cursos_disciplinas OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 16410)
-- Name: alunos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.alunos (
    id_aluno integer NOT NULL,
    nome character varying(50),
    telefone character varying(11),
    endereco character varying(255),
    cpf character varying(11),
    dt_nascimento date,
    id_responsavel integer
);


ALTER TABLE public.alunos OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 16415)
-- Name: turmas; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.turmas (
    id_turma integer NOT NULL,
    id_professor integer,
    id_disciplina integer,
    codigo character varying(9),
    sala character varying(5)
);


ALTER TABLE public.turmas OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 16420)
-- Name: alunos_cursos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.alunos_cursos (
    matricula character varying(8) NOT NULL,
    id_aluno integer,
    id_curso integer,
    periodo character varying(15),
    dt_matricula date
);


ALTER TABLE public.alunos_cursos OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 16425)
-- Name: alunos_turmas; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.alunos_turmas (
    id_aluno_turma integer NOT NULL,
    id_aluno integer,
    id_turma integer,
    dt_inscricao date
);


ALTER TABLE public.alunos_turmas OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 16430)
-- Name: historicos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.historicos (
    id_aluno_turma integer NOT NULL,
    numero_faltas integer,
    situacao character varying(20)
);


ALTER TABLE public.historicos OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 16435)
-- Name: avaliacoes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.avaliacoes (
    id_avaliacao integer NOT NULL,
    descricao character varying(100),
    data date,
    nota numeric(4,2),
    id_aluno_turma integer,
    CONSTRAINT avaliacoes_nota_check CHECK (((nota >= 0.00) AND (nota <= 10.00)))
);


ALTER TABLE public.avaliacoes OWNER TO postgres;

--
-- TOC entry 3345 (class 0 OID 16385)
-- Dependencies: 210
-- Data for Name: responsaveis; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.responsaveis (id_responsavel, nome, telefone, cpf) VALUES
(1, 'Marcos Oliveira', '47912345678', '11122233344'),
(2, 'Julia Pereira', '47987654321', '55566677788'),
(3, 'Ricardo Almeida', '47999887766', '99988877766');


--
-- TOC entry 3346 (class 0 OID 16390)
-- Dependencies: 211
-- Data for Name: professores; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.professores (id_professor, nome, telefone, endereco, cpf, especialidade) VALUES
(1, 'Carlos Andrade', '47911223344', 'Rua das Flores, 123', '12345678901', 'Matemática'),
(2, 'Ana Souza', '47955667788', 'Avenida Principal, 456', '23456789012', 'Desenvolvimento de Sistemas'),
(3, 'Beatriz Costa', '47988776655', 'Rua da Lapa, 789', '34567890123', 'Administração');


--
-- TOC entry 3348 (class 0 OID 16400)
-- Dependencies: 213
-- Data for Name: cursos; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.cursos (id_curso, nome, tipo, turno) VALUES
(1, 'Ensino Médio', 'Regular', 'Matutino'),
(2, 'Técnico em Informática', 'Técnico', 'Noturno'),
(3, 'Graduação em Administração', 'Superior', 'Noturno');


--
-- TOC entry 3347 (class 0 OID 16395)
-- Dependencies: 212
-- Data for Name: disciplinas; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.disciplinas (id_disciplina, nome, ementa) VALUES
(1, 'Matemática', 'Estudo de funções, geometria e estatística.'),
(2, 'Língua Portuguesa', 'Estudo da gramática, literatura e redação.'),
(3, 'Programação Orientada a Objetos', 'Introdução aos conceitos de classes, objetos e herança.'),
(4, 'Gestão de Projetos', 'Planejamento, execução e controle de projetos corporativos.');


--
-- TOC entry 3350 (class 0 OID 16410)
-- Dependencies: 215
-- Data for Name: alunos; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.alunos (id_aluno, nome, telefone, endereco, cpf, dt_nascimento, id_responsavel) VALUES
(1, 'Fernando Henrique', '47910102020', 'Rua dos Girassóis, 10', '10120230344', '2008-05-10', 1),
(2, 'Larissa Manoela', '47930304040', 'Avenida Brasil, 200', '50560670788', '2007-11-22', 2),
(3, 'Lucas Martins', '47950506060', 'Rua do Bosque, 30', '90980870766', '2002-02-15', NULL),
(4, 'Mariana Ferreira', '47970708080', 'Avenida das Nações, 450', '40430320211', '2001-09-30', NULL);


--
-- TOC entry 3349 (class 0 OID 16405)
-- Dependencies: 214
-- Data for Name: cursos_disciplinas; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.cursos_disciplinas (id_curso, id_disciplina, carga_horaria_disciplina, obrigatoriedade_disciplina) VALUES
(1, 1, 90, TRUE),
(1, 2, 90, TRUE),
(2, 3, 120, TRUE),
(3, 4, 80, TRUE);


--
-- TOC entry 3351 (class 0 OID 16415)
-- Dependencies: 216
-- Data for Name: turmas; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.turmas (id_turma, id_professor, id_disciplina, codigo, sala) VALUES
(1, 1, 1, 'EM1-MAT', 'A101'),
(2, 2, 3, 'TEC-POO', 'B203'),
(3, 3, 4, 'ADM-GP', 'C305');


--
-- TOC entry 3352 (class 0 OID 16420)
-- Dependencies: 217
-- Data for Name: alunos_cursos; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.alunos_cursos (matricula, id_aluno, id_curso, periodo, dt_matricula) VALUES
('20250001', 1, 1, '2° ano', '2024-01-15'),
('20250002', 2, 1, '3° ano', '2023-01-20'),
('20250003', 3, 2, 'Módulo 3', '2024-07-25'),
('20250004', 4, 3, '5° semestre', '2023-07-18');


--
-- TOC entry 3353 (class 0 OID 16425)
-- Dependencies: 218
-- Data for Name: alunos_turmas; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.alunos_turmas (id_aluno_turma, id_aluno, id_turma, dt_inscricao) VALUES
(1, 1, 1, '2025-02-01'),
(2, 2, 1, '2025-02-01'),
(3, 3, 2, '2025-08-01'),
(4, 4, 3, '2025-08-01');


--
-- TOC entry 3354 (class 0 OID 16430)
-- Dependencies: 219
-- Data for Name: historicos; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.historicos (id_aluno_turma, numero_faltas, situacao) VALUES
(1, 4, 'Cursando'),
(2, 2, 'Cursando'),
(3, 1, 'Aprovado'),
(4, 5, 'Reprovado');


--
-- TOC entry 3355 (class 0 OID 16435)
-- Dependencies: 220
-- Data for Name: avaliacoes; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.avaliacoes (id_avaliacao, descricao, data, nota, id_aluno_turma) VALUES
(1, 'Prova 1', '2025-04-10', 8.50, 1),
(2, 'Prova 1', '2025-04-10', 9.00, 2),
(3, 'Trabalho Final', '2025-06-20', 7.50, 3),
(4, 'Prova Final', '2025-11-30', 4.00, 4);


--
-- TOC entry 3185 (class 2606 OID 16441)
-- Name: responsaveis responsaveis_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.responsaveis
    ADD CONSTRAINT responsaveis_pkey PRIMARY KEY (id_responsavel);


--
-- TOC entry 3187 (class 2606 OID 16443)
-- Name: responsaveis responsaveis_telefone_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.responsaveis
    ADD CONSTRAINT responsaveis_telefone_key UNIQUE (telefone);


--
-- TOC entry 3189 (class 2606 OID 16445)
-- Name: responsaveis responsaveis_cpf_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.responsaveis
    ADD CONSTRAINT responsaveis_cpf_key UNIQUE (cpf);


--
-- TOC entry 3191 (class 2606 OID 16447)
-- Name: professores professores_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.professores
    ADD CONSTRAINT professores_pkey PRIMARY KEY (id_professor);


--
-- TOC entry 3193 (class 2606 OID 16449)
-- Name: professores professores_telefone_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.professores
    ADD CONSTRAINT professores_telefone_key UNIQUE (telefone);


--
-- TOC entry 3195 (class 2606 OID 16451)
-- Name: professores professores_cpf_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.professores
    ADD CONSTRAINT professores_cpf_key UNIQUE (cpf);


--
-- TOC entry 3197 (class 2606 OID 16453)
-- Name: disciplinas disciplinas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.disciplinas
    ADD CONSTRAINT disciplinas_pkey PRIMARY KEY (id_disciplina);


--
-- TOC entry 3199 (class 2606 OID 16455)
-- Name: cursos cursos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cursos
    ADD CONSTRAINT cursos_pkey PRIMARY KEY (id_curso);


--
-- TOC entry 3201 (class 2606 OID 16457)
-- Name: cursos_disciplinas cursos_disciplinas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cursos_disciplinas
    ADD CONSTRAINT cursos_disciplinas_pkey PRIMARY KEY (id_curso, id_disciplina);


--
-- TOC entry 3203 (class 2606 OID 16459)
-- Name: alunos alunos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.alunos
    ADD CONSTRAINT alunos_pkey PRIMARY KEY (id_aluno);


--
-- TOC entry 3205 (class 2606 OID 16461)
-- Name: alunos alunos_telefone_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.alunos
    ADD CONSTRAINT alunos_telefone_key UNIQUE (telefone);


--
-- TOC entry 3207 (class 2606 OID 16463)
-- Name: alunos alunos_cpf_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.alunos
    ADD CONSTRAINT alunos_cpf_key UNIQUE (cpf);


--
-- TOC entry 3209 (class 2606 OID 16465)
-- Name: turmas turmas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.turmas
    ADD CONSTRAINT turmas_pkey PRIMARY KEY (id_turma);


--
-- TOC entry 3211 (class 2606 OID 16467)
-- Name: turmas turmas_codigo_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.turmas
    ADD CONSTRAINT turmas_codigo_key UNIQUE (codigo);


--
-- TOC entry 3213 (class 2606 OID 16469)
-- Name: alunos_cursos alunos_cursos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.alunos_cursos
    ADD CONSTRAINT alunos_cursos_pkey PRIMARY KEY (matricula);


--
-- TOC entry 3215 (class 2606 OID 16471)
-- Name: alunos_turmas alunos_turmas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.alunos_turmas
    ADD CONSTRAINT alunos_turmas_pkey PRIMARY KEY (id_aluno_turma);


--
-- TOC entry 3217 (class 2606 OID 16473)
-- Name: historicos historicos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.historicos
    ADD CONSTRAINT historicos_pkey PRIMARY KEY (id_aluno_turma);


--
-- TOC entry 3219 (class 2606 OID 16475)
-- Name: avaliacoes avaliacoes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.avaliacoes
    ADD CONSTRAINT avaliacoes_pkey PRIMARY KEY (id_avaliacao);


--
-- TOC entry 3220 (class 2606 OID 16476)
-- Name: cursos_disciplinas cursos_disciplinas_id_curso_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cursos_disciplinas
    ADD CONSTRAINT cursos_disciplinas_id_curso_fkey FOREIGN KEY (id_curso) REFERENCES public.cursos(id_curso);


--
-- TOC entry 3221 (class 2606 OID 16481)
-- Name: cursos_disciplinas cursos_disciplinas_id_disciplina_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cursos_disciplinas
    ADD CONSTRAINT cursos_disciplinas_id_disciplina_fkey FOREIGN KEY (id_disciplina) REFERENCES public.disciplinas(id_disciplina);


--
-- TOC entry 3222 (class 2606 OID 16486)
-- Name: alunos alunos_id_responsavel_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.alunos
    ADD CONSTRAINT alunos_id_responsavel_fkey FOREIGN KEY (id_responsavel) REFERENCES public.responsaveis(id_responsavel);


--
-- TOC entry 3223 (class 2606 OID 16491)
-- Name: turmas turmas_id_professor_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.turmas
    ADD CONSTRAINT turmas_id_professor_fkey FOREIGN KEY (id_professor) REFERENCES public.professores(id_professor);


--
-- TOC entry 3224 (class 2606 OID 16496)
-- Name: turmas turmas_id_disciplina_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.turmas
    ADD CONSTRAINT turmas_id_disciplina_fkey FOREIGN KEY (id_disciplina) REFERENCES public.disciplinas(id_disciplina);


--
-- TOC entry 3225 (class 2606 OID 16501)
-- Name: alunos_cursos alunos_cursos_id_aluno_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.alunos_cursos
    ADD CONSTRAINT alunos_cursos_id_aluno_fkey FOREIGN KEY (id_aluno) REFERENCES public.alunos(id_aluno);


--
-- TOC entry 3226 (class 2606 OID 16506)
-- Name: alunos_cursos alunos_cursos_id_curso_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.alunos_cursos
    ADD CONSTRAINT alunos_cursos_id_curso_fkey FOREIGN KEY (id_curso) REFERENCES public.cursos(id_curso);


--
-- TOC entry 3227 (class 2606 OID 16511)
-- Name: alunos_turmas alunos_turmas_id_aluno_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.alunos_turmas
    ADD CONSTRAINT alunos_turmas_id_aluno_fkey FOREIGN KEY (id_aluno) REFERENCES public.alunos(id_aluno);


--
-- TOC entry 3228 (class 2606 OID 16516)
-- Name: alunos_turmas alunos_turmas_id_turma_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.alunos_turmas
    ADD CONSTRAINT alunos_turmas_id_turma_fkey FOREIGN KEY (id_turma) REFERENCES public.turmas(id_turma);


--
-- TOC entry 3229 (class 2606 OID 16521)
-- Name: historicos historicos_id_aluno_turma_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.historicos
    ADD CONSTRAINT historicos_id_aluno_turma_fkey FOREIGN KEY (id_aluno_turma) REFERENCES public.alunos_turmas(id_aluno_turma);


--
-- TOC entry 3230 (class 2606 OID 16526)
-- Name: avaliacoes avaliacoes_id_aluno_turma_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.avaliacoes
    ADD CONSTRAINT avaliacoes_id_aluno_turma_fkey FOREIGN KEY (id_aluno_turma) REFERENCES public.historicos(id_aluno_turma);


--
-- Completed on 2025-10-02 02:00:13
--

--
-- PostgreSQL database dump complete
--