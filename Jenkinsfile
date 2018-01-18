pipeline {
    agent any 

    stages {
        stage('Build stage') { 
            steps { 
                withMaven(maven : 'maven_4_0_0'){
                    sh 'mvn clean compile'
                }
            }
        }
        stage('Testing stage'){
            steps { 
                withMaven(maven : 'maven_4_0_0'){
                    sh 'mvn test'
            
                }
            }
        }
        stage('Deploy') {
            steps { 
                withMaven(maven : 'maven_4_0_0'){
                    sh 'mvn deploy'
                }
            }    
        }
    }
}