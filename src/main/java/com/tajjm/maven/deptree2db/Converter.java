package com.tajjm.maven.deptree2db;

import com.tajjm.maven.MavenDotBaseListener;
import com.tajjm.maven.MavenDotParser;

import java.util.*;

public class Converter extends MavenDotBaseListener {
    public static final char SEPARATOR = ',';

    private final Map<String, List<Dependency>> map = new HashMap<>();
    private final String runId;

    private Dependency from;
    private Dependency to;
    private boolean isFrom;

    public Converter(String runId) {
        this.runId = runId;
    }

    @Override
    public void enterId_(MavenDotParser.Id_Context ctx) {
        Dependency dependency = new Dependency(ctx.getText());
        if (isFrom) {
            from = dependency;
        } else {
            to = dependency;
        }
        super.enterId_(ctx);
    }

    @Override
    public void exitId_(MavenDotParser.Id_Context ctx) {
        //System.out.println(ctx.getText());
        super.exitId_(ctx);
    }

    @Override
    public void enterEdge_stmt(MavenDotParser.Edge_stmtContext ctx) {
        isFrom = true;
        super.enterEdge_stmt(ctx);
    }

    @Override
    public void exitEdge_stmt(MavenDotParser.Edge_stmtContext ctx) {
        System.out.println(toCSV());
        var list = map.computeIfAbsent(from.toGAV(), s -> new ArrayList<>());
        list.add(to);
        super.exitEdge_stmt(ctx);
    }
    String toCSV() {
        return runId
                + SEPARATOR + from.getGroup() + SEPARATOR + from.getArtefact() + SEPARATOR + from.getVersion()
                + SEPARATOR + to.getGroup() + SEPARATOR + to.getArtefact() + SEPARATOR + to.getVersion();
    }
    @Override
    public void enterEdgeRHS(MavenDotParser.EdgeRHSContext ctx) {
        isFrom = false;
        super.enterEdgeRHS(ctx);
    }

    public Map<String, List<Dependency>> getMap() {
        return map;
    }
}
