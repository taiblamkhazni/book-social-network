#docker build -t bsn/bsn-ui:1.0.0 -f ../docker/frontend/Dockerfile .
FROM node:16 as build-stage

WORKDIR /app

COPY package*.json ./

RUN npm install

# Create the openapi directory
RUN mkdir -p ./openapi

# Télécharger la spécification OpenAPI depuis l'URL
RUN wget http://host.docker.internal:8088/api/v1/v3/api-docs -O ./openapi/openapi.json

# Print the contents of the /app/openapi directory
RUN ls -la /app/openapi

# Installer ng-openapi-gen
RUN npm install -g ng-openapi-gen
# Create the output directory
RUN mkdir -p ./services/api

RUN ng-openapi-gen --input /app/openapi/openapi.json --output ./services/api

COPY . .

RUN npm run build --prod

FROM nginx:alpine

COPY nginx.conf /etc/nginx/nginx.conf

COPY --from=build-stage /app/dist/book-network-ui /usr/share/nginx/html

EXPOSE 80