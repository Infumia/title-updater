plugins { alias(libs.plugins.paperweight) }

dependencies {
    compileOnly(project(":nms-common"))

    paperweight { paperDevBundle(libs.versions.minecraft.one.twenty.six) }
}

tasks {
    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(21)
            vendor = JvmVendorSpec.ADOPTIUM
        }
    }

    assemble { dependsOn(reobfJar) }
}
