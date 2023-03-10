val MAVEN_REPOSITORY_URL = "https://maven.aliyun.com/repository/central"
val JCENTER_REPOSITORY_URL = "https://maven.aliyun.com/repository/jcenter"
val GOOGLE_REPOSITORY_URL = "https://maven.aliyun.com/repository/google"
val GRADLE_PLUGIN_REPOSITORY_URL = "https://maven.aliyun.com/repository/gradle-plugin"
gradle.settingsEvaluated {
    pluginManagement {
// Print repositories collection
        println("Plugins Repositories names: " + repositories.names)

        // Clear repositories collection
        repositories.clear()

        // Add my Artifactory mirror
        repositories {
            maven {
                name = "Aly Gradle Plugin Repo"
                url = uri(GRADLE_PLUGIN_REPOSITORY_URL)
            }
        }

        // Print repositories collection
        println("Now Plugins Repositories names : " + repositories.names)
    }
}

allprojects {
    repositories {
        all {
            if (this is MavenArtifactRepository) {
                val url = url.toString()
                when {
                    url.startsWith("https://repo1.maven.org/maven2") || url.startsWith("https://repo.maven.apache.org/maven2/")
                    -> {
                        setUrl(MAVEN_REPOSITORY_URL)
                    }
                    url.startsWith("https://jcenter.bintray.com/") -> {
                        setUrl(JCENTER_REPOSITORY_URL)
                    }
                    url.startsWith("https://dl.google.com/dl/android/maven2") -> {
                        setUrl(GOOGLE_REPOSITORY_URL)
                    }
                }
            }
        }
    }
    buildscript {
        repositories {
            all {
                if (this is MavenArtifactRepository) {
                    val url = this.url.toString()
                    when {
                        url.startsWith("https://repo1.maven.org/maven2") || url.startsWith("https://repo.maven.apache.org/maven2/")
                        -> {
                            setUrl(MAVEN_REPOSITORY_URL)
                        }
                        url.startsWith("https://jcenter.bintray.com/") -> {
                            setUrl(JCENTER_REPOSITORY_URL)
                        }
                        url.startsWith("https://dl.google.com/dl/android/maven2") -> {
                            setUrl(GOOGLE_REPOSITORY_URL)
                        }
                    }
                }
            }
        }
    }
    
//    afterEvaluate {
//        repositories {
//            val lastUsedRepos = filterIsInstance().map {
//                it.name + "(${it.url})"
//            }
//            if (lastUsedRepos.isNotEmpty()) {
//                println("Use these repositories at last :\n $lastUsedRepos")
//            }
//        }
//        buildscript {
//            repositories {
//                val lastUsedRepos = filterIsInstance().map {
//                    it.name + "(${it.url})"
//                }
//                if (lastUsedRepos.isNotEmpty()) {
//                    println("Use these repositories at last in build script:\n $lastUsedRepos")
//                }
//            }
//        }
//    }
}
