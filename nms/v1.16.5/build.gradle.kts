import net.infumia.gradle.applyJava

applyJava()

dependencies {
    compileOnly(project(":nms-common"))
    compileOnly(libs.minecraft.one.sixteen.five.nms) { isTransitive = false }
}
