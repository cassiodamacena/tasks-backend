pipeline {
    agent any
    stages {
        stage('Build Backend') {
            steps {
                bat 'mvn clean package -DskipTests=true'
            }
        }
        stage('Unit Test') {
            steps {
                bat 'mvn test'
            }
        }
        stage('Sonar Analysis') {
            environment {
                scannerHome = tool 'SONAR_SCANNER'
            }
            steps {
                withSonarQubeEnv('SONAR_LOCAL') {
                    bat "${scannerHome}/bin/sonar-scanner -e -Dsonar.projectKey=DeployBack -Dsonar.projectName='DeployBack' -Dsonar.host.url=http://localhost:9000 -Dsonar.token=sqp_12c5a8b81b01f9ab8a32fdb20d2d03f2ed4b3345 -Dsonar.java.binaries=target -Dsonar.qualitygate.wait=true -Dsonar.coverage.exclusions=**/.mvn/**,**/src/test/**,**/model/**,**Application.java"
                }
            }
        }
        stage('Quality Gate') {
            steps {
                sleep(5)
                timeout(time: 1, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
        stage('Deploy Backend HML') {
            steps {
                deploy adapters: [tomcat8(credentialsId: 'TomcatLogin', path: '', url: 'http://localhost:8001/')], contextPath: 'tasks-backend', war: 'target/tasks-backend.war'
            }
        }
        stage('API Test') {
            steps {
                dir('api-test') {
                    git credentialsId: '62cedd6a-de34-4909-a166-35be2ec7444d', url: 'https://github.com/cassiodamacena/tasks-api-test'
                    bat 'mvn test'
                }
            }
        }
        stage('Deploy Frontend HML') {
            steps {
                dir('frontend') {
                    git credentialsId: '62cedd6a-de34-4909-a166-35be2ec7444d', url: 'https://github.com/cassiodamacena/tasks-frontend'
                    bat 'mvn clean package'
                    deploy adapters: [tomcat8(credentialsId: 'TomcatLogin', path: '', url: 'http://localhost:8001/')], contextPath: 'tasks', war: 'target/tasks.war'
                }
            }
        }
        stage('Funcional Test') {
            steps {
                dir('funcional-test') {
                    git credentialsId: '62cedd6a-de34-4909-a166-35be2ec7444d', url: 'https://github.com/cassiodamacena/tasks-e2e-test'
                    bat 'mvn test'
                }
            }
        }
        stage('Deploy Prod') {
            steps {
                bat 'docker-compose build'
                bat 'docker-compose up -d'
            }
        }
        stage('Check Prod') {
            steps {
                sleep(5)
                dir('funcional-test') {
                    bat 'mvn verify -DskipTest'
                }
            }
        }
    }
}