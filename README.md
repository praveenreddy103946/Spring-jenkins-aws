name: Build and deploy JAR app to Azure Web App - neverstop

on:
push:
branches:
- main
workflow_dispatch:

jobs:
build:
runs-on: windows-latest

    steps:
      - uses: actions/checkout@v4

      - name: Set up Java version
        uses: actions/setup-java@v1
        with:
          java-version: '17'

      - name: Build with Maven
        run: mvn clean install

      - name: Upload artifact for deployment job
        uses: actions/upload-artifact@v3
        with:
          name: java-app
          path: '${{ github.workspace }}/target/*.jar'

deploy:
runs-on: windows-latest
needs: build
environment:
name: 'production'
url: ${{ steps.deploy-to-webapp.outputs.webapp-url }}
permissions:
id-token: write #This is required for requesting the JWT

    steps:
      - name: Download artifact from build job
        uses: actions/download-artifact@v3
        with:
          name: java-app
      
      - name: Login to Azure
        uses: azure/login@v1
        with:
          client-id: ${{ secrets.__clientidsecretname__ }}
          tenant-id: ${{ secrets.__tenantidsecretname__ }}
          subscription-id: ${{ secrets.__subscriptionidsecretname__ }}

      - name: Deploy to Azure Web App
        id: deploy-to-webapp
        uses: azure/webapps-deploy@v2
        with:
          app-name: 'neverstop'
          slot-name: 'production'
          package: '*.jar'

      - name: Restart Azure Web App
        run: az webapp restart --name neverstop --resource-group praveen-group
        # Optionally, wait a few seconds before checking the URL
        # run: sleep 10s

      - name: Check deployed application
        run: curl --retry 10 --retry-connrefused --retry-delay 5 -sSf ${{ steps.deploy-to-webapp.outputs.webapp-url }}
# Spring-jenkins-aws
