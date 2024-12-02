plugins { id("org.gradle.toolchains.foojay-resolver-convention") version "0.9.0" }

rootProject.name = "title-updater"

include("common")

registerNmsModules(
    "common",
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
)

private fun registerNmsModules(vararg modules: String) {
    modules.forEach { registerInnerModule("nms", it) }
}

private fun registerInnerModule(vararg paths: String) {
    val moduleName = paths.joinToString("-")
    include(moduleName)
    project(":$moduleName").projectDir = file(paths.joinToString("/"))
}
