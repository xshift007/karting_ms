#!/usr/bin/env bash
# -----------------------------------------------
#  tools/ports.sh
#  Muestra el puerto HTTP expuesto por cada micro
#  registrado en Eureka (server.port, NO actuator)
# -----------------------------------------------

EUREKA="http://localhost:8761/eureka/apps"

# $1 = nombre lógico del servicio tal como aparece en spring.application.name
# (Eureka guarda el appId en mayúsculas)
get_instance_xml() {
  local APP_ID="${1^^}"
  curl -sf "${EUREKA}/${APP_ID}"
}

# Extrae el <ipAddr> usando sed
extract_ip() {
  get_instance_xml "$1" \
    | sed -n 's:.*<ipAddr>\([^<]*\)</ipAddr>.*:\1:p'
}

# Extrae el <port enabled="true"> usando sed
extract_port() {
  get_instance_xml "$1" \
    | sed -n 's:.*<port[^>]*>\([0-9]*\)</port>.*:\1:p'
}

# -----------------------------------------------
#  Pricing Service
# -----------------------------------------------
IP_PRICING=$(extract_ip pricing-service)
PORT_PRICING=$(extract_port pricing-service)

if [[ -z "$IP_PRICING" || -z "$PORT_PRICING" ]]; then
  echo "No se pudo obtener IP o puerto de pricing-service" >&2
  exit 1
fi

echo "Pricing 👉  curl http://${IP_PRICING}:${PORT_PRICING}/api/pricing/..."

# -----------------------------------------------
#  Reservation Service
# -----------------------------------------------
IP_RESERV=$(extract_ip reservation-service)
PORT_RESERV=$(extract_port reservation-service)

if [[ -z "$IP_RESERV" || -z "$PORT_RESERV" ]]; then
  echo "No se pudo obtener IP o puerto de reservation-service" >&2
  exit 1
fi

echo "Reservation 👉  curl http://${IP_RESERV}:${PORT_RESERV}/api/reservations"
