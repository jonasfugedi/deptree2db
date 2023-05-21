package com.tajjm.maven.deptree2db;

public class Dependency {
    private final String group;
    private final String artefact;
    private final String version;
    private final String type;
    private final String scope;

    public Dependency(String text) {
        var parts = text.replaceAll("\"", "").split(":");
        if (parts.length == 4 || parts.length == 5) {
            group = parts[0];
            artefact = parts[1];
            type = parts[2];
            version = parts[3];
            scope = parts.length == 5 ? parts[4] : null;
        } else {
            throw new RuntimeException("Bad dependency format: " + text);
        }
    }

    public String getGroup() {
        return group;
    }

    public String getArtefact() {
        return artefact;
    }

    public String getVersion() {
        return version;
    }

    public String getType() {
        return type;
    }

    public String getScope() {
        return scope;
    }

    public String toGAV() {
        return group + ":" + artefact + ":" + version;
    }

    @Override
    public String toString() {
        return "Dependency{" +
                "group='" + group + '\'' +
                ", artefact='" + artefact + '\'' +
                ", version='" + version + '\'' +
                ", type='" + type + '\'' +
                ", scope='" + scope + '\'' +
                '}';
    }
}
