#!/bin/bash

REPOSITORY=/home/ec2-user/app
PROJECT_NAME=matchus

echo "> Build 파일 복사"

cp $REPOSITORY/zip/build/libs/*.jar $REPOSITORY/

CURRENT_PID=$(pgrep -fl matchus | awk '{print $1}')

echo "현재 구동 중인 애플리케이션 pid 확인"

if [ -z "$CURRENT_PID" ]; then
  echo "> 현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> 새 애플리케이션 배포"

JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)

echo "> JAR Name: $JAR_NAME"

echo "> $JAR_NAME 에 실행권한 추가"

chmod +x $JAR_NAME

echo "> $JAR_NAME 실행"

nohup java -jar \
  -Dspring.config.location=classpath:/application.yml,classpath:/application-prod.yml \
  -Dspring.profiles.active=prod \
  $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &

CURRENT_PORT=$(cat /home/ec2-user/service_url.inc | grep -Po '[0-9]+' | tail -1)

echo "> 애플리케이션 health check 'http://127.0.0.1:${CURRENT_PORT}'"

for RETRY_COUNT in {1...10}
do
  echo "> #${RETRY_COUNT} trying..."
  RESPONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://127.0.0.1:${CURRENT_PORT}/health-check)

  if [ ${RESPONSE_CODE} -eq 200 ]; then
    echo "> WAS가 성공적으로 작동 중입니다 :)"
    exit 0
  elif [ ${RETRY_COUNT} -eq 10 ]; then
    echo "> Health check failed :("
    exit 1
  fi
  sleep 10
done

# Reload Nginx
sudo service nginx reload

echo "> Nginx reloaded."
