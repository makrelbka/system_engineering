pipeline {
  agent any

  triggers {
    pollSCM('H/1 * * * *')
  }

  options {
    timestamps()
  }

  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Deploy (docker compose)') {
      steps {
        dir('website') {
          sh 'docker compose up -d --build --remove-orphans'
        }
      }
    }
  }
}

