package net.infumia.gradle

import com.github.jengelman.gradle.plugins.shadow.ShadowPlugin
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import kotlin.io.path.absolutePathString
import org.gradle.api.Project
import org.gradle.kotlin.dsl.*

private val supportedVersions =
    listOf(
        "v1.8.8",
        "v1.9.4",
        "v1.10.2",
        "v1.11.2",
        "v1.12.2",
        "v1.13.2",
        "v1.14.4",
        "v1.15.2",
        "v1.16.5",
        "v1.17.1",
        "v1.18.2",
        "v1.19.4",
        "v1.20.6",
        "v1.21.1",
        "v1.21.3",
        "v1.21.4",
    )

fun Project.applyNms() {
    apply<ShadowPlugin>()

    tasks {
        withType<ShadowJar> {
            archiveClassifier.set("")

            dependsOn("jar")
            dependsOn(":nms-common:jar")

            from(zipTree(findJarFile("common")))
            nmsModuleToJar().forEach { (projectName, jarFile) ->
                dependsOn(projectName)
                from(zipTree(jarFile))
            }

            exclude("org/intellij/lang/annotations/**", "org/jetbrains/annotations/**")
        }

        named("build") { dependsOn("shadowJar") }
    }
}

private fun Project.nmsModuleToJar(): Map<String, String> =
    supportedVersions.associate { ":nms-$it:build" to findJarFile(it) }

private fun Project.nmsFolder() = rootProject.layout.projectDirectory.asFile.toPath().resolve("nms")

private fun Project.findJarFile(moduleName: String): String =
    nmsFolder()
        .resolve("$moduleName")
        .resolve("build")
        .resolve("libs")
        .resolve("nms-$moduleName-$version.jar")
        .absolutePathString()
