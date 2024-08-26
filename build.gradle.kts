import net.infumia.gradle.applySpotless

plugins { java }

applySpotless()

subprojects {
    apply<JavaPlugin>()

    repositories {
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://repo.codemc.io/repository/nms/")
    }
}
