import net.infumia.gradle.applyNms
import net.infumia.gradle.applyPublish

applyPublish()

applyNms()

dependencies { implementation(project(":nms-common")) }
