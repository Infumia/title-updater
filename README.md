# title updater
[![Maven Central Version](https://img.shields.io/maven-central/v/net.infumia/title-updater)](https://central.sonatype.com/artifact/net.infumia/title-updater)
## How to Use (Developers)
### Gradle
```groovy
repositories {
    mavenCentral()
}

dependencies {
    // Base module
    implementation "net.infumia:title-updater:VERSION"
}
```
### Code
```java
void run(final Player player) {
    TitleUpdater.update(player, "New Title");
    TitleUpdater.update(player, Component.text("New title"));
    
    ComponentSupport.deserializer(Minimessage.miniMessage());
 
    TitleUpdater.update(player, "<white>New Title");
}
```
