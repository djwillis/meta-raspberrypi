DESCRIPTION = "HTTP and reverse proxy server"
HOMEPAGE = "http://nginx.org/"
LICENSE = "BSD"
SECTION = "net"
PRIORITY = "optional"

DEPENDS = "libpcre gzip openssl"

PR = "r2"

SRC_URI = " \
	http://nginx.org/download/nginx-${PV}.tar.gz \
	file://nginx-cross_${PV}.diff;name=crosspatch \
	file://nginx.conf \
	file://nginx.init \
"

S = "${WORKDIR}/nginx-${PV}"

inherit autotools update-rc.d

SRC_URI[md5sum] = "d496e58864ab10ed56278b7655b0d0b2"
SRC_URI[sha256sum] = "84aeb7a131fccff036dc80283dd98c989d2844eb84359cfe7c4863475de923a9"
SRC_URI[crosspatch.md5sum] = "707c4cdd6bb82719ea2ed50971101c21"
SRC_URI[crosspatch.sha256sum] = "96cc3b087126caaa0951ab3e3f9f26169e9caf283dd2aeb689ed6c435070f052"
LIC_FILES_CHKSUM = "file://LICENSE;md5=917bfdf005ffb6fd025550414ff05a9f"

CONFFILES_${PN} = "${sysconfdir}/nginx/nginx.conf \
					${sysconfdir}/nginx/fastcgi.conf\
					${sysconfdir}/nginx/fastcgi_params \
					${sysconfdir}/nginx/koi-utf \
					${sysconfdir}/nginx/koi-win \
					${sysconfdir}/nginx/mime.types \
					${sysconfdir}/nginx/scgi_params \
					${sysconfdir}/nginx/uwsgi_params \
					${sysconfdir}/nginx/win-utf \
"

INITSCRIPT_NAME = "nginx"
INITSCRIPT_PARAMS = "defaults 92 20"

do_configure () {
	PTRSIZE=$(expr ${SITEINFO_BITS} / 8)

	echo $CFLAGS
	echo $LDFLAGS

	./configure \
	--crossbuild=Linux:${TUNE_ARCH} \
	--with-endian=${@base_conditional('SITEINFO_ENDIANNESS', 'le', 'little', 'big', d)} \
	--with-int=4 \
	--with-long=${PTRSIZE} \
	--with-long-long=8 \
	--with-ptr-size=${PTRSIZE} \
	--with-sig-atomic-t=${PTRSIZE} \
	--with-size-t=${PTRSIZE} \
	--with-off-t=${PTRSIZE} \
	--with-time-t=${PTRSIZE} \
	--with-sys-nerr=132 \
	--conf-path=/etc/nginx/nginx.conf \
	--http-log-path=/var/log/nginx/access.log \
	--error-log-path=/var/log/nginx/error.log \
	--pid-path=/var/run/nginx/nginx.pid \
	--prefix=/usr \
	--with-http_ssl_module \
	--with-http_gzip_static_module
}

do_install_append () {
    install -d ${D}${localstatedir}/www
    mv ${D}/usr/html ${D}${localstatedir}/www/
    chown www-data:www-data -R ${D}${localstatedir}/www
    chmod g+w -R ${D}/${localstatedir}/www

    install -d ${D}${sysconfdir}/init.d
    install -d ${D}${sysconfdir}/nginx
    install -m 0755 ${WORKDIR}/nginx.init ${D}${sysconfdir}/init.d/nginx
    install -m 0644 ${WORKDIR}/nginx.conf ${D}${sysconfdir}/nginx/

    install -d ${D}${sysconfdir}/default/volatiles
    echo "d www-data www-data 0755 ${localstatedir}/run/nginx none" \
        > ${D}${sysconfdir}/default/volatiles/99_nginx
}

FILES_${PN} += "${localstatedir}/ /run/"
