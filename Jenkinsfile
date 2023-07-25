pipeline{
    agent any
    stages {
        stage ('Just Test') {
            steps {
                bat 'mvn clean package -DskipTests=true'
            }
        }
    }
}
