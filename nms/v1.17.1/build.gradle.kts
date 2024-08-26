plugins { alias(libs.plugins.paperweight) }

dependencies {
    compileOnly(project(":nms-common"))

    paperweight { paperDevBundle(libs.versions.minecraft.one.seventeen.one) }
}

tasks {
    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(17)
            vendor = JvmVendorSpec.ADOPTIUM
        }
    }

    reobfJar { outputJar = layout.buildDirectory.file("libs/${project.name}.jar") }

    assemble { dependsOn(reobfJar) }
}
