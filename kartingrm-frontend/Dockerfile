# Stage 1: Build de React
FROM node:18 AS build
WORKDIR /app
COPY package.json package-lock.json ./
RUN npm install
COPY . .
RUN npm run build

# Stage 2 – Nginx
FROM nginx:stable

# ⬇⬇  Copia TU configuración sobre la default
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Archivos estáticos de React
COPY --from=build /app/dist /usr/share/nginx/html
EXPOSE 80
ENTRYPOINT ["nginx","-g","daemon off;"]
