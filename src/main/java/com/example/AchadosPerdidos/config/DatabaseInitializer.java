package com.example.AchadosPerdidos.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private DataSource dataSource;

    @Override
    public void run(String... args) throws Exception {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            // Criar tabelas se não existirem
            String[] sqls = {
                "CREATE TABLE IF NOT EXISTS role (id BIGSERIAL PRIMARY KEY, nome VARCHAR(50) NOT NULL UNIQUE)",
                
                "CREATE TABLE IF NOT EXISTS usuario (id BIGSERIAL PRIMARY KEY, nome VARCHAR(100) NOT NULL, email VARCHAR(100) NOT NULL UNIQUE, senha VARCHAR(255) NOT NULL, telefone VARCHAR(20))",
                
                "CREATE TABLE IF NOT EXISTS usuario_roles (usuario_id BIGINT NOT NULL, roles_id BIGINT NOT NULL, PRIMARY KEY (usuario_id, roles_id), CONSTRAINT fk_usuario_roles_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id), CONSTRAINT fk_usuario_roles_role FOREIGN KEY (roles_id) REFERENCES role(id))",
                
                "CREATE TABLE IF NOT EXISTS itens (id BIGSERIAL PRIMARY KEY, titulo VARCHAR(100) NOT NULL, descricao TEXT NOT NULL, status VARCHAR(20), localizacao VARCHAR(100), imagem TEXT, user_name VARCHAR(100), latitude DOUBLE PRECISION, longitude DOUBLE PRECISION, data_registro TIMESTAMP, id_usuario BIGINT, CONSTRAINT fk_itens_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id))",
                
                "CREATE TABLE IF NOT EXISTS foto_item (id BIGSERIAL PRIMARY KEY, url_foto TEXT, id_item BIGINT NOT NULL, CONSTRAINT fk_foto_item_itens FOREIGN KEY (id_item) REFERENCES itens(id))",
                
                "CREATE TABLE IF NOT EXISTS historico_item (id BIGSERIAL PRIMARY KEY, descricao TEXT, data_alteracao TIMESTAMP, id_item BIGINT NOT NULL, CONSTRAINT fk_historico_item_itens FOREIGN KEY (id_item) REFERENCES itens(id))",
                
                "CREATE TABLE IF NOT EXISTS mensagem (id BIGSERIAL PRIMARY KEY, conteudo TEXT, data_envio TIMESTAMP, id_usuario_remetente BIGINT, id_usuario_destinatario BIGINT, id_item BIGINT, CONSTRAINT fk_mensagem_remetente FOREIGN KEY (id_usuario_remetente) REFERENCES usuario(id), CONSTRAINT fk_mensagem_destinatario FOREIGN KEY (id_usuario_destinatario) REFERENCES usuario(id), CONSTRAINT fk_mensagem_itens FOREIGN KEY (id_item) REFERENCES itens(id))",
                
                "CREATE TABLE IF NOT EXISTS notificacao (id BIGSERIAL PRIMARY KEY, titulo VARCHAR(255), mensagem TEXT, data_criacao TIMESTAMP, lida BOOLEAN, id_usuario BIGINT, id_item BIGINT, CONSTRAINT fk_notificacao_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id), CONSTRAINT fk_notificacao_itens FOREIGN KEY (id_item) REFERENCES itens(id))",
                
                "INSERT INTO role (nome) VALUES ('ROLE_ALUNO') ON CONFLICT(nome) DO NOTHING",
                
                "INSERT INTO role (nome) VALUES ('ROLE_ADMIN') ON CONFLICT(nome) DO NOTHING"
            };

            for (String sql : sqls) {
                try {
                    statement.execute(sql);
                } catch (Exception e) {
                    // Ignorar erros de criação se tabelas já existem
                }
            }

            System.out.println("✓ Banco de dados inicializado com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao inicializar banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
