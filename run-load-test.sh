#!/bin/bash
STACK_NAME=CdkWorkshopGradleStack
export API_URL="$(aws cloudformation describe-stacks --stack-name $STACK_NAME --query 'Stacks[0].Outputs[?OutputKey==`ApiUrl`].OutputValue' --output text)"
./gradlew :loadtests:gatlingRun

