-- Livro 1: O Pequeno Príncipe
INSERT INTO livro (
    id,
    isbn,
    titulo,
    data_publicacao,
    genero,
    nome_autor,
    nacionalidade_autor,
    imagem_capa,
    pdf,
    habilitado
)
SELECT
    101,
    '978-3-16-148410-0',
    'O Pequeno Príncipe',
    '1943-04-06',
    'FICCAO',
    'Antoine de Saint-Exupéry',
    'Francesa',
    NULL,
    NULL,
    true
WHERE NOT EXISTS (SELECT 1 FROM livro WHERE id = 101);

-- Livro 2: 1984
INSERT INTO livro (
    id,
    isbn,
    titulo,
    data_publicacao,
    genero,
    nome_autor,
    nacionalidade_autor,
    imagem_capa,
    pdf,
    habilitado
)
SELECT
    102,
    '978-0-452-28423-4',
    '1984',
    '1949-06-08',
    'BIOGRAFIA',
    'George Orwell',
    'Britânica',
    NULL,
    NULL,
    true
WHERE NOT EXISTS (SELECT 1 FROM livro WHERE id = 102);