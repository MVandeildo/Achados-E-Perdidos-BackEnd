-- Criar tabela role
CREATE TABLE IF NOT EXISTS role (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL UNIQUE
);

-- Criar tabela usuario
CREATE TABLE IF NOT EXISTS usuario (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    telefone VARCHAR(20)
);

-- Criar tabela usuario_roles
CREATE TABLE IF NOT EXISTS usuario_roles (
    usuario_id BIGINT NOT NULL,
    roles_id BIGINT NOT NULL,
    PRIMARY KEY (usuario_id, roles_id),
    CONSTRAINT fk_usuario_roles_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id),
    CONSTRAINT fk_usuario_roles_role FOREIGN KEY (roles_id) REFERENCES role(id)
);

-- Criar tabela itens
CREATE TABLE IF NOT EXISTS itens (
    id BIGSERIAL PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    descricao TEXT NOT NULL,
    status VARCHAR(20),
    localizacao VARCHAR(100),
    imagem LONGTEXT,
    user_name VARCHAR(100),
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    data_registro TIMESTAMP,
    id_usuario BIGINT,
    CONSTRAINT fk_itens_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id)
);

-- Criar tabela foto_item
CREATE TABLE IF NOT EXISTS foto_item (
    id BIGSERIAL PRIMARY KEY,
    url_foto LONGTEXT,
    id_item BIGINT NOT NULL,
    CONSTRAINT fk_foto_item_itens FOREIGN KEY (id_item) REFERENCES itens(id)
);

-- Criar tabela historico_item
CREATE TABLE IF NOT EXISTS historico_item (
    id BIGSERIAL PRIMARY KEY,
    descricao TEXT,
    data_alteracao TIMESTAMP,
    id_item BIGINT NOT NULL,
    CONSTRAINT fk_historico_item_itens FOREIGN KEY (id_item) REFERENCES itens(id)
);

-- Criar tabela mensagem
CREATE TABLE IF NOT EXISTS mensagem (
    id BIGSERIAL PRIMARY KEY,
    conteudo TEXT,
    data_envio TIMESTAMP,
    id_usuario_remetente BIGINT,
    id_usuario_destinatario BIGINT,
    id_item BIGINT,
    CONSTRAINT fk_mensagem_remetente FOREIGN KEY (id_usuario_remetente) REFERENCES usuario(id),
    CONSTRAINT fk_mensagem_destinatario FOREIGN KEY (id_usuario_destinatario) REFERENCES usuario(id),
    CONSTRAINT fk_mensagem_itens FOREIGN KEY (id_item) REFERENCES itens(id)
);

-- Criar tabela notificacao
CREATE TABLE IF NOT EXISTS notificacao (
    id BIGSERIAL PRIMARY KEY,
    titulo VARCHAR(255),
    mensagem TEXT,
    data_criacao TIMESTAMP,
    lida BOOLEAN,
    id_usuario BIGINT,
    id_item BIGINT,
    CONSTRAINT fk_notificacao_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id),
    CONSTRAINT fk_notificacao_itens FOREIGN KEY (id_item) REFERENCES itens(id)
);

-- Inserir roles padrões se não existirem
INSERT INTO role (nome) VALUES ('ROLE_ALUNO') ON CONFLICT(nome) DO NOTHING;
INSERT INTO role (nome) VALUES ('ROLE_ADMIN') ON CONFLICT(nome) DO NOTHING;
