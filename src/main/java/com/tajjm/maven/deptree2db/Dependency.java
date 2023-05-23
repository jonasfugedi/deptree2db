package com.tajjm.maven.deptree2db;

public class Dependency {
    private final String group;
    private final String artefact;
    private final String version;
    private final String type;
    private final String scope;
    private final String classifier;

    public Dependency(String text) {
        var parts = text.replaceAll("\"", "").split(":");
        if (parts.length == 4) {
            group = parts[0];
            artefact = parts[1];
            type = parts[2];
            classifier = null;
            version = parts[3];
            scope = null;
        } else if (parts.length == 5) {
            group = parts[0];
            artefact = parts[1];
            type = parts[2];
            classifier = null;
            version = parts[3];
            scope =  parts[4];
        } else if (parts.length == 6) {
            group = parts[0];
            artefact = parts[1];
            type = parts[2];
            classifier = parts[3];
            version = parts[4];
            scope =  parts[5];
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
                ", classifier='" + classifier + '\'' +
                '}';
    }
}
