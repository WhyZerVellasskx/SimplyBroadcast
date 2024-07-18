@file:Suppress("VulnerableLibrariesLocal")

import net.minecrell.pluginyml.paper.PaperPluginDescription

plugins {
    kotlin("jvm") version "2.0.0"
    id("xyz.jpenilla.run-paper") version "2.3.0"
    id("net.minecrell.plugin-yml.paper") version "0.6.0"
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.xenondevs.xyz/releases")
    maven("https://repo.panda-lang.org/releases")
    maven("https://jitpack.io")
    maven("https://repo.codemc.io/repository/maven-public/")
    maven("https://repo.codemc.io/repository/maven-snapshots/")
}

dependencies {
    val guiceVersion = "7.0.0"
    val configurateVersion = "4.1.2"
    val adventureVersion = "4.3.3"

    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
    compileOnly("me.lucko:helper:5.6.14")

    // kotlin
    library(kotlin("stdlib"))
    library(kotlin("reflect"))


    // guice
    library("com.google.inject:guice:$guiceVersion")
    library("com.google.inject.extensions:guice-assistedinject:$guiceVersion")

    // configurate
    library("org.spongepowered:configurate-yaml:$configurateVersion")
    library("org.spongepowered:configurate-extra-kotlin:$configurateVersion")

    // adventure
    library("net.kyori:adventure-platform-bukkit:$adventureVersion")
    library("net.kyori:adventure-text-serializer-bungeecord:$adventureVersion")
    library("net.kyori:adventure-text-minimessage:4.17.0")

    library("dev.rollczi:litecommands-bukkit:3.4.2")
    library("io.github.blackbaroness:duration-serializer:2.0.2")
    library("it.unimi.dsi:fastutil:8.5.13")
    library("io.github.blackbaroness:fastutil-extender-common:1.2.0")
    implementation(kotlin("stdlib-jdk8"))
}

kotlin {
    jvmToolchain(21)
}

tasks.compileKotlin.configure {
    compilerOptions.javaParameters = true
}

paper {
    version = "SNAPSHOT"
    main = "io.whyzervellasskx.simplybroadcast.bootstrap.Bootstrap"
    loader = "io.whyzervellasskx.simplybroadcast.Loader"
    apiVersion = "1.20"
    author = "WhyZerVellasskx"
    generateLibrariesJson = true
    hasOpenClassloader = false

    serverDependencies {
        register("helper") { load = PaperPluginDescription.RelativeLoadOrder.BEFORE }
    }
}

tasks.runServer {
    minecraftVersion("1.20.4")

    downloadPlugins {
        url("https://ci.lucko.me/job/helper/lastSuccessfulBuild/artifact/helper/target/helper.jar")
        modrinth("essentialsx", "2.20.1")
    }

    @Suppress("USELESS_ELVIS")
            jvmArgs = (jvmArgs ?: listOf())
                    .plus("-DPaper.IgnoreJavaVersion=true")
                    .plus("-Dfile.encoding=UTF-8")
                    .plus("-DIReallyKnowWhatIAmDoingISwear")
                    .plus("-Dcom.mojang.eula.agree=true")
}
