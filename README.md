Bibliotech API
API backend para o sistema Bibliotech, uma plataforma para gerenciamento e consulta de livros, autores e arquivos relacionados.

Sobre
Esta API foi desenvolvida usando Spring Boot com suporte a upload de arquivos (imagens e PDFs), autenticação via JWT, persistência com PostgreSQL, e envio de e-mails via SMTP Gmail.

Ela permite o cadastro, alteração, listagem e exclusão de livros, além de gestão de usuários.

Repositório no GitHub
Você pode obter o código fonte clonando o repositório oficial:

bash
Copiar
Editar
git clone https://github.com/Davialves22/bibliotech-backend.git
Requisitos
Java 17 ou superior

Maven (para buildar o projeto)

PostgreSQL rodando localmente (ou acessível)

Conta Gmail para configuração do SMTP (envio de e-mails)

Node.js + React (frontend separado, não incluso aqui)

Configuração
Banco de dados
Configure o PostgreSQL com as seguintes informações:

Banco: bibliotech_db

Porta: 5438

Usuário: postgres (pode ser alterado)

Senha: root (pode ser alterado)

Se necessário, altere estas configurações no arquivo application.yml na seção datasource.

Configuração de variáveis sensíveis
Crie um arquivo .env (não versionado) na raiz do projeto com:

ini
Copiar
Editar
EMAIL_USERNAME=seu.email@gmail.com
EMAIL_PASSWORD=sua_senha_app
Essas variáveis são usadas para o envio de e-mails via Gmail SMTP.

Upload de arquivos
No arquivo application.yml, há a configuração:

yaml
Copiar
Editar
file:
  upload_dir: E:/Projetos-SpringBoot/ihc/bibliotech-backend/src/main/java/com/br/bibliotech/Uploads
IMPORTANTE: Antes de rodar a aplicação, ajuste esse caminho para apontar para a pasta onde você quer que os arquivos enviados (imagens, PDFs) sejam armazenados em seu sistema local.

Exemplo para Windows:

yaml
Copiar
Editar
file:
  upload_dir: C:/Users/SeuUsuario/Projetos/Bibliotech/Uploads
Exemplo para Linux/Mac:

yaml
Copiar
Editar
file:
  upload_dir: /home/seuusuario/projetos/bibliotech/Uploads
Garanta que a pasta exista e que o processo Java tenha permissão para leitura e escrita nela.

Outras configurações importantes
JWT secret e expiração estão configurados em:

yaml
Copiar
Editar
jwt:
  secret: sua_chave_secreta_aqui
  expiration: 3600000 # 1 hora
Limites de upload são configurados para permitir até 500MB por arquivo e por requisição.

Como executar o backend
Clone o repositório:

bash
Copiar
Editar
git clone https://github.com/Davialves22/bibliotech-backend.git
cd bibliotech-backend
Ajuste o arquivo application.yml conforme as instruções acima.

Configure o banco PostgreSQL e crie o banco bibliotech_db.

Crie o arquivo .env com as credenciais do Gmail para envio de e-mail.

Rode a aplicação com Maven:

bash
Copiar
Editar
./mvnw spring-boot:run
Ou, caso tenha o Maven instalado:

bash
Copiar
Editar
mvn spring-boot:run
A API estará disponível em http://localhost:8080.

Testando a API
Você pode usar ferramentas como Postman ou Insomnia para testar os endpoints.

Exemplos:

POST /login para autenticação (recebe JWT)

GET /api/livro/v1 para listar livros

POST /api/livro/v1 para cadastrar livros (precisa token JWT)

Frontend
O frontend React está em projeto separado (não incluso aqui). Configure o CORS para permitir chamadas da URL do frontend (ex: http://localhost:3000).

Suporte
Dúvidas ou problemas, abra uma issue no GitHub ou entre em contato.

Licença
MIT License

