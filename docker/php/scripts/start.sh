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

echo going to Webserver
cd Webserver

echo ""
echo "-= updating fixtures =-"
echo ""

php bin/console d:s:d --force
php bin/console d:s:u --force
php bin/console d:f:l --append

cd ../

echo "every seems to be working!"
echo "hope you having fun!"
echo "if it does not work it is not my fault"
echo "FROM: application."

/bin/bash

