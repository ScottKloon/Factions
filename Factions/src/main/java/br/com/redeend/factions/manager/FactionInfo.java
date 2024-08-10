package br.com.redeend.factions.manager;

import java.util.ArrayList;
import java.util.List;

public class FactionInfo {

    private final String tag;
    private final String name;
    private final List<String> members;

    public FactionInfo(String tag, String name, String leader) {
        this.tag = tag;
        this.name = name;
        this.members = new ArrayList<>();
        this.members.add(leader);
    }

    public String getTag() {
        return tag;
    }

    public String getName() {
        return name;
    }

    public List<String> getMembers() {
        return members;
    }

    public void addMember(String player) {
        members.add(player);
    }

    public void removeMember(String player) {
        members.remove(player);
    }
}