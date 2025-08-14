plugins { alias(libs.plugins.paperweight) }

dependencies {
    compileOnly(project(":nms-common"))

    paperweight { paperDevBundle(libs.versions.minecraft.one.nineteen.four) }
}

tasks {
    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(17)
            vendor = JvmVendorSpec.ADOPTIUM
        }
    }

    assemble { dependsOn(reobfJar) }
}
