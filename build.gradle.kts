import net.infumia.gradle.applySpotless

plugins { java }

applySpotless()

subprojects {
    apply<JavaPlugin>()

    repositories {
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://repo.codemc.io/repository/nms/")
    }
}
