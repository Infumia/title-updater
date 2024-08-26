import net.infumia.gradle.applyJava

applyJava()

dependencies { compileOnly(libs.minecraft.one.eight.eight) { isTransitive = false } }
