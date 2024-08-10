package br.com.redeend.factions.zone;

public enum ZoneType {

    ALIADA(""),
    INIMIGA(""),
    NEUTRA(""),
    LIVRE("§aZona Livre"),
    GUERRA("§cZona de Guerra"),
    PROTEGIDA("§6Zona Protegida");


    private final String name;
    ZoneType(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
