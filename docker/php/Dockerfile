FROM php:7.2.7-apache
#install composer
RUN curl -sS https://getcomposer.org/installer | php -- --install-dir=/usr/local/bin --filename=composer \
 && apt-get update &&apt-get install  -y git libzip-dev unzip \
     && docker-php-ext-install zip \
     && apt-get install -y wget \
     apt-utils \
     iputils-ping \
     libmcrypt-dev \
     libcurl4-openssl-dev \
     libicu-dev \
     libonig-dev \
     libpq-dev \
     build-essential \
     libsqlite3-dev \
     sqlite3 \
     sqlite

RUN docker-php-ext-install pdo pdo_mysql pdo_pgsql

# https://nodejs.org/en/download/package-manager/#debian-and-ubuntu-based-linux-distributions
RUN curl -sL https://deb.nodesource.com/setup_12.x | bash -
RUN apt-get update \
 && apt-get install -y \
 nodejs
RUN npm install -g yarn

RUN pecl install xdebug && docker-php-ext-enable xdebug

RUN echo 'memory_limit = -1' >> /usr/local/etc/php/conf.d/docker-php-memlimit.ini;
RUN echo 'max_file_uploads = 1000' >> /usr/local/etc/php/conf.d/docker-php-memlimit.ini;
RUN echo 'post_max_size = 500M' >> /usr/local/etc/php/conf.d/docker-php-memlimit.ini;
RUN echo 'upload_max_filesize = 500M' >> /usr/local/etc/php/conf.d/docker-php-memlimit.ini;

COPY project.conf /etc/apache2/sites-available/000-default.conf
RUN a2enmod rewrite

RUN mkdir /var/www/project

COPY ./scripts/* /var/www/

RUN usermod -a -G root www-data
#todo restart apache in script
RUN chmod +x /var/www/*
RUN sed -i 's/\r//' /var/www/excluded_Files.txt
RUN sed -i 's/\r//' /var/www/rsync.sh
RUN sed -i 's/\r//' /var/www/rsync-loop.sh
RUN sed -i 's/\r//' /var/www/start.sh

CMD /bin/bash /var/www/start.sh

