plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.3"
}

group = "dev.igoyek"
version = "1.0.4"
description = "Chat manager for FlameCode studio."

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
    toolchain.languageVersion.set(JavaLanguageVersion.of(11))
}

bukkit {
    name = "FlameChat"
    main = "dev.igoyek.flamechat.ChatPlugin"
    version = project.version as String
    apiVersion = "1.13"
    description = project.description
    authors = listOf("igoyek", "FlameCode.pl")
    website = "https://flamecode.pl"
    commands {
        create("chat") {
            description = "A main command of FlameChat plugin"
            aliases = listOf("flamechat")
            permission = "flamechat.command.chat"
        }
        create("adminchat") {
            description = "Communication for staff members"
            aliases = listOf("ac")
            permission = "flamechat.adminchat"
        }
    }
}

tasks {
    shadowJar {
        relocate("org.bstats", "dev.igoyek.flamechat.metrics")
    }
    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
}

repositories {
    mavenCentral()
    mavenLocal()

    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.codemc.org/repository/maven-public/")
}

dependencies {
    // spigot api
    compileOnly("org.spigotmc:spigot-api:1.20.4-R0.1-SNAPSHOT")

    // bstats
    implementation("org.bstats:bstats-bukkit:3.0.2")
}