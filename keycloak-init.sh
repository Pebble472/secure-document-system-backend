#!/bin/bash

echo "Waiting 10 seconds for Keycloak to be fully started..."
sleep 10

echo "Logging in to Keycloak..."
/opt/keycloak/bin/kcadm.sh config credentials --server http://localhost:8080 --realm master --user admin --password admin

echo "Creating securedocs realm..."
/opt/keycloak/bin/kcadm.sh create realms -s realm=securedocs -s enabled=true

echo "Creating secure-docs-app client..."
/opt/keycloak/bin/kcadm.sh create clients -r securedocs -s clientId=secure-docs-app -s publicClient=true -s redirectUris='["http://localhost:4200/*"]' -s webOrigins='["http://localhost:4200"]' -s enabled=true

echo "Creating roles..."
/opt/keycloak/bin/kcadm.sh create roles -r securedocs -s name=ADMIN
/opt/keycloak/bin/kcadm.sh create roles -r securedocs -s name=EDITOR
/opt/keycloak/bin/kcadm.sh create roles -r securedocs -s name=VIEWER

echo "Creating admin user..."
ADMIN_ID=
/opt/keycloak/bin/kcadm.sh set-password -r securedocs --username admin -p admin
/opt/keycloak/bin/kcadm.sh add-roles -r securedocs --uusername admin --rolename ADMIN

echo "Creating editor user..."
EDITOR_ID=
/opt/keycloak/bin/kcadm.sh set-password -r securedocs --username editor -p editor
/opt/keycloak/bin/kcadm.sh add-roles -r securedocs --uusername editor --rolename EDITOR

echo "Creating viewer user..."
VIEWER_ID=
/opt/keycloak/bin/kcadm.sh set-password -r securedocs --username viewer -p viewer
/opt/keycloak/bin/kcadm.sh add-roles -r securedocs --uusername viewer --rolename VIEWER

echo "Keycloak initialization completed."
