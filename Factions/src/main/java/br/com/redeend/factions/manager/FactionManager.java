package br.com.redeend.factions.manager;

import br.com.redeend.factions.mysql.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class FactionManager {

    private final MySQL mySQL;
    private final Map<String, FactionInfo> factions;
    private final Map<String, String> invites;
    private final Map<String, String> playerFactions = new HashMap<>();

    public FactionManager(MySQL mySQL) {
        this.mySQL = mySQL;
        this.factions = new HashMap<>();
        this.invites = new HashMap<>();
    }

    public void createFaction(String tag, String name, String leader) throws Exception {
        tag = tag.toUpperCase();
        if (!tag.matches("[A-Z]{3}")) {
            throw new Exception("Tag deve ter exatamente 3 letras.");
        }

        try (Connection connection = mySQL.getConnection()) {
            String checkQuery = "SELECT * FROM player_factions WHERE tag = ?";
            try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
                checkStmt.setString(1, tag);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next()) {
                        throw new Exception("A tag já está em uso.");
                    }
                }
            }

            String insertQuery = "INSERT INTO player_factions (jogador, tag, nome, rank) VALUES (?, ?, ?, ?)";
            try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                insertStmt.setString(1, leader);
                insertStmt.setString(2, tag);
                insertStmt.setString(3, name);
                insertStmt.setString(4, "LIDER");
                insertStmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new Exception("Erro ao criar a facção: " + e.getMessage());
        }
    }
    public void invitePlayer(String inviter, String invitee) throws Exception {
        try (Connection connection = mySQL.getConnection()) {
            // Obtém a tag da facção do jogador que está convidando
            String selectQuery = "SELECT tag, nome FROM player_factions WHERE jogador = ?";
            String tag;
            String nome;
            try (PreparedStatement selectStmt = connection.prepareStatement(selectQuery)) {
                selectStmt.setString(1, inviter);
                try (ResultSet rs = selectStmt.executeQuery()) {
                    if (rs.next()) {
                        tag = rs.getString("tag");
                        nome = rs.getString("nome");
                    } else {
                        throw new Exception("Você não está em uma facção.");
                    }
                }
            }

            // Verifica se o jogador convidado está online
            Player inviteePlayer = Bukkit.getPlayer(invitee);
            if (inviteePlayer == null) {
                throw new Exception("Jogador não encontrado.");
            }

            // Insere o convite na tabela faction_invites
            String inviteQuery = "INSERT INTO faction_invites (invitee, tag) VALUES (?, ?)";
            try (PreparedStatement inviteStmt = connection.prepareStatement(inviteQuery)) {
                inviteStmt.setString(1, invitee);
                inviteStmt.setString(2, tag);
                inviteStmt.executeUpdate();
            }

            // Envia a mensagem ao jogador convidado
            inviteePlayer.sendMessage(ChatColor.GREEN + inviter + " convidou você para a facção [" + tag + "] " + nome + ".\nDigite " + ChatColor.WHITE + "'/f entrar " + tag + "'" + ChatColor.GREEN + " para entrar na facção.");
        } catch (SQLException e) {
            throw new Exception("Erro ao convidar jogador: " + e.getMessage());
        }
    }

    public void joinFaction(String player, String tag) throws Exception {
        try (Connection connection = mySQL.getConnection()) {
            System.out.println("Tentando entrar na facção: " + tag + " para o jogador: " + player);

            // Converte a tag para maiúsculas
            String upperTag = tag.toUpperCase();

            // Verifique se o convite existe
            String checkQuery = "SELECT * FROM faction_invites WHERE invitee = ? AND tag = ?";
            try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
                checkStmt.setString(1, player);
                checkStmt.setString(2, upperTag);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println("Nenhum convite encontrado para o jogador: " + player + " na facção: " + upperTag);
                        throw new Exception("Você não foi convidado para esta facção.");
                    }
                }
            }

            // Obter o nome da facção
            String getNameQuery = "SELECT nome FROM player_factions WHERE tag = ? LIMIT 1";
            String factionName;
            try (PreparedStatement getNameStmt = connection.prepareStatement(getNameQuery)) {
                getNameStmt.setString(1, upperTag);
                try (ResultSet rs = getNameStmt.executeQuery()) {
                    if (rs.next()) {
                        factionName = rs.getString("nome");
                    } else {
                        throw new Exception("Facção não encontrada.");
                    }
                }
            }

            // Adicionar jogador à facção
            String insertQuery = "INSERT INTO player_factions (jogador, tag, nome, rank) VALUES (?, ?, ?, 'MEMBRO')";
            try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                insertStmt.setString(1, player);
                insertStmt.setString(2, upperTag);
                insertStmt.setString(3, factionName);
                insertStmt.executeUpdate();
            }

            // Remover o convite
            String deleteInviteQuery = "DELETE FROM faction_invites WHERE invitee = ? AND tag = ?";
            try (PreparedStatement deleteStmt = connection.prepareStatement(deleteInviteQuery)) {
                deleteStmt.setString(1, player);
                deleteStmt.setString(2, upperTag);
                deleteStmt.executeUpdate();
            }

            System.out.println("Jogador " + player + " entrou na facção " + upperTag + " com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro ao entrar na facção: " + e.getMessage());
            throw new Exception("Erro ao entrar na facção: " + e.getMessage());
        }
    }




    public void addPlayerToFaction(String tag, String player, String rank) throws SQLException {
        String query = "INSERT INTO player_factions (jogador, tag, rank) VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE rank = VALUES(rank)"; // Atualiza o rank se o jogador já estiver na facção
        try (Connection connection = mySQL.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, player);
            statement.setString(2, tag);
            statement.setString(3, rank);
            statement.executeUpdate();
        }
    }
    public String getFactionTag(String playerName) throws SQLException {
        String query = "SELECT tag FROM player_factions WHERE jogador = ?";
        try (Connection connection = mySQL.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, playerName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("tag");
            } else {
                return null;
            }
        }
    }
    public String getNameFactions(String playerName) throws SQLException {
        String query = "SELECT nome FROM player_factions WHERE jogador = ?";
        try (Connection connection = mySQL.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, playerName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("nome");
            } else {
                return null;
            }
        }
    }
    public boolean isPlayerInFaction(String playerName) {
        String query = "SELECT 1 FROM factions WHERE player = ?";
        try (Connection connection = mySQL.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, playerName);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // Se existir um resultado, o jogador está em uma facção
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Em caso de erro, retornar false
        }
    }

    public void leaveFaction(String player) {
        String query = "DELETE FROM player_factions WHERE jogador = ?";
        try (Connection connection = mySQL.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, player);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void removePlayer(String remover, String player) {
        try {
            String removerFactionTag = getFactionTag(remover);
            String playerFactionTag = getFactionTag(player);
            if (removerFactionTag == null || !removerFactionTag.equals(playerFactionTag)) {
                throw new IllegalArgumentException("O jogador que está removendo não pertence à mesma facção do jogador a ser removido.");
            }
            if (remover.equals(player)) {
                throw new IllegalArgumentException("O jogador não pode se remover da facção.");
            }
            leaveFaction(player);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void disbandFaction(String disbander) {
        try {
            String factionTag = getFactionTag(disbander);
            if (factionTag == null) {
                throw new IllegalArgumentException("O jogador não pertence a nenhuma facção.");
            }

            String query = "DELETE FROM player_factions WHERE tag = ?";
            try (Connection connection = mySQL.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, factionTag);
                statement.executeUpdate();
            }

            factions.remove(factionTag);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getPlayerFaction(String playerName) {
        return playerFactions.get(playerName);
    }

}
