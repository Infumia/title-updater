import net.infumia.gradle.applyJava

applyJava()

dependencies {
    compileOnly(project(":nms-common"))
    compileOnly(libs.minecraft.one.ten.two.nms) { isTransitive = false }
}
