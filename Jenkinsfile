pipeline {
    agent any

    environment {
        AWS_REGION    = "us-east-1"   // change to your AWS region
        AWS_ACCOUNT_ID = "357415106205" // change to your AWS account ID
        ECR_REPO      = "test"     // your ECR repository name
        IMAGE_TAG     = "latest"     // or use BUILD_NUMBER/commit SHA for versioning
    }

    stages {
        stage('Checkout Code') {
            steps {
                checkout scm
            }
        }

        stage('Login to ECR') {
            steps {
                sh '''
                   aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 357415106205.dkr.ecr.us-east-1.amazonaws.com
                '''
            }
        }

        stage('Build Docker Image') {
            steps {
                sh '''
                  docker build -t $ECR_REPO:$IMAGE_TAG .
                  docker tag $ECR_REPO:$IMAGE_TAG $AWS_ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com/$ECR_REPO:$IMAGE_TAG
                '''
            }
        }

        stage('Push to ECR') {
            steps {
                sh '''
                  docker push $AWS_ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com/$ECR_REPO:$IMAGE_TAG
                '''
            }
        }
    }

    post {
        always {
            sh 'docker logout $AWS_ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com || true'
        }
    }
}
