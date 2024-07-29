plugins {
    `java-library`
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
}

group = "dev.igoyek"
version = "2.0.0"
description = "Chat manager for FlameCode studio."

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
    java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

bukkit {
    name = "FlameChat"
    main = "dev.igoyek.flamechat.ChatPlugin"
    version = project.version as String
    apiVersion = "1.16"
    description = project.description
    prefix = "FlameChat"
    author = "igoyek"
    commands {
        create("chat") {
            description = "A main command of FlameChat plugin"
            permission = "flamechat.command.chat"
        }
        create("adminchat") {
            description = "Communication for staff members"
            aliases = listOf("ac")
            permission = "flamechat.adminchat"
        }
    }
}

repositories {
    mavenCentral()
    mavenLocal()

    maven { url = uri("https://repo.papermc.io/repository/maven-public/") }
    maven { url = uri("https://storehouse.okaeri.eu/repository/maven-public/") }
    maven { url = uri("https://repo.codemc.org/repository/maven-public/") }
    maven { url = uri("https://repo.panda-lang.org/releases") }
    maven { url = uri("https://repo.eternalcode.pl/releases") }
}

dependencies {
    // configs
    val okaeriConfigsVersion = "5.0.1"
    implementation("eu.okaeri:okaeri-configs-yaml-bukkit:${okaeriConfigsVersion}")
    implementation("eu.okaeri:okaeri-configs-serdes-bukkit:${okaeriConfigsVersion}")

    // kyori
    implementation("net.kyori:adventure-platform-bukkit:4.3.2")

    // database
    implementation("com.zaxxer:HikariCP:5.1.0")

    // panda-utilities & expressible
    implementation("org.panda-lang:panda-utilities:0.5.2-alpha")
    implementation("org.panda-lang:expressible:1.3.6")

    // gitcheck
    implementation("com.eternalcode:gitcheck:1.0.0")

    // papermc
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
    compileOnly("org.spigotmc:spigot-api:1.20.4-R0.1-SNAPSHOT")
    api("io.papermc:paperlib:1.0.8")

    // bstats
    implementation("org.bstats:bstats-bukkit:3.0.2")

    // jetbrains annotations
    api("org.jetbrains:annotations:24.1.0")
}

tasks {
    shadowJar {
        archiveFileName.set("FlameChat v${project.version}.jar")

        exclude(
            "org/intellij/lang/annotations/**",
            "org/jetbrains/annotations/**",
            "META-INF/**",
            "javax/**",
        )

        mergeServiceFiles()
        minimize()

        val prefix = "dev.igoyek.flamechat.libs"
        listOf(
            "panda",
            "org.panda_lang",
            "net.dzikoysk",
            "dev.rollczi",
            "org.bstats"
        ).forEach { relocate(it, prefix) }
    }
    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
}