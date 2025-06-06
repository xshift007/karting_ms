#!/usr/bin/env bash
EUREKA=http://localhost:8761/eureka

lookup() { curl -s "$EUREKA/apps/${1^^}" | tr -d '\n'; }
ip()   { lookup "$1" | grep -oP '(?<=<ipAddr>)[^<]+'; }
port() { lookup "$1" | grep -oP '(?<=<port[^>]*>)[0-9]+'; }

svc=${1:-pricing-service}
echo "curl http://$(ip $svc):$(port $svc)/api/${svc%-service}/..."
