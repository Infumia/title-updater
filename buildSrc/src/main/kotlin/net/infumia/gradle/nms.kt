package net.infumia.gradle

import com.github.jengelman.gradle.plugins.shadow.ShadowPlugin
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import kotlin.io.path.absolutePathString
import kotlin.io.path.notExists
import org.gradle.api.Project
import org.gradle.kotlin.dsl.*

fun Project.applyNms() {
    apply<ShadowPlugin>()

    tasks {
        withType<ShadowJar> {
            archiveClassifier.set("")

            dependsOn("jar")
            dependsOn(":nms-common:jar")

            from(zipTree(findNmsJarFile("common")))
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
    rootProject.subprojects
        .map { it.name }
        .filter { it.startsWith("nms-") }
        .minus("nms-common")
        .associate { ":$it:build" to findNmsJarFile(it.removePrefix("nms-")) }

private fun Project.nmsFolder() = rootProject.layout.projectDirectory.asFile.toPath().resolve("nms")

private fun Project.findNmsJarFile(moduleName: String): String {
    val libsPath = nmsFolder().resolve(moduleName).resolve("build").resolve("libs")
    var path = libsPath.resolve("nms-$moduleName-$version-reobj.jar")
    if (path.notExists()) {
        path = libsPath.resolve("nms-$moduleName-$version.jar")
    }
    return path.absolutePathString()
}
