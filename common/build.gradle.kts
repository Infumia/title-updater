import net.infumia.gradle.applyNms
import net.infumia.gradle.applyPublish

applyPublish()

applyNms()

dependencies {
    compileOnly(libs.minecraft.one.eight.eight) { isTransitive = false }
    compileOnly(libs.adventure.api)
    compileOnly(libs.adventure.legacy)
    compileOnly(libs.adventure.gson)

    implementation(project(":nms-common"))
}
