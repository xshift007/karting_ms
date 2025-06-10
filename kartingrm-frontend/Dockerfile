# Stage 1: Build de React
FROM node:18 AS build
WORKDIR /app
COPY package.json package-lock.json ./
RUN npm install
COPY . .
RUN npm run build

# Stage 2: Servir con Nginx
FROM nginx:stable
COPY --from=build /app/dist /usr/share/nginx/html
# ❶ Sustituye el vhost por defecto con el nuestro
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
ENTRYPOINT ["nginx", "-g", "daemon off;"]
