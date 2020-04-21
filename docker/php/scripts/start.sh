#!/bin/bash
echo ""
echo "-=- Starting rsync from host to docker -=-"
echo ""
rsync -avq --exclude-from '/var/www/excluded_Files.txt' /project/ /var/www/project --delete --chmod=ugo+rwX

(/var/www/rsync-loop.sh 2> /dev/null) &
echo ""
echo "-=- rsync script automatically running every 5 seconds -=-"
echo ""

service apache2 restart
echo "apache2 restarted"

/bin/bash

