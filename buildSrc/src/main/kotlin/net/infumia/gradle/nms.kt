package net.infumia.gradle

import com.github.jengelman.gradle.plugins.shadow.ShadowPlugin
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import kotlin.io.path.absolutePathString
import org.gradle.api.Project
import org.gradle.kotlin.dsl.*

private val supportedVersions =
    listOf(
        "1.8.8",
        "1.9.4",
        "1.10.2",
        "1.11.2",
        "1.12.2",
        "1.13.2",
        "1.14.4",
        "1.15.2",
        "1.16.5",
        "1.17.1",
        "1.18.2",
        "1.19.4",
        "1.20.6",
        "1.21",
    )

fun Project.applyNms() {
    apply<ShadowPlugin>()

    tasks {
        withType<ShadowJar> {
            archiveClassifier.set("")

            dependsOn("jar")
            dependsOn(":nms-common:jar")

            nmsModuleToJar().forEach { (projectName, jarFile) ->
                println(projectName)
                dependsOn(projectName)
                from(zipTree(jarFile))
            }

            exclude("org/intellij/lang/annotations/**", "org/jetbrains/annotations/**")
        }

        named("build") { dependsOn("shadowJar") }
    }
}

private fun Project.nmsModuleToJar(): Map<String, String> =
    supportedVersions.associate { ":nms-v$it:build" to findJarFile(it) }

private fun Project.nmsFolder() = rootProject.layout.projectDirectory.asFile.toPath().resolve("nms")

private fun Project.findJarFile(moduleName: String): String =
    nmsFolder()
        .resolve("v$moduleName")
        .resolve("build")
        .resolve("libs")
        .resolve("nms-v$moduleName-$version.jar")
        .absolutePathString()
