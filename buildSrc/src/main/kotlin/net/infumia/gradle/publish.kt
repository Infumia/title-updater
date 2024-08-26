package net.infumia.gradle

import com.vanniktech.maven.publish.MavenPublishBaseExtension
import com.vanniktech.maven.publish.MavenPublishPlugin
import com.vanniktech.maven.publish.SonatypeHost
import com.vanniktech.maven.publish.tasks.JavadocJar
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.*

fun Project.applyPublish(moduleName: String? = null, javaVersion: Int = 8) {
    applyJava(javaVersion)
    apply<MavenPublishPlugin>()

    val projectName = "title-updater${if (moduleName == null) "" else "-$moduleName"}"
    val signRequired = project.hasProperty("sign-required")

    val sourceSets = extensions.getByType<JavaPluginExtension>().sourceSets
    tasks.register("sourcesJar", Jar::class) {
        dependsOn("classes")
        archiveClassifier.set("sources")
        from(sourceSets["main"].allSource)
    }

    tasks.withType<JavadocJar> { afterEvaluate { archiveBaseName = name } }

    extensions.configure<MavenPublishBaseExtension> {
        coordinates(project.group.toString(), projectName, project.version.toString())
        publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL, true)
        if (signRequired) {
            signAllPublications()
        }

        pom {
            name = projectName
            description = "A simpe inventory title updater for bukkit with adventure support."
            url = "https://github.com/Infumia/title-updater/"
            licenses {
                license {
                    name = "MIT License"
                    url = "https://mit-license.org/license.txt"
                }
            }
            developers {
                developer {
                    id = "portlek"
                    name = "Hasan Demirtaş"
                    email = "utsukushihito@outlook.com"
                }
            }
            scm {
                connection = "scm:git:git://github.com/infumia/title-updater.git"
                developerConnection = "scm:git:ssh://github.com/infumia/title-updater.git"
                url = "https://github.com/infumia/title-updater/"
            }
        }
    }
}
