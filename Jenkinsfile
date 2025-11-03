pipeline {
    agent any

    options {
        buildDiscarder(logRotator(numToKeepStr: '10'))
        timeout(time: 30, unit: 'MINUTES')
    }

    stages {
        stage('Checkout') {
            steps {
                echo '========== STAGE 1: Checking out code from GitHub =========='
                echo "Checking out from: ${GIT_URL}"
                echo "Branch: ${GIT_BRANCH}"
            }
        }

        stage('Build Backend') {
            steps {
                echo '========== STAGE 2A: Building Spring Boot Backend =========='
                bat '.\\mvnw clean package -DskipTests'
                echo 'Backend build completed successfully!'
            }
        }

        stage('Build Frontend') {
            steps {
                echo '========== STAGE 2B: Building React Frontend =========='
                dir('ogs-frontend') {
                    bat '"C:\\Program Files\\nodejs\\npm.cmd" install --legacy-peer-deps'
                    bat '"C:\\Program Files\\nodejs\\npm.cmd" run build'
                }
                echo 'Frontend build completed successfully!'
            }
        }

        stage('Build Docker Images') {
            steps {
                echo '========== STAGE 3: Building Docker Images =========='
                bat 'docker build -f Dockerfile -t online-groceries-shopping:backend-%BUILD_NUMBER% --target production .'
                echo 'Backend Docker image built: online-groceries-shopping:backend-%BUILD_NUMBER%'
                
                bat 'docker build -f ogs-frontend\\Dockerfile -t online-groceries-shopping:frontend-%BUILD_NUMBER% --target production .\\ogs-frontend'
                echo 'Frontend Docker image built: online-groceries-shopping:frontend-%BUILD_NUMBER%'
            }
        }

        stage('Run Tests') {
            steps {
                echo '========== STAGE 4: Running Automated Tests =========='
                
                bat '.\\mvnw test -DskipITs'
                
                dir('ogs-frontend') {
                    catchError(buildResult: 'SUCCESS', stageResult: 'UNSTABLE') {
                        bat '"C:\\Program Files\\nodejs\\npm.cmd" test -- --watchAll=false'
                    }
                }
                
                echo 'All tests completed!'
            }
        }
    }

    post {
        success {
            echo '✅ Pipeline succeeded! All stages completed.'
            echo "Backend Image: online-groceries-shopping:backend-%BUILD_NUMBER%"
            echo "Frontend Image: online-groceries-shopping:frontend-%BUILD_NUMBER%"
        }
        failure {
            echo '❌ Pipeline failed! Check logs above for details.'
        }
    }
}
