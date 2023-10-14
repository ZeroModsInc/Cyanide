plugins {
    idea
    java
    id("gg.essential.loom") version "0.10.0.+"
    id("dev.architectury.architectury-pack200") version "0.1.3"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

val baseGroup: String by project
val mixinGroup = "$baseGroup.mixin"
val gameVersion: String by project
val gameForgeVersion: String by project
val gameMappings: String by project
val modId: String by project
val modName: String by project
val modVersion: String by project
val clientTweakClass: String by project
val mixinVersion: String by project
val mixinProcessorVersion: String by project
val lombokVersion: String by project
val clientMemory: String by project

val mixinsConfig = "mixins.$modId.json"
val mixinsRefmapConfig = "mixins.$modId.refmap.json"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(8))
}

loom {
    log4jConfigs.from(file("log4j2.xml"))
    launchConfigs {
        "client" {
            arg("-Xmx$clientMemory")
            arg("-Xms512M")
            property("mixin.debug", "true")
            property("asmhelper.verbose", "true")
            arg("--tweakClass", clientTweakClass)
        }
    }
    forge {
        pack200Provider.set(dev.architectury.pack200.java.Pack200Adapter())
        mixinConfig(mixinsConfig)
    }
    mixin {
        defaultRefmapName.set(mixinsRefmapConfig)
    }
}

sourceSets.main {
    output.setResourcesDir(sourceSets.main.flatMap { it.java.classesDirectory })
}

repositories {
    mavenCentral()
    maven("https://repo.spongepowered.org/maven/")
}

val shadowImpl: Configuration by configurations.creating {
    configurations.implementation.get().extendsFrom(this)
}

dependencies {
    minecraft("com.mojang:minecraft:$gameVersion")
    mappings("de.oceanlabs.mcp:mcp_stable:$gameMappings-$gameVersion")
    forge("net.minecraftforge:forge:$gameForgeVersion-$gameVersion")

    shadowImpl("org.spongepowered:mixin:$mixinVersion") {
        isTransitive = false
    }
    annotationProcessor("org.spongepowered:mixin:$mixinProcessorVersion")

    shadowImpl("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
}

tasks.withType(JavaCompile::class) {
    options.encoding = "UTF-8"
}

tasks.withType(Jar::class) {
    archiveBaseName.set(modId)
    manifest.attributes.run {
        this["FMLCorePluginContainsFMLMod"] = "true"
        this["ForceLoadAsMod"] = "true"
        this["TweakClass"] = clientTweakClass
        this["MixinConfigs"] = mixinsConfig
    }
}

tasks.processResources {
    inputs.property("modVersion", project.version)
    inputs.property("gameVersion", gameVersion)
    inputs.property("modId", modId)
    inputs.property("mixinGroup", mixinGroup)

    filesMatching(listOf("mcmod.info", mixinsConfig)) {
        expand(inputs.properties)
    }

    rename("(.+_at.cfg)", "META-INF/$1")
}


val remapJar by tasks.named<net.fabricmc.loom.task.RemapJarTask>("remapJar") {
    archiveClassifier.set("")
    from(tasks.shadowJar)
    input.set(tasks.shadowJar.get().archiveFile)
}

tasks.jar {
    archiveClassifier.set("without-deps")
    destinationDirectory.set(layout.buildDirectory.dir("badjars"))
}

tasks.shadowJar {
    destinationDirectory.set(layout.buildDirectory.dir("badjars"))
    archiveClassifier.set("all-dev")
    configurations = listOf(shadowImpl)
    doLast {
        configurations.forEach {
            println("Copying jars into mod: ${it.files}")
        }
    }

    // If you want to include other dependencies and shadow them, you can relocate them in here
    fun relocate(name: String) = relocate(name, "$baseGroup.deps.$name")
}

tasks.assemble.get().dependsOn(tasks.remapJar)

