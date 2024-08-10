package br.com.redeend.factions.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQL {

    private final String host, database, username, password;
    private final int port;
    private Connection connection;

    public MySQL(String host, int port, String database, String username, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    public void connect() {
        try {
            if (connection != null && !connection.isClosed()) {
                return;
            }
            synchronized (this) {
                if (connection != null && !connection.isClosed()) {
                    return;
                }

                connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
                //System.out.println("Conectado ao banco de dados com sucesso.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao MySQL: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Desconectado do banco de dados com sucesso.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao desconectar do MySQL: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connect();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao obter a conexão do MySQL: " + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }

    public void createTables() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connect();
        }

        // Criação da tabela player_factions
        String playerFactionsQuery = "CREATE TABLE IF NOT EXISTS player_factions (" +
                "jogador VARCHAR(16) NOT NULL, " +
                "tag VARCHAR(3) NOT NULL, " +
                "nome VARCHAR(32) NOT NULL, " +
                "rank VARCHAR(10) NOT NULL, " +
                "PRIMARY KEY (jogador, tag))"; // Chave primária composta

        // Criação da tabela faction_invites
        String factionInvitesQuery = "CREATE TABLE IF NOT EXISTS faction_invites (" +
                "invitee VARCHAR(16) NOT NULL, " +
                "tag VARCHAR(3) NOT NULL, " +
                "PRIMARY KEY (invitee, tag))"; // Chave primária composta

        try (Statement statement = connection.createStatement()) {
            // Executa a criação ou verificação das tabelas
            statement.executeUpdate(playerFactionsQuery);
            System.out.println("Tabela 'player_factions' criada/verificada com sucesso.");

            statement.executeUpdate(factionInvitesQuery);
            System.out.println("Tabela 'faction_invites' criada/verificada com sucesso.");
        } catch (SQLException e) {
            System.err.println("Erro ao criar as tabelas: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
