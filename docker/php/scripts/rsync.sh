#!/bin/bash
rsync -avq --exclude-from '/var/www/excluded_Files.txt' /project/ /var/www/project --delete --chmod=ugo+rwX
