#!/usr/bin/env bash
get_port() {
  curl -s "http://localhost:8761/eureka/apps/${1^^}" \
  | grep -oP '(?<=<port enabled="true">)\d+' | head -1
}

PPORT=$(get_port pricing-service)
RPORT=$(get_port reservation-service)

echo "Pricing      :$PPORT"
echo "Reservation  :$RPORT"

