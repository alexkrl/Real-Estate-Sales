apply plugin: "maven-publish"
apply plugin: "com.jfrog.artifactory"

afterEvaluate {
    publishing {
        publications {
            aar(MavenPublication) {
                groupId = "com.chegg"
                artifactId = project.name
                version = "${project.version}-draft"
                // Tell maven to prepare the generated "*.aar" file for publishing
                artifact("$buildDir/outputs/aar/${project.getName()}-release.aar")

                pom.withXml {
                    def config2scope = [
                        "api"           : 'compile',
                        "implementation": 'runtime'
                    ]
                    def dependenciesAlreadyIncluded = [:]
                    def dependenciesNode = asNode().appendNode('dependencies')
                    //Iterate over the compile dependencies (we don't want the test ones), adding a <dependency> node for each
                    config2scope.each { conf, scope ->
                        configurations[conf].allDependencies.each {
                            if (dependenciesAlreadyIncluded["${it.group}:${it.name}"] == null) {
                                dependenciesAlreadyIncluded["${it.group}:${it.name}"] = 1
                                if (it.group != null && (it.name != null || "unspecified".equals(it.name)) && it.version != null) {
                                    def dependencyNode = dependenciesNode.appendNode('dependency')
                                    dependencyNode.appendNode('groupId', it.group)
                                    dependencyNode.appendNode('artifactId', it.name)
                                    dependencyNode.appendNode('version', it.version)
                                    dependencyNode.appendNode('scope', scope)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    artifactoryPublish {
        publications(publishing.publications.aar)
    }

    artifactory {
        publish {
            contextUrl = "$artifactoryUrl"
            repository {
                repoKey = "libs-release-local"
                username = "$artifactoryUser"
                password = "$artifactoryPassword"

                maven = true
            }
            defaults {
                publishArtifacts = true
                publishPom = true
            }
        }
    }

    artifactoryPublish.dependsOn tasks.withType(GenerateMavenPom.class)
    artifactoryPublish.dependsOn('build')
}
