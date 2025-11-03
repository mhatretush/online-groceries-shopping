pipeline {
    agent any

    environment {
        // Set Docker socket for running Docker commands from Jenkins
        DOCKER_HOST = 'unix:///var/run/docker.sock'
    }

    options {
        // Keep last 10 builds
        buildDiscarder(logRotator(numToKeepStr: '10'))
        // Timeout after 30 minutes
        timeout(time: 30, unit: 'MINUTES')
    }

    stages {
        stage('Checkout') {
            steps {
                echo '========== STAGE 1: Checking out code from GitHub =========='
                // Jenkins automatically checks out code, but we log it for clarity
                echo "Checking out from: ${GIT_URL}"
                echo "Branch: ${GIT_BRANCH}"
            }
        }

        stage('Build Backend') {
            steps {
                echo '========== STAGE 2A: Building Spring Boot Backend =========='
                // Build Maven project
                sh 'mvn clean package -DskipTests'
                echo 'Backend build completed successfully!'
            }
        }

        stage('Build Frontend') {
            steps {
                echo '========== STAGE 2B: Building React Frontend =========='
                dir('ogs-frontend') {
                    sh 'npm install --legacy-peer-deps'
                    sh 'npm run build'
                }
                echo 'Frontend build completed successfully!'
            }
        }

        stage('Build Docker Images') {
            steps {
                echo '========== STAGE 3: Building Docker Images =========='
                // Build backend Docker image
                sh 'docker build -f Dockerfile -t online-groceries-shopping:backend-${BUILD_NUMBER} --target production .'
                echo 'Backend Docker image built: online-groceries-shopping:backend-${BUILD_NUMBER}'
                
                // Build frontend Docker image
                sh 'docker build -f ogs-frontend/Dockerfile -t online-groceries-shopping:frontend-${BUILD_NUMBER} --target production ./ogs-frontend'
                echo 'Frontend Docker image built: online-groceries-shopping:frontend-${BUILD_NUMBER}'
            }
        }

        stage('Run Tests') {
            steps {
                echo '========== STAGE 4: Running Automated Tests =========='
                
                // Backend tests (from Maven)
                sh 'mvn test'
                
                // Frontend tests (if you have Jest tests)
                dir('ogs-frontend') {
                    catchError(buildResult: 'SUCCESS', stageResult: 'UNSTABLE') {
                        sh 'npm test -- --watchAll=false'
                    }
                }
                
                echo 'All tests completed!'
            }
        }

        stage('Notifications') {
            steps {
                echo '========== STAGE 5: Sending Notifications =========='
                // Success notification
                mail (
                    to: "${CHANGE_AUTHOR_EMAIL}",
                    subject: "Jenkins Build #${BUILD_NUMBER} - SUCCESS ✅",
                    body: """
                    Build Details:
                    - Job: ${JOB_NAME}
                    - Build Number: ${BUILD_NUMBER}
                    - Status: SUCCESS
                    - Branch: ${GIT_BRANCH}
                    - Commit: ${GIT_COMMIT}
                    
                    Backend Image: online-groceries-shopping:backend-${BUILD_NUMBER}
                    Frontend Image: online-groceries-shopping:frontend-${BUILD_NUMBER}
                    
                    View Build: ${BUILD_URL}
                    """,
                    recipientProviders: [developers(), requestor(), brokenBuildSuspects()]
                )
            }
        }
    }

    post {
        success {
            echo '✅ Pipeline succeeded! All stages completed.'
        }
        failure {
            echo '❌ Pipeline failed! Check logs above for details.'
            mail (
                to: "${CHANGE_AUTHOR_EMAIL}",
                subject: "Jenkins Build #${BUILD_NUMBER} - FAILED ❌",
                body: """
                Build FAILED!
                
                Job: ${JOB_NAME}
                Build Number: ${BUILD_NUMBER}
                Branch: ${GIT_BRANCH}
                
                Check the logs: ${BUILD_URL}console
                """,
                recipientProviders: [developers(), requestor(), brokenBuildSuspects()]
            )
        }
    }
}
