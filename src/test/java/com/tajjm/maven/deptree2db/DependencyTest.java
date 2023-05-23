package com.tajjm.maven.deptree2db;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DependencyTest {
    @Test
    void toGAV() {
        String depText = "com.tajjm:cool-artefact:jar:osx-86_64:1.0.Final:compile";
        assertEquals("com.tajjm:cool-artefact:1.0.Final", new Dependency(depText).toGAV());
        depText = "com.google.protobuf:protobuf-java:jar:3.11.3:compile";
        assertEquals("com.google.protobuf:protobuf-java:3.11.3", new Dependency(depText).toGAV());
        depText = "io.opencensus:opencensus-impl:jar:0.24.0:runtime";
        assertEquals("io.opencensus:opencensus-impl:0.24.0", new Dependency(depText).toGAV());
        depText = "com.tajjm.demo.tracing:proto:jar:1.0-SNAPSHOT";
        assertEquals("com.tajjm.demo.tracing:proto:1.0-SNAPSHOT", new Dependency(depText).toGAV());
        depText = "io.netty:netty-resolver-dns-native-macos:jar:osx-x86_64:4.1.84.Final:compile";
        assertEquals("io.netty:netty-resolver-dns-native-macos:4.1.84.Final", new Dependency(depText).toGAV());
    }
}