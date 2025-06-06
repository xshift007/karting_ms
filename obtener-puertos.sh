#!/usr/bin/env bash
# -----------------------------------------------
#  tools/ports.sh
#  Muestra el puerto HTTP expuesto por cada micro
#  registrado en Eureka (server.port, NO actuator)
# -----------------------------------------------

EUREKA="http://localhost:8761/eureka"

# $1 = nombre lógico del servicio tal como aparece en spring.application.name
get_port() {
  local APP=${1^^}        # Eureka guarda el appId en mayúsculas

  # ---------- intento 1: JSON + jq ----------
  if command -v jq &>/dev/null; then
    curl -sf -H "Accept: application/json" \
         "${EUREKA}/apps/${APP}" \
    | jq -r '.application.instance[0].port."$"' 2>/dev/null
    return
  fi

  # ---------- intento 2: XML + grep ----------
  curl -sf "${EUREKA}/apps/${APP}" \
  | grep -oP '(?<=<port[^>]*>)[0-9]+' \
  | head -n1
}

PPORT=$(get_port pricing-service)
RPORT=$(get_port reservation-service)

printf "Pricing service     ➜ %s\n" "${PPORT:-<no encontrado>}"
printf "Reservation service ➜ %s\n" "${RPORT:-<no encontrado>}"
