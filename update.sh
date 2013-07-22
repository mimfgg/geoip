#!/bin/bash
rm -fr GeoLiteCity-*
wget http://geolite.maxmind.com/download/geoip/database/GeoLiteCity_CSV/GeoLiteCity-latest.zip
unzip -oj GeoLiteCity-latest.zip