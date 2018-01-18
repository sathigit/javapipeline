pipeline {
    agent any 

    stages {
        stage('Build stage') { 
            steps { 
                withMaven(maven : 'maven_3_5_0'){
                    sh 'mvn clean compile'
                }
            }
        }
        stage('Testing stage'){
            steps { 
                withMaven(maven : 'maven_3_5_0'){
                    sh 'mvn test'
            
                }
            }
        }
        stage('Deploy') {
            steps { 
                withMaven(maven : 'maven_3_5_0'){
                    sh 'mvn deploy'
                }
            }    
        }
    }
}