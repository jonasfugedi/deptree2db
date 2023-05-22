package com.tajjm.maven.deptree2db;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

class AppTest {

    @Test
    void main() throws IOException, URISyntaxException {
        var app = new App();
        URL res = getClass().getClassLoader().getResource("deptree.dot");
        File file = Paths.get(res.toURI()).toFile();
        String absolutePath = file.getAbsolutePath();
        app.main(new String[] { "234", absolutePath });
    }

    @Test
    void multi_dep() throws IOException, URISyntaxException {
        var app = new App();
        URL res = getClass().getClassLoader().getResource("multi_dep_graph.dot");
        File file = Paths.get(res.toURI()).toFile();
        String absolutePath = file.getAbsolutePath();
        app.main(new String[] { "567", absolutePath });
    }
}